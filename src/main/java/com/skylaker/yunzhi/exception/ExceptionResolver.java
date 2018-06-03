package com.skylaker.yunzhi.exception;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.pojo.res.BaseResult;
import com.skylaker.yunzhi.pojo.res.IResult;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.apache.shiro.ShiroException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 异常处理类
 *
 * @author zhuyong
 */
@Component
public class ExceptionResolver implements HandlerExceptionResolver{
	private static Logger logger = LoggerFactory.getLogger("exceptionLog");

	@Autowired
	private LoginExceptionHandler loginExceptionHandler;

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception) {

		//异常返回结果
		IResult iResult = null;

		//异常处理
		if(exception instanceof ShiroException){		//权限异常
			iResult = loginExceptionHandler.handlerLoginException(exception);
		}else if(exception instanceof LegalException){	//内容非法异常
			iResult = BaseResult.NOT_LEGAL;
		}else {
			iResult = BaseResult.FAILTURE;
		}

		//返回异常结果JSON数据
		JSONObject result = new JSONObject();
		result.put("code", iResult.getCode());
		result.put("message", iResult.getMessage());

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");

		try {
			response.getWriter().write(result.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("处理异常失败:" + e.getMessage(), e);
		}

		logger.debug("系统处理逻辑发现异常：" + exception.getMessage(), exception);
		exception.printStackTrace();

		return new ModelAndView();
	}
}

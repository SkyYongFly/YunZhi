package com.skylaker.yunzhi.exception;

import org.apache.shiro.ShiroException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理类
 *
 * @author zhuyong
 */
@Component
public class ExceptionResolver implements HandlerExceptionResolver{
	@Autowired
	private LoginExceptionHandler loginExceptionHandler;


	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception) {
		//异常输出
		exception.printStackTrace();

		//返回码
		ModelAndView modelAndView = new ModelAndView();

		//登录异常
		if(exception instanceof ShiroException){
			modelAndView.addObject("result",loginExceptionHandler.handlerLoginException(exception));
			modelAndView.setViewName("login");
			return modelAndView;
		}

		BaseException baseException = null;
		if(exception instanceof BaseException) {
			baseException = (BaseException) exception;
		}else {
			baseException = new BaseException("服务器发生异常！");
		}

		modelAndView.addObject("errormsg", baseException.getMessage());
		modelAndView.setViewName("error");

		return modelAndView;
	}
}

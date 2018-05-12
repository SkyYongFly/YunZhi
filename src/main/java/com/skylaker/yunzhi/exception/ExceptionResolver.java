package com.skylaker.yunzhi.exception;

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

	@Override
	public ModelAndView resolveException(HttpServletRequest request, 
			HttpServletResponse response, Object handler, Exception exception) {
		//异常输出
		exception.printStackTrace();
		
		BaseException BaseException = null;
		if(exception instanceof BaseException) {
			BaseException = (BaseException) exception;
		}else {
			BaseException = new BaseException("服务器发生异常！");
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("errormsg", BaseException.getMessage());
		modelAndView.setViewName("error");
		
		return modelAndView;
	}
}

package com.skylaker.wenda.exception;

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
public class SystemExceptionResolver implements HandlerExceptionResolver{

	@Override
	public ModelAndView resolveException(HttpServletRequest request, 
			HttpServletResponse response, Object handler, Exception exception) {
		//异常输出
		exception.printStackTrace();
		
		SystemBaseException systemBaseException = null;
		if(exception instanceof SystemBaseException) {
			systemBaseException = (SystemBaseException) exception;
		}else {
			systemBaseException = new SystemBaseException("服务器发生异常！");
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("errormsg", systemBaseException.getMessage());
		modelAndView.setViewName("error");
		
		return modelAndView;
	}
}

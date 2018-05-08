package com.skylaker.wenda.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.skylaker.wenda.exception.CustomException;

/**
 * �Զ���ȫ���쳣������
 * <p>
 * 	ϵͳ�׳��쳣ͳһ�ɸô��������� <br/>
 * 	�ж��׳��쳣����<br/>
 * 	1�����ΪCustomException������ת����ʾ��Ϣҳ��<br/>
 * 	2����������������Զ����쳣
 * </p> 
 * @author zhuyong
 */
@Component
public class CustomExceptionResolver implements HandlerExceptionResolver{

	@Override
	public ModelAndView resolveException(HttpServletRequest request, 
			HttpServletResponse response, Object handler, Exception exception) {
		//����쳣��Ϣ
		exception.printStackTrace();
		
		//�жϻ�ȡ�쳣��Ϣ
		CustomException customException = null;
		if(exception instanceof CustomException) {
			customException = (CustomException) exception;
		}else {
			customException = new CustomException("�������������������Ա��ϵ��");
		}
		
		//���÷�����ͼ
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("errormsg", customException.getMessage());
		modelAndView.setViewName("error");
		
		return modelAndView;
	}
}

package com.skylaker.wenda.exception;

/**
 * �Զ����쳣��SpringMVC��������з�����Ԥ���쳣���״��쳣
 * 
 * @author zhuyong
 */
public class CustomException extends Exception{
	//�쳣��Ϣ
	private String message;
	
	public CustomException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

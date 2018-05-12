package com.skylaker.yunzhi.exception;

/**
 * 系统异常基类
 * 
 * @author zhuyong
 */
public class BaseException extends Exception{
	//异常打印信息
	private String message;
	
	public BaseException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

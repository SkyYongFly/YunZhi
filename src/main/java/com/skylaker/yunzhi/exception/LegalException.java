package com.skylaker.yunzhi.exception;

/**
 * 内容不合法异常
 *
 * User: zhuyong
 * Date: 2018/6/3 15:05
 */
public class LegalException extends RuntimeException{
    private String message;

    public LegalException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
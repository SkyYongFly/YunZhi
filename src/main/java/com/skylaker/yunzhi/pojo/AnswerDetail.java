package com.skylaker.yunzhi.pojo;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 问题回答详细信息POJO
 *
 * User: zhuyong
 * Date: 2018/5/30 18:27
 */
@Alias("answerDetail")
public class AnswerDetail extends Answer implements Serializable {
    private static final long serialVersionUID = 1L;

    //回答用户名称
    private String username;
    //回答用户签名
    private String signature;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
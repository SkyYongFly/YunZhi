package com.skylaker.yunzhi.pojo;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * User: zhuyong
 * Date: 2018/5/28 23:29
 */
@Alias("questionDetail")
public class QuestionDetail extends Question implements Serializable {
    private static final long serialVersionUID = 1L;

    //问题提问用户名称
    private String username;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
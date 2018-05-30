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
    //问题提问用户签名
    private String signature;
    //问题回答数
    private Long answersnum;

    //点赞数回答用户名称
    private String hotusername;
    //点赞数回答用户签名
    private String hotsignature;
    //点赞数最多的回答内容
    private String hotanswer;
    //点赞数做多的回答的点赞数
    private Long hotstar;


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

    public Long getAnswersnum() {
        return answersnum;
    }

    public void setAnswersnum(Long answersnum) {
        this.answersnum = answersnum;
    }

    public String getHotusername() {
        return hotusername;
    }

    public void setHotusername(String hotusername) {
        this.hotusername = hotusername;
    }

    public String getHotsignature() {
        return hotsignature;
    }

    public void setHotsignature(String hotsignature) {
        this.hotsignature = hotsignature;
    }

    public String getHotanswer() {
        return hotanswer;
    }

    public void setHotanswer(String hotanswer) {
        this.hotanswer = hotanswer;
    }

    public Long getHotstar() {
        return hotstar;
    }

    public void setHotstar(Long hotstar) {
        this.hotstar = hotstar;
    }
}
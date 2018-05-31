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
    //热门指数
    private Double hotIndex;

    //点赞数最多的回答用户ID
    private Integer hotuserid;
    //点赞数最多的回答用户名称
    private String hotusername;
    //点赞数最多的回答用户签名
    private String hotsignature;
    //热门回答ID
    private Integer hotanswerid;
    //点赞数最多的回答内容
    private String hotanswer;
    //点赞数做多的回答的点赞数
    private Long hotstar;

    public QuestionDetail() {
    }

    public QuestionDetail(Question question){
        super(question);
    }


    /**
     * 设置点赞数最多回答详细信息
     *
     * @param answerDetail  点赞数回答
     */
    public void setAnswerInfo(AnswerDetail answerDetail) {
        this.hotuserid = answerDetail.getUserid();
        this.hotusername = answerDetail.getUsername();
        this.hotsignature = answerDetail.getSignature();
        this.hotanswer = answerDetail.getText();
        this.hotanswerid = answerDetail.getAid();
    }


    public Integer getHotuserid() {
        return hotuserid;
    }

    public void setHotuserid(Integer hotuserid) {
        this.hotuserid = hotuserid;
    }

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

    public void setHotIndex(Double hotIndex) {
        this.hotIndex = hotIndex;
    }

    public Double getHotIndex() {
        return hotIndex;
    }

    public Integer getHotanswerid() {
        return hotanswerid;
    }

    public void setHotanswerid(Integer hotanswerid) {
        this.hotanswerid = hotanswerid;
    }
}
package com.skylaker.yunzhi.pojo;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/27 12:48
 * Description:
 *      问题热门回答
 */
public class QuestionHotAnswer extends  Question{
    //热门回答用户ID
    private Integer answeruserid;

    //热门回答用户名称
    private String answerusername;

    //热门回答用户签名
    private String answerusersign;

    //热门回答内容（截取部分文本内容）
    private String answertext;

    //热门回答点赞数
    private Integer star;


    public Integer getAnsweruserid() {
        return answeruserid;
    }

    public void setAnsweruserid(Integer answeruserid) {
        this.answeruserid = answeruserid;
    }

    public String getAnswerusername() {
        return answerusername;
    }

    public void setAnswerusername(String answerusername) {
        this.answerusername = answerusername;
    }

    public String getAnswerusersign() {
        return answerusersign;
    }

    public void setAnswerusersign(String answerusersign) {
        this.answerusersign = answerusersign;
    }

    public String getAnswertext() {
        return answertext;
    }

    public void setAnswertext(String answertext) {
        this.answertext = answertext;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }
}
package com.skylaker.yunzhi.pojo.db;

import org.apache.ibatis.type.Alias;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 问题实体
 *
 * User: zhuyong
 * Date: 2018/5/17 22:29
 */
@Alias("question")
@Table(name = "QUESTION")
public class Question implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "qid")
    @GeneratedValue(generator = "JDBC")
    private   Integer qid;          //问题主键ID
    @Column
    private   String  title;        //问题标题
    @Column
    private   String  text;         //问题描述
    @Column
    private   Integer userid;       //提问用户ID
    @Column
    private   Date    createtime;   //创建时间
    @Column
    private   Date    updatetime;   //修改时间

    @Transient
    private   String  username;     //问题提问用户名称
    @Transient
    protected String  signature;    //问题提问用户签名
    @Transient
    protected String  userheadimg;  //用户头像
    @Transient
    protected Long    answersnum;   //问题回答数
    @Transient
    protected Double  hotIndex;     //热门指数
    @Transient
    protected Integer hotuserid;    //点赞数最多的回答用户ID
    @Transient
    protected String  hotusername;  //点赞数最多的回答用户名称
    @Transient
    protected String  hotsignature; //点赞数最多的回答用户签名
    @Transient
    protected String  hotuserheadimg;//点赞数最多的回答用户头像相对路径
    @Transient
    protected Integer hotanswerid;  //热门回答ID
    @Transient
    protected String  hotanswer;    //点赞数最多的回答内容
    @Transient
    protected Long    hotstar;      //点赞数做多的回答的点赞数


    public Question() {

    }

    public Question(Question question) {
        this.title = question.title;
        this.text = question.text;
        this.userid = question.userid;
        this.createtime = question.createtime;
        this.updatetime = question.updatetime;
    }

    public Question(String title, String text, Integer userid, Date createtime, Date updatetime) {
        this.title = title;
        this.text = text;
        this.userid = userid;
        this.createtime = createtime;
        this.updatetime = updatetime;
    }

    /**
     * 设置点赞数最多回答详细信息
     *
     * @param answer  点赞数回答
     */
    public void setAnswerInfo(Answer answer) {
        this.hotuserid = answer.getUserid();
        this.hotusername = answer.getUsername();
        this.hotsignature = answer.getSignature();
        this.hotanswer = answer.getText();
        this.hotanswerid = answer.getAid();
    }

    public Integer getQid() {
        return qid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
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

    public String getUserheadimg() {
        return userheadimg;
    }

    public void setUserheadimg(String userheadimg) {
        this.userheadimg = userheadimg;
    }

    public Long getAnswersnum() {
        return answersnum;
    }

    public void setAnswersnum(Long answersnum) {
        this.answersnum = answersnum;
    }

    public Double getHotIndex() {
        return hotIndex;
    }

    public void setHotIndex(Double hotIndex) {
        this.hotIndex = hotIndex;
    }

    public Integer getHotuserid() {
        return hotuserid;
    }

    public void setHotuserid(Integer hotuserid) {
        this.hotuserid = hotuserid;
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

    public String getHotuserheadimg() {
        return hotuserheadimg;
    }

    public void setHotuserheadimg(String hotuserheadimg) {
        this.hotuserheadimg = hotuserheadimg;
    }

    public Integer getHotanswerid() {
        return hotanswerid;
    }

    public void setHotanswerid(Integer hotanswerid) {
        this.hotanswerid = hotanswerid;
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

    @Override
    public String toString() {
        return "Question{" +
                "qid=" + qid +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", userid=" + userid +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }
}
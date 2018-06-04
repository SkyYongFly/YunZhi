package com.skylaker.yunzhi.pojo.db;

import org.apache.ibatis.type.Alias;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 问题回答POJO
 *
 * User: zhuyong
 * Date: 2018/5/17  22:33
 */
@Alias("answer")
@Table(name = "ANSWER")
public class Answer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "aid")
    @GeneratedValue(generator = "JDBC")
    private Integer aid;        //回答主键ID
    @Column
    private String  text;       //回答内容
    @Column
    private Integer qid;        //回答的问题ID
    @Column
    private Integer userid;     //回答用户ID
    @Column
    private Date    createtime; //创建时间
    @Column
    private Integer star;       //点赞数

    @Transient
    protected String username;  //回答用户名称
    @Transient
    protected String signature; //回答用户签名
    @Transient
    protected String userheadimg;//回答用户头像


    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getQid() {
        return qid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
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

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
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

    @Override
    public String toString() {
        return "Answer{" +
                "aid=" + aid +
                ", text='" + text + '\'' +
                ", qid=" + qid +
                ", userid=" + userid +
                ", createtime=" + createtime +
                ", star=" + star +
                '}';
    }
}
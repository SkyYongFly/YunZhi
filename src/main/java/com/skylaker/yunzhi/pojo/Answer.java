package com.skylaker.yunzhi.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/17
 * Time: 22:33
 * Description:
 */
public class Answer implements Serializable {
    private static final long serialVersionUID = 1L;

    //回答主键ID
    private int aid;

    //回答内容
    private String text;

    //回答的问题ID
    private int qid;

    //回答用户ID
    private int userid;

    //创建时间
    private Date createtime;

    //点赞数
    private int star;

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
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
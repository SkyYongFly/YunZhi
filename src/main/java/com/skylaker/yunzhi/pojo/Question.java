package com.skylaker.yunzhi.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/17
 * Time: 22:29
 * Description: 问题实体
 */
public class Question implements Serializable {
    private static final long serialVersionUID = 1L;

    //问题主键ID
    private int qid;

    //问题标题
    private String title;

    //问题描述
    private String text;

    //提问用户ID
    private int userid;

    //创建时间
    private Date createtime;

    //修改时间
    private Date updatetime;

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
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

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
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
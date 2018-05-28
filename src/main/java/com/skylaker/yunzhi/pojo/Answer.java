package com.skylaker.yunzhi.pojo;

import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/17
 * Time: 22:33
 * Description:
 */
@Alias("answer")
@Table(name = "ANSWER")
public class Answer implements Serializable {
    private static final long serialVersionUID = 1L;

    //回答主键ID
    @Id
    @Column(name = "aid")
    @GeneratedValue(generator = "JDBC")
    private int aid;

    //回答内容
    @Column
    private String text;

    //回答的问题ID
    @Column
    private int qid;

    //回答用户ID
    @Column
    private int userid;

    //创建时间
    @Column
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
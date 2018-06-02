package com.skylaker.yunzhi.pojo.db;

import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 文件保存记录POJO
 *
 * User: zhuyong
 * Date: 2018/6/1 10:48
 */
@Alias("fileupload")
@Table(name = "FILEUPLOAD")
public class Fileupload implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "fid")
    @GeneratedValue(generator = "JDBC")
    private Integer fid;     //文件记录主键ID
    @Column
    private Integer userid;  //关联用户ID
    @Column
    private String  fname;   //文件名称
    @Column
    private String fpath;   //文件绝对路径
    @Column
    private String fspath;  //文件相对路径
    @Column
    private String fcode;   //文件关联的业务标识
    @Column
    private String ftype;   //文件类型
    @Column
    private Date   time;    //文件上传时间
    @Column
    private Double size;    //文件大小


    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFpath() {
        return fpath;
    }

    public void setFpath(String fpath) {
        this.fpath = fpath;
    }

    public String getFspath() {
        return fspath;
    }

    public void setFspath(String fspath) {
        this.fspath = fspath;
    }

    public String getFcode() {
        return fcode;
    }

    public void setFcode(String fcode) {
        this.fcode = fcode;
    }

    public String getFtype() {
        return ftype;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }
}
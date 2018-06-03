package com.skylaker.yunzhi.pojo.com;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志信息POJO
 *
 * User: zhuyong
 * Date: 2018/6/3 12:54
 */
public class LogInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    //用户ID
    private Integer userid;
    //用户名称
    private String  username;
    //操作类型
    private String  logtype;
    //操作内容
    private String  logaction;
    //时间
    private Date    time = new Date();

    public LogInfo() {
    }

    public LogInfo(String logtype, String logaction) {
        this.logtype = logtype;
        this.logaction = logaction;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLogtype() {
        return logtype;
    }

    public void setLogtype(String logtype) {
        this.logtype = logtype;
    }

    public String getLogaction() {
        return logaction;
    }

    public void setLogaction(String logaction) {
        this.logaction = logaction;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getJSONString(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userid", this.userid);
        jsonObject.put("username", this.username);
        jsonObject.put("logtype", this.logtype);
        jsonObject.put("logaction", this.logaction);
        jsonObject.put("time", this.time);

        return jsonObject.toJSONString();
    }

    @Override
    public String toString() {
        return "LogInfo{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", logtype='" + logtype + '\'' +
                ", logaction='" + logaction + '\'' +
                ", time=" + time +
                '}';
    }
}
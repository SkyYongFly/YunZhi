package com.skylaker.yunzhi.pojo;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/12
 * Time: 11:34
 * Description: 用户权限信息
 */
public class Permission {
    //用户权限信息表主键ID
    private int pid;

    //权限名称
    private String pername;



    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPername() {
        return pername;
    }

    public void setPername(String pername) {
        this.pername = pername;
    }
}
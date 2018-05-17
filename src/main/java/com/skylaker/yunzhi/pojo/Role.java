package com.skylaker.yunzhi.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色
 *
 * @author  sky
 */
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    //角色表主键ID
    private int     roleid;

    //角色名称
    private String  rolename;

    //角色描述
    private String  content;

    //角色创建时间
    private Date    createtime;

    //角色信息修改时间
    private Date 	updatetime;

    //是否被锁定，0否1是
    private boolean locked;


    public Role() {

    }

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleid=" + roleid +
                ", rolename='" + rolename + '\'' +
                ", content='" + content + '\'' +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", locked=" + locked +
                '}';
    }
}

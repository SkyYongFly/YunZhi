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
    private int     rid;

    //角色名称
    private String  rolename;

    //角色描述
    private String  description;

    //角色创建时间
    private Date    createtime;

    //角色信息修改时间
    private Date 	updatetime;


    public Role() {

    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return "Role{" +
                "rid=" + rid +
                ", name='" + rolename + '\'' +
                ", description='" + description + '\'' +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                '}';
    }
}

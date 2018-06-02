package com.skylaker.yunzhi.pojo.db;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色
 *
 * @author  sky
 */
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "roleid")
    @GeneratedValue(generator = "JDBC")
    private Integer roleid;     //角色表主键ID
    @Column
    private String  rolename;   //角色名称
    @Column
    private String  content;    //角色描述
    @Column
    private Date    createtime; //角色创建时间
    @Column
    private Date 	updatetime; //角色信息修改时间
    @Column
    private boolean locked;     //是否被锁定，0否1是

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
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

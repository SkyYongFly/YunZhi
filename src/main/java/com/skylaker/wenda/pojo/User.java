package com.skylaker.wenda.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * User POJO对象
 * 
 * @author sky
 */
@Alias("user")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;

	//用户表主键ID
	private Integer	uid;

	//用户名称（昵称）
	private String 	name;

	//用户账号
	private String  account;

	//账号密码
	private String 	password;

	//用户创建时间
	private Date 	createtime;

	//用户信息修改时间
	private Date 	updatetime;

	public User(){

	}


	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
		return "User{" +
				"uid=" + uid +
				", name='" + name + '\'' +
				", account='" + account + '\'' +
				", password='" + password + '\'' +
				", createtime=" + createtime +
				", updatetime=" + updatetime +
				'}';
	}
}

package com.skylaker.yunzhi.pojo;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

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

	//用户名/账号
	private String 	username;

	//账号密码
	private String 	password;

	//用户昵称
	private String  nickname;

	//用户创建时间
	private Date 	createtime;

	//用户信息修改时间
	private Date 	updatetime;

	//用户是否被锁定
	private boolean locked;

	//用户锁定时间
	private Date lockedtime;

	//用户密码加盐
	private String salt;


	public User(){

	}

	/**
	 * 获取用户密码加密盐，默认使用用户名以及设置的盐混合
	 *
	 * @return
	 */
	public String getCredentialsSalt(){
		return username + salt;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public Date getLockedtime() {
		return lockedtime;
	}

	public void setLockedtime(Date lockedtime) {
		this.lockedtime = lockedtime;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override
	public String toString() {
		return "User{" +
				"uid=" + uid +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", nickname='" + nickname + '\'' +
				'}';
	}
}

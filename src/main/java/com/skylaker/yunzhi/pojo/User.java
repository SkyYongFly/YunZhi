package com.skylaker.yunzhi.pojo;

import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * User POJO对象
 * 
 * @author sky
 */
@Alias("user")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;

	//用户表主键ID
	private Integer	id;

	//用户名/账号
	private String 	username;

	//账号密码
	private String 	password;

	//手机号
	private String  phone;

	//签名
	private String  signature;

	//用户创建时间
	private Date 	createtime;

	//用户信息修改时间
	private Date 	updatetime;

	//用户是否被锁定
	private boolean locked;

	//用户密码加盐
	private String 	salt;

	//角色ID
	private String 	roleid;


	public User(){

	}

	public User(Builder builder){
		this.username = builder.username;
		this.password = builder.password;
		this.phone = builder.phone;
		this.signature = builder.signature;
		this.createtime = builder.createtime;
		this.updatetime = builder.updatetime;
		this.locked = builder.locked;
		this.salt = builder.salt;
	}

	public static class Builder{
		private String 	username;
		private String 	password;
		private String  phone;
		private String  signature;
		private String  salt;

		//默认属性设置
		private Date 	createtime = new Date();
		private Date 	updatetime = new Date();
		private boolean locked = false;

		public 	Builder(String phone){
			this.phone = phone;
		}

		public Builder username(String username){
			this.username = username;
			return this;
		}

		public Builder password(String password){
			this.password = password;
			return this;
		}

		public Builder salt(String salt){
			this.salt = salt;
			return this;
		}

		public Builder signature(String signature){
			this.signature = signature;
			return this;
		}

		public User build(){
			return new User(this);
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
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

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", phone='" + phone + '\'' +
				", signature='" + signature + '\'' +
				", createtime=" + createtime +
				", updatetime=" + updatetime +
				", locked=" + locked +
				", salt='" + salt + '\'' +
				", roleid='" + roleid + '\'' +
				'}';
	}
}

package com.skylaker.yunzhi.pojo.db;

import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
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

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "JDBC")
	private Integer	id;			//用户表主键ID
	@Column
	private String 	username;	//用户名/账号
	@Column
	private String 	password;	//账号密码
	@Column
	private String  phone;		//手机号
	@Column
	private String  signature;	//签名
	@Column
	private Date 	createtime;	//用户创建时间
	@Column
	private Date 	updatetime;	//用户信息修改时间
	@Column
	private Boolean locked;		//用户是否被锁定
	@Column
	private String 	salt;		//用户密码加盐
	@Column
	private String 	roleid;		//角色ID

	@Transient
	private String  vercode;	//短信验证码


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

	public User(Integer id, String username, String signature) {
		this.id = id;
		this.username = username;
		this.signature = signature;
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
		private Boolean locked = false;

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

	public Boolean isLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
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

	public String getVercode() {
		return vercode;
	}

	public void setVercode(String vercode) {
		this.vercode = vercode;
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

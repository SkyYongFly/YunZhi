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
	
	private Integer id;
	private String name;
	private String address;
	private Integer age;
	private Date birth;
	
	public User() {
		super();
	}

	public User(Integer id, String name, String address, Integer age, Date birth) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.age = age;
		this.birth = birth;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
}

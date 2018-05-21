package com.skylaker.yunzhi.mappers;

import java.util.List;

import com.skylaker.yunzhi.pojo.User;

/**
 * User 相关SQL操作Mapper接口
 * 
 * @author sky
 */
public interface UserMapper{
	/**
	 * 根据用户ID获取用户信息
	 * 
	 * @param 	id	用户ID
	 * @return		指定ID对应用户对象
	 */
	User getUserById(Integer id);
	
	/**
	 * 添加用户
	 *
	 * @param user	要添加的用户对象
	 */
	void addUser(User user);

	/**
	 * 获取所用的用户
	 * 
	 * @return 用户列表
	 */
	List<User> getAllUsers();

}

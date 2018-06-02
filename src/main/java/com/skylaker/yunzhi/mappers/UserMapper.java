package com.skylaker.yunzhi.mappers;

import java.util.List;

import com.skylaker.yunzhi.pojo.db.Fileupload;
import com.skylaker.yunzhi.pojo.db.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * User 相关SQL操作Mapper接口
 * 
 * @author sky
 */
@Repository
public interface UserMapper extends Mapper<User>, MySqlMapper<User> {
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

	/**
	 * 根据用户手机号获取对应的账号信息
	 *
	 * @param 	phone
	 * @return
	 */
	User getUserByPhone(String phone);

	/**
	 * 获取用户头像相对路径
	 *
	 * @param 	userId
	 * @return
	 */
	Fileupload getUserHeadImg(@Param("userId")Integer userId);
}

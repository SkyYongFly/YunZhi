package com.skylaker.yunzhi.service;

import com.skylaker.yunzhi.pojo.db.Role;
import com.skylaker.yunzhi.pojo.db.User;
import com.skylaker.yunzhi.pojo.res.IResult;
import com.skylaker.yunzhi.pojo.res.LoginResult;

import java.util.List;
import java.util.Set;

/**
 * 用户相关逻辑处理接口定义
 *
 * User: zhuyong
 * Date: 2018/5/20 11:17
 */
public interface IUserService {

    /**
     * 手机号、密码验证
     *
     * @param phone
     * @param password
     * @return
     */
    LoginResult userPwdValidate(String phone, String password);

    /**
     * 获取所有用户
     *
     * @return  用户列表
     */
    List<User> getAllUsers();

    /**
     * 根据手机号获取用户信息对象
     *
     * @param   phone  手机号
     * @return
     */
    User getUserByPhone(String phone);

    /**
     * 获取用户拥有的角色信息
     * @param   phone     手机号
     * @return  {set}     角色信息集合
     */
    Set<Role> getUserRoles(String phone);

    /**
     * 保存注册用户信息
     *
     * @param user  注册用户
     */
    void saveRegisterUser(User user);

    /**
     * 获取用户头像相对路径
     *
     * @param userId
     * @return
     */
    String getUserHeadImg(Integer userId);

    /**
     * 获取用户信息
     *
     * @return
     */
    User getUserInfo();

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    IResult updateUserInfo(User user);
}

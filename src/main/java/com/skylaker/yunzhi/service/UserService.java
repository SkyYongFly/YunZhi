package com.skylaker.yunzhi.service;

import com.skylaker.yunzhi.mappers.UserMapper;
import com.skylaker.yunzhi.pojo.Permission;
import com.skylaker.yunzhi.pojo.Role;
import com.skylaker.yunzhi.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 用户处理逻辑类
 *
 * @author sky
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 获取所有用户
     *
     * @return  用户列表
     */
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    /**
     * 根据用户名获取用户信息对象
     *
     * @param   username  用户名
     * @return
     */
    public User getUserByUserName(String username) {
        //TODO
        return  null;
    }

    /**
     * 获取用户拥有的角色信息
     * @param   username  用户名
     * @return  {set}     角色信息集合
     */
    public Set<Role> getUserRoles(String username) {
        //TODO
        return null;
    }

    /**
     * 获取用户拥有的权限信息
     * @param   username  用户名
     * @return  {set}     权限信息集合
     */
    public Set<Permission> getUserPermissions(String username) {
        //TODO
        return null;
    }
}

package com.skylaker.wenda.service;

import com.skylaker.wenda.mappers.UserMapper;
import com.skylaker.wenda.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

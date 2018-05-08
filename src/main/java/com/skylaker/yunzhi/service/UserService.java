package com.skylaker.yunzhi.service;

import com.skylaker.yunzhi.mappers.UserMapper;
import com.skylaker.yunzhi.pojo.User;
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

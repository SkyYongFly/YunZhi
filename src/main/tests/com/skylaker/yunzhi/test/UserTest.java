package com.skylaker.yunzhi.test;

import com.skylaker.yunzhi.pojo.RegisterInfo;
import com.skylaker.yunzhi.service.UserService;
import com.skylaker.yunzhi.service.impl.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/21 21:42
 * Description:
 */
public class UserTest {
    @Test
    public void testAddUser(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setUsername("小米");
        registerInfo.setPassword("123455");
        registerInfo.setPhone("1223232");

        UserService userService = context.getBean("userServiceImpl",UserService.class);
        userService.saveRegisterUser(registerInfo);
    }
}
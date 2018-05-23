package com.skylaker.yunzhi.test;

import com.skylaker.yunzhi.pojo.RegisterInfo;
import com.skylaker.yunzhi.pojo.User;
import com.skylaker.yunzhi.service.UserService;
import com.skylaker.yunzhi.service.impl.UserServiceImpl;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.UUID;

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


    @Test
    public void testGetUser(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        UserService userService = context.getBean("userServiceImpl", UserService.class);
        User user = userService.getUserByPhone("17692133962");
        System.out.println(user.toString());
    }

    @Test
    public void testUUID(){
        System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
    }

    @Test
    public void testMD5(){
    }

    @Test
    public void testUser(){
        User user = new User.Builder("1212").username("小张").password("111222").build();
        System.out.println(user);

    }
}
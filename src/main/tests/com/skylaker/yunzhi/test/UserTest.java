package com.skylaker.yunzhi.test;

import com.skylaker.yunzhi.pojo.db.User;
import com.skylaker.yunzhi.service.IUserService;
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

        User registerInfo = new User();
        registerInfo.setUsername("小米");
        registerInfo.setPassword("123455");
        registerInfo.setPhone("1223232");

        IUserService userService = context.getBean("userServiceImpl",IUserService.class);
        userService.saveRegisterUser(registerInfo);
    }


    @Test
    public void testGetUser(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        IUserService userService = context.getBean("userServiceImpl", IUserService.class);
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

//        Subject subject = SecurityUtils.getSubject();
//        User user2 = (User) subject.getSession().getAttribute(GlobalConstant.SESSION_USER_NAME);
//        System.out.println(user2);

    }

    @Test
    public void testFile(){
    }
}
package com.skylaker.yunzhi.controller;

import com.skylaker.yunzhi.pojo.LoginResult;
import com.skylaker.yunzhi.pojo.User;
import com.skylaker.yunzhi.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/12
 * Time: 11:55
 * Description: 用户登录管理
 */
@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public @ResponseBody LoginResult login(@RequestParam("username")String username, @RequestParam("password")String password){
        //获取验证token
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        //用户名、密码验证
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        }catch (IncorrectCredentialsException e){
            //密码不正确
            return LoginResult.INCORRECT_PWD;
        }catch (UnknownAccountException e){
            //用户不存在
            return LoginResult.NO_ACCOUNT;
        }catch (ExcessiveAttemptsException e){
            //输错密码次数过多
            return LoginResult.TO_MUCH_ERROR;
        }

        User user = userService.getUserByUserName(username);
        subject.getSession().setAttribute("user", user);

        return LoginResult.SUCCESS;
    }
}
package com.skylaker.yunzhi.controller;

import com.skylaker.yunzhi.pojo.LoginResult;
import com.skylaker.yunzhi.pojo.RegisterInfo;
import com.skylaker.yunzhi.pojo.User;
import com.skylaker.yunzhi.service.UserService;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/12
 * Time: 11:55
 * Description: 用户登录管理
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    /**
     * 获取登录页面
     *
     * @return
     */
    @RequestMapping("/getLoginPage")
    public String getLoginPage(){
        return "redirect:/login/login.do";
    }

    /**
     * 登录页面
     *
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    /**
     * 系统登录请求控制器
     *
     * @param   user
     * @return
     */
    @RequestMapping(value = "/loginreq", method = RequestMethod.POST)
    public String  login(@ModelAttribute User user){
        LoginResult loginResult = userPwdValidate(user.getPhone(), user.getPassword());

        //登录成功返回首页
        if(LoginResult.SUCCESS == loginResult){
            return "redirect:/index.jsp";
        }

        //返回码
        ModelAndView modelAndView = new ModelAndView();
        //返回页面，默认失败返回登录页面
        modelAndView.addObject(loginResult);
        return "redirect:/login/login.do";
    }

    /**
     * 获取用户登录验证结果
     *
     * @param phone
     * @param password
     * @return
     */
    @RequestMapping(value = "/loginValidate", method = RequestMethod.GET)
    public @ResponseBody LoginResult loginValidate(@RequestParam("phone")String phone, @RequestParam("password")String password){
        return  userPwdValidate(phone, password);
    }

    /**
     * 手机号、密码验证
     *
     * @param phone
     * @param password
     * @return
     */
    private LoginResult userPwdValidate(String phone, String password){
        if(BaseUtil.isNullOrEmpty(phone) || BaseUtil.isNullOrEmpty(password)){
            return LoginResult.NULL_NAME_PWD;
        }

        //获取验证token
        UsernamePasswordToken token = new UsernamePasswordToken(phone, password);

        //用户名、密码验证
        Subject subject = SecurityUtils.getSubject();

        //登录验证，异常由异常处理对象来处理
        subject.login(token);

        User user = userService.getUserByPhone(phone);
        subject.getSession().setAttribute("user", user);

        return LoginResult.SUCCESS;
    }
}
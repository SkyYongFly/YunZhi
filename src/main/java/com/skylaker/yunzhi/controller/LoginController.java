package com.skylaker.yunzhi.controller;

import com.skylaker.yunzhi.pojo.LoginResult;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @RequestMapping(value = "/getLoginPage", method = RequestMethod.GET)
    public String getLoginPage(){
        return "login";
    }

    /**
     * 系统登录请求控制器
     *
     * @param username
     * @param password
     * @return  成功返回系统首页，失败登陆页面
     */
    @RequestMapping(value = "/loginreq", method = RequestMethod.GET)
    public String  login(@RequestParam("username")String username, @RequestParam("password")String password){
        LoginResult loginResult = userPwdValidate(username, password);

        //登录成功返回首页
        if(LoginResult.SUCCESS == loginResult){
            return "redirect:/index.jsp";
        }

        //返回码
        ModelAndView modelAndView = new ModelAndView();
        //返回页面，默认失败返回登录页面
        modelAndView.addObject(loginResult);
        return "login";
    }

    /**
     * 获取用户登录验证结果
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/loginValidate", method = RequestMethod.GET)
    public @ResponseBody LoginResult loginValidate(@RequestParam("username")String username, @RequestParam("password")String password){
        return  userPwdValidate(username, password);
    }

    /**
     * 用户名、密码验证
     * @param username
     * @param password
     * @return
     */
    private LoginResult userPwdValidate(String username, String password){
        if(BaseUtil.isNullOrEmpty(username) || BaseUtil.isNullOrEmpty(password)){
            return LoginResult.NULL_NAME_PWD;
        }

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
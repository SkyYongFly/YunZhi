package com.skylaker.yunzhi.controller;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.pojo.db.User;
import com.skylaker.yunzhi.service.IUserService;
import com.skylaker.yunzhi.service.aop.LogAnnotation;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户登录管理
 *
 * User: zhuyong
 * Date: 2018/5/12 11:55
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    @Resource(name = "userServiceImpl")
    private IUserService userService;


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
     * 跳转到系统首页
     *
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String  index(){
        return "redirect:/index.jsp";
    }

    /**
     * 获取用户登录验证结果
     *
     * @param   user
     * @return
     */
    @LogAnnotation(type = "登录", action = "验证用户名、密码")
    @RequestMapping(value = "/loginValidate", method = RequestMethod.POST)
    public @ResponseBody JSONObject loginValidate(@RequestBody User user){
        return BaseUtil.getResult(userService.userPwdValidate(user.getPhone(), user.getPassword()));
    }

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    @LogAnnotation(type = "用户操作", action = "获取用户信息")
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public @ResponseBody User getUserInfo(){
        User user = BaseUtil.getSessionUser();

        if(null == user){
            return new User();
        }

        //避免传输其他信息
        return new User(user.getId(), user.getUsername(), user.getSignature());
    }
}
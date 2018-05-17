package com.skylaker.yunzhi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/17
 * Time: 20:21
 * Description: 注册相关请求处理类
 */
@Controller
@RequestMapping("/register")
public class RegisterController {

    /**
     * 获取注册页面
     *
     * @return  {string} 注册页面
     */
    @RequestMapping(value = "/getPage", method = RequestMethod.GET)
    public String getRegisterPage(){
        return "register";
    }
}
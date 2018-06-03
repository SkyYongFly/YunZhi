package com.skylaker.yunzhi.controller;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.pojo.db.User;
import com.skylaker.yunzhi.service.IRegisterService;
import com.skylaker.yunzhi.service.aop.LogAnnotation;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 注册相关请求处理类
 *
 * User: zhuyong
 * Date: 2018/5/17  20:21
 */
@Controller
@RequestMapping("/register")
public class RegisterController {
    @Resource(name = "registerServiceImpl")
    private IRegisterService registerService;


    /**
     * 获取注册页面
     *
     * @return  {string} 注册页面
     */
    @RequestMapping(value = "/getPage")
    public String getRegisterPage(){
        return "register";
    }

    /**
     * 获取短信验证码
     *
     * @param phone 请求手机号
     */
    @LogAnnotation(type = "注册", action = "获取注册短信验证码")
    @RequestMapping(value = "/getVercode", method = RequestMethod.GET)
    public @ResponseBody JSONObject getVercode(@RequestParam("phone")String phone){
        return BaseUtil.getResult(registerService.getVercode(phone));
    }

    /**
     * 用户注册信息验证请求
     *
     * @param   user 注册用户
     * @return
     */
    @LogAnnotation(type = "注册", action = "用户注册")
    @RequestMapping(value = "/registerValidate", method = RequestMethod.POST)
    public @ResponseBody JSONObject registerValidate(@RequestBody User user){
        return BaseUtil.getResult(registerService.registerValidate(user));
    }
}
package com.skylaker.yunzhi.controller;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.pojo.RegisterInfo;
import com.skylaker.yunzhi.service.IRegisterService;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    @Qualifier("registerServiceImpl")
    private IRegisterService registerService;


    /**
     * 获取注册页面
     *
     * @return  {string} 注册页面
     */
    @RequestMapping(value = "/getPage", method = RequestMethod.GET)
    public String getRegisterPage(){
        return "register";
    }

    /**
     * 获取短信验证码
     *
     * @param phone 请求手机号
     */
    @RequestMapping(value = "/getVercode", method = RequestMethod.GET)
    public @ResponseBody JSONObject getVercode(@RequestParam("phone")String phone){
        return BaseUtil.getResult(registerService.getVercode(phone));
    }

    /**
     * 用户注册信息验证请求
     *
     * @param registerInfo
     * @return
     */
    @RequestMapping(value = "/registerValidate", method = RequestMethod.POST)
    public @ResponseBody JSONObject registerValidate(@RequestBody RegisterInfo registerInfo){
        return BaseUtil.getResult(registerService.registerValidate(registerInfo));
    }
}
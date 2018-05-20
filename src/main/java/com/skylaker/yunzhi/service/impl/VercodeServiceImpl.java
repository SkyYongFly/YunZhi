package com.skylaker.yunzhi.service.impl;

import com.skylaker.yunzhi.service.VercodeService;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/20 11:09
 * Description:
 */
@Service("vercodeServiceImpl")
public class VercodeServiceImpl implements VercodeService {

    @Override
    public void sendPhoneVercode(String phone) {
        //TODO 采用MQ发送短信验证码 需要把手机号以及对应的验证码信息缓存到redis中
        System.out.println("已发送验证码。。。");

    }
}
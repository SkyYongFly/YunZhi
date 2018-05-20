package com.skylaker.yunzhi.service;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/20 11:07
 * Description: 验证码逻辑接口定义
 */
public interface VercodeService {
    /**
     * 发送手机验证码
     *
     * @param phone 手机号
     */
    void sendPhoneVercode(String phone);
}

package com.skylaker.yunzhi.service;

import com.skylaker.yunzhi.pojo.db.User;

/**
 * 验证码逻辑接口定义
 *
 * User: zhuyong
 * Date: 2018/5/20 11:07
 */
public interface IVercodeService {
    /**
     * 发送手机验证码
     *
     * @param phone 手机号
     */
    void sendPhoneVercode(String phone);

    /**
     * 将请求验证码手机号及请求时间缓存到redis
     *
     * @param  phone 手机号
     */
    void savePhoneHasSendSmsInfo(String phone);

    /**
     * 从redis中获取缓存的手机号及请求时间以判断手机号是否在一分钟之内已发送过验证码
     *
     * @param   phone       手机号
     * @return  {boolean}   一分钟内发送过：true ；未发送过或者超过一分钟 ：false
     */
    boolean hasSendSmsInOneMin(String phone);

    /**
     * 手机验证码验证
     * 验证提交的验证码和发送的验证码是否一致
     *
     * @param  user         注册用户
     * @return {boolean}    验证通过：true；不通过：false
     */
    boolean validateVercode(User user);
}

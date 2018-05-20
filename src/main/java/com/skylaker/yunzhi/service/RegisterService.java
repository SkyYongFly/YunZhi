package com.skylaker.yunzhi.service;

import com.skylaker.yunzhi.pojo.RegisterInfo;
import com.skylaker.yunzhi.pojo.RegisterResult;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/20 11:31
 * Description:
 *      注册相关逻辑接口定义
 */
public interface RegisterService {
    /**
     * 发送短信验证码
     *
     * @param phone 手机号
     */
    RegisterResult getVercode(String phone);

    /**
     * 用户注册信息验证
     *
     * @param registerInfo  注册信息
     * @return
     */
    RegisterResult registerValidate(RegisterInfo registerInfo);
}

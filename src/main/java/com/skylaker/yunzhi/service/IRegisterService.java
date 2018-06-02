package com.skylaker.yunzhi.service;

import com.skylaker.yunzhi.pojo.db.User;
import com.skylaker.yunzhi.pojo.res.IResult;
import com.skylaker.yunzhi.pojo.res.RegisterResult;

/**
 * 注册相关逻辑接口定义
 *
 * User: zhuyong
 * Date: 2018/5/20 11:31
 */
public interface IRegisterService {
    /**
     * 发送短信验证码
     *
     * @param phone 手机号
     */
    RegisterResult getVercode(String phone);


    /**
     * 用户注册验证
     *
     * @param   user  注册用户
     * @return
     */
    IResult registerValidate(User user);
}

package com.skylaker.yunzhi.service.impl;

import com.skylaker.yunzhi.pojo.db.User;
import com.skylaker.yunzhi.pojo.res.RegisterResult;
import com.skylaker.yunzhi.service.IRegisterService;
import com.skylaker.yunzhi.service.IUserService;
import com.skylaker.yunzhi.service.IVercodeService;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 注册相关逻辑具体实现类
 *
 * User: zhuyong
 * Date: 2018/5/20 11:33
 */
@Service("registerServiceImpl")
public class RegisterServiceImpl extends BaseService<User> implements IRegisterService {
    @Resource(name = "vercodeServiceImpl")
    private IVercodeService vercodeService;

    @Resource(name = "userServiceImpl")
    private IUserService userService;


    /**
     * 获取手机号短信验证码
     * 考虑并发情况需要加锁
     *
     * @param   phone 手机号
     * @return
     */
    @Override
    public  synchronized RegisterResult getVercode(String phone) {
        if(BaseUtil.isNullOrEmpty(phone) || !BaseUtil.isPhone(phone)){
            return RegisterResult.INVALIDATE_PHONE;
        }

        //判断手机号是否在一分钟之内已发送过验证码
        if(vercodeService.hasSendSmsInOneMin(phone)){
            return RegisterResult.TO_MUCH_COUNT;
        }

        //缓存手机号信息到redis，方便判断是否在一分钟之内已发送过验证码
        vercodeService.savePhoneHasSendSmsInfo(phone);

        //调用短信验证码发送
        vercodeService.sendPhoneVercode(phone);

        return RegisterResult.SUCCESS;
    }

    /**
     * 注册信息验证
     *
     * @param   user  注册用户
     * @return  {enum}
     */
    @Override
    public RegisterResult registerValidate(User user) {
        //验证用户名格式
        if(!BaseUtil.validateUserName(user.getUsername())){
            return RegisterResult.INVALIDATE_USERNAME;
        }

        //验证密码格式
        if(!BaseUtil.validatePassword(user.getPassword())){
            return RegisterResult.INVALIDATE_PASSWORD;
        }

        //验证手机号格式
        if(!BaseUtil.isPhone(user.getPhone())){
            return RegisterResult.INVALIDATE_PHONE;
        }

        //验证手机号是否已注册过
        if(redisService.validatePhoneHasRegister(user.getPhone())){
            return RegisterResult.PHONE_HAS_REGISTER;
        }

        //验证验证码
        if(!vercodeService.validateVercode(user)){
            return RegisterResult.INVALIDATE_VERCODE;
        }

        try {
            //保存注册用户信息
            userService.saveRegisterUser(user);
            //验证成功缓存已注册手机号
            redisService.saveHasRegisterPhone(user);
        }catch (Exception e){
            e.printStackTrace();
            return RegisterResult.REGISTER_FAILURE;
        }

        return RegisterResult.SUCCESS;
    }
}
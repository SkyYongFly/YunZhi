package com.skylaker.yunzhi.service.impl;

import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.pojo.RegisterInfo;
import com.skylaker.yunzhi.pojo.RegisterResult;
import com.skylaker.yunzhi.service.IRegisterService;
import com.skylaker.yunzhi.service.IUserService;
import com.skylaker.yunzhi.service.IVercodeService;
import com.skylaker.yunzhi.utils.BaseUtil;
import com.skylaker.yunzhi.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/20 11:33
 * Description:
 *      注册相关逻辑具体实现类
 */
@Service("registerServiceImpl")
public class RegisterServiceImpl implements IRegisterService {
    @Autowired
    @Qualifier("vercodeServiceImpl")
    private IVercodeService vercodeService;

    @Autowired
    @Qualifier("userServiceImpl")
    private IUserService userService;

    @Autowired
    private RedisUtil redisUtil;


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
     * @param   registerInfo  注册信息
     * @return  {enum}
     */
    @Override
    public RegisterResult registerValidate(RegisterInfo registerInfo) {
        //验证用户名格式
        if(!BaseUtil.validateUserName(registerInfo.getUsername())){
            return RegisterResult.INVALIDATE_USERNAME;
        }

        //验证密码格式
        if(!BaseUtil.validatePassword(registerInfo.getPassword())){
            return RegisterResult.INVALIDATE_PASSWORD;
        }

        //验证手机号格式
        if(!BaseUtil.isPhone(registerInfo.getPhone())){
            return RegisterResult.INVALIDATE_PHONE;
        }

        //验证手机号是否已注册过
        if(validatePhoneHasRegister(registerInfo.getPhone())){
            return RegisterResult.PHONE_HAS_REGISTER;
        }

        //验证验证码
        if(!vercodeService.validateVercode(registerInfo)){
            return RegisterResult.INVALIDATE_VERCODE;
        }

        try {
            //保存注册用户信息
            userService.saveRegisterUser(registerInfo);
            //验证成功缓存已注册手机号
            redisUtil.addSetValue(GlobalConstant.REDIS_SET_HASREGISTERPHONE, registerInfo.getPhone());
        }catch (Exception e){
            e.printStackTrace();
            return RegisterResult.REGISTER_FAILURE;
        }

        return RegisterResult.SUCCESS;
    }

    /**
     * 判断指定手机号用户是否已注册
     *
     * @param   phone   手机号
     * @return
     */
    private boolean validatePhoneHasRegister(String phone) {
        if(BaseUtil.isNullOrEmpty(phone)){
            return false;
        }

        return  redisUtil.existInSet(GlobalConstant.REDIS_SET_HASREGISTERPHONE, phone);
    }
}
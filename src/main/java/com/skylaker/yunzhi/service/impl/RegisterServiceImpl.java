package com.skylaker.yunzhi.service.impl;

import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.pojo.RegisterInfo;
import com.skylaker.yunzhi.pojo.RegisterResult;
import com.skylaker.yunzhi.pojo.User;
import com.skylaker.yunzhi.service.RegisterService;
import com.skylaker.yunzhi.service.UserService;
import com.skylaker.yunzhi.service.VercodeService;
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
public class RegisterServiceImpl implements RegisterService{
    @Autowired
    @Qualifier("vercodeServiceImpl")
    private VercodeService vercodeService;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

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
        if(hasSendSmsInOneMin(phone)){
            return RegisterResult.TO_MUCH_COUNT;
        }

        //缓存手机号信息到redis，方便判断是否在一分钟之内已发送过验证码
        savePhoneHasSendSmsInfo(phone);

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
        if(!validateVercode(registerInfo)){
            return RegisterResult.INVALIDATE_VERCODE;
        }

        //TODO 验证成功需要缓存已注册手机号
        userService.saveRegisterUser(registerInfo);

        return null;
    }

    /**
     * 从redis中获取缓存的手机号及请求时间以判断手机号是否在一分钟之内已发送过验证码
     *
     * @param   phone       手机号
     * @return  {boolean}   一分钟内发送过：true ；未发送过或者超过一分钟 ：false
     */
    private boolean hasSendSmsInOneMin(String phone) {
        long lastTime = 0;
        if(redisUtil.hasHashKey(GlobalConstant.REDIS_HASH_PHONEVERCODE, phone)){
            lastTime = (long) redisUtil.getHashValue(GlobalConstant.REDIS_HASH_PHONEVERCODE, phone);
        }

        long nowTime = System.currentTimeMillis();
        if(nowTime - lastTime < GlobalConstant.ONE_MINUTE_MICRO_SECONDS){
            return true;
        }

        return false;
    }

    /**
     * 将请求验证码手机号及请求时间缓存到redis
     *
     * @param  phone 手机号
     */
    private void savePhoneHasSendSmsInfo(String phone) {
        redisUtil.setHashValue(GlobalConstant.REDIS_HASH_PHONEVERCODE, phone, System.currentTimeMillis());
    }

    /**
     * 手机验证码验证
     * 验证提交的验证码和发送的验证码是否一致
     *
     * @param  registerInfo 注册信息
     * @return {boolean}    验证通过：true；不通过：false
     */
    private boolean validateVercode(RegisterInfo registerInfo) {
        if(BaseUtil.isNullOrEmpty(registerInfo.getVercode())){
            return false;
        }

        //缓存手机号验证码的hash键名称
        String vercodeHashKey = GlobalConstant.REDIS_HASH_PHONEVERCODE_PREFIX + registerInfo.getPhone();

        //判断是否存在指定键
        if(!redisUtil.hasHashKey(vercodeHashKey, registerInfo.getPhone())){
            return false;
        }

        //获取并判断验证码
        String vercode = (String) redisUtil.getHashValue(vercodeHashKey, registerInfo.getPhone());
        if(!registerInfo.getVercode().equals(vercode)){
            return false;
        }

        return true;
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
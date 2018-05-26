package com.skylaker.yunzhi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.pojo.RegisterInfo;
import com.skylaker.yunzhi.service.VercodeService;
import com.skylaker.yunzhi.utils.BaseUtil;
import com.skylaker.yunzhi.utils.RedisUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/20 11:09
 * Description:
 */
@Service("vercodeServiceImpl")
public class VercodeServiceImpl implements VercodeService {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    AmqpTemplate rabbitTemplate;

    /**
     * 发送手机验证码
     *
     * @param phone 手机号
     */
    @Override
    public void sendPhoneVercode(String phone) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phone", phone);

        rabbitTemplate.convertAndSend("exchange_direct_sms", "register_sms", jsonObject.toJSONString());
    }

    /**
     * 将请求验证码手机号及请求时间缓存到redis
     *
     * @param  phone 手机号
     */
    public void savePhoneHasSendSmsInfo(String phone) {
        redisUtil.setHashValue(GlobalConstant.REDIS_HASH_PHONEVERCODE, phone, System.currentTimeMillis());
    }

    /**
     * 从redis中获取缓存的手机号及请求时间以判断手机号是否在一分钟之内已发送过验证码
     *
     * @param   phone       手机号
     * @return  {boolean}   一分钟内发送过：true ；未发送过或者超过一分钟 ：false
     */
    public boolean hasSendSmsInOneMin(String phone) {
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
     * 手机验证码验证
     * 验证提交的验证码和发送的验证码是否一致
     *
     * @param  registerInfo 注册信息
     * @return {boolean}    验证通过：true；不通过：false
     */
    public boolean validateVercode(RegisterInfo registerInfo) {
        if(BaseUtil.isNullOrEmpty(registerInfo.getVercode())){
            return false;
        }

        //判断是否存在指定键
        if(!redisUtil.hasHashKey(GlobalConstant.REDIS_HASH_PHONEVERCODES, registerInfo.getPhone())){
            return false;
        }

        //获取并判断验证码
        String vercode = (String) redisUtil.getHashValue(GlobalConstant.REDIS_HASH_PHONEVERCODES, registerInfo.getPhone());
        if(!registerInfo.getVercode().equals(vercode)){
            return false;
        }

        return true;
    }
}
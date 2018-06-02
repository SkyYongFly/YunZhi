package com.skylaker.yunzhi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.pojo.db.User;
import com.skylaker.yunzhi.service.IVercodeService;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 验证码处理逻辑
 *
 * User: zhuyong
 * Date: 2018/5/20 11:09
 */
@Service("vercodeServiceImpl")
public class VercodeServiceImpl extends BaseService<User> implements IVercodeService {
    @Autowired
    AmqpTemplate rabbitTemplate;


    @Override
    public void sendPhoneVercode(String phone) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phone", phone);

        rabbitTemplate.convertAndSend("exchange_direct_sms", "register_sms", jsonObject.toJSONString());
    }

    @Override
    public void savePhoneHasSendSmsInfo(String phone) {
        redisService.savePhoneHasSendSms(phone);
    }

    @Override
    public boolean hasSendSmsInOneMin(String phone) {
        return redisService.hasSendSmsInOneMin(phone);
    }

    @Override
    public boolean validateVercode(User user) {
        if(BaseUtil.isNullOrEmpty(user.getVercode())){
            return false;
        }

        return redisService.validateVercode(user);
    }
}
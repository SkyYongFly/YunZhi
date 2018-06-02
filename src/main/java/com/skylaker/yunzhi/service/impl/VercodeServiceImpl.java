package com.skylaker.yunzhi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.pojo.db.User;
import com.skylaker.yunzhi.service.IVercodeService;
import com.skylaker.yunzhi.utils.BaseUtil;
import com.skylaker.yunzhi.utils.RedisUtil;
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

    @Override
    public void savePhoneHasSendSmsInfo(String phone) {
        redisService.savePhoneHasSendSmsInfo(phone);
    }

    @Override
    public boolean hasSendSmsInOneMin(String phone) {
        return redisService.hasSendSmsInOneMin(phone);
    }


    /**
     * 手机验证码验证
     * 验证提交的验证码和发送的验证码是否一致
     *
     * @param  user         注册用户
     * @return {boolean}    验证通过：true；不通过：false
     */
    public boolean validateVercode(User user) {
        if(BaseUtil.isNullOrEmpty(user.getVercode())){
            return false;
        }

        return redisService.validateVercode(user);
    }
}
package com.skylaker.yunzhi.listener;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.utils.RedisUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 * 发送短信MQ处理
 *
 * User: zhuyong
 * Date: 2018/5/23 23:29
 */
@Service
public class SmsMqListener implements MessageListener {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void onMessage(Message message) {
        try {
            String messageBody = new String(message.getBody(), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(messageBody);
            String phone = (String) jsonObject.get("phone");

            //TODO 发送短信
            System.out.println("手机请求发送短信 ：" + phone);

            //TODO 获取回执，成功
            //缓存手机号验证码的hash键名称
            redisUtil.setHashValue(GlobalConstant.REDIS_HASH_PHONEVERCODES, phone, "111222");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
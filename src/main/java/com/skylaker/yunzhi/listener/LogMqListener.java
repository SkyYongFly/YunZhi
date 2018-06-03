package com.skylaker.yunzhi.listener;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 * User: zhuyong
 * Date: 2018/6/3 13:17
 */
@Service
public class LogMqListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            String messageBody = new String(message.getBody(), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(messageBody);
System.out.println(jsonObject.toJSONString());

            //TODO 日志处理，保存到数据库或者磁盘


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
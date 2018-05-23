package com.skylaker.yunzhi.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/23 23:29
 * Description:
 *      发送短信MQ处理
 */
@Service
public class SmsMqListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        System.out.println("接收到了消息：" + message.getBody());
    }
}
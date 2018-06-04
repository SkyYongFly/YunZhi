package com.skylaker.yunzhi.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 * 问题队列处理
 *
 * User: zhuyong
 * Date: 2018/6/4 11:10
 */
@Service
public class QuestionMqListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            String questionMessage = new String(message.getBody(), "UTF-8");
            System.out.println("收到问题处理消息:" + questionMessage);



        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
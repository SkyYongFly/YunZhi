package com.skylaker.yunzhi.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 * 回答队列处理
 *
 * User: zhuyong
 * Date: 2018/6/4 11:11
 */
@Service
public class AnswerMqListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            String answerMessage = new String(message.getBody(), "UTF-8");
            System.out.println("获取回答消息：" + answerMessage);



        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
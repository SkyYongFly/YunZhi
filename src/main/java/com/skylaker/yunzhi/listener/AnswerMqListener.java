package com.skylaker.yunzhi.listener;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.skylaker.yunzhi.pojo.db.Answer;
import com.skylaker.yunzhi.service.ILuceneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * 回答队列处理
 *
 * User: zhuyong
 * Date: 2018/6/4 11:11
 */
@Service
public class AnswerMqListener implements MessageListener {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "luceneServiceImpl")
    private ILuceneService luceneService;


    @Override
    public void onMessage(Message message) {
        try {
            String answerMessage = new String(message.getBody(), "UTF-8");
            logger.debug("获取回答消息：" + answerMessage);

            Answer answer = JSONObject.parseObject(answerMessage, new TypeReference<Answer>(){});
            luceneService.addIndex(answer);
            logger.debug("回答:" + answer.getAid() + "已交由Lucene索引！");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
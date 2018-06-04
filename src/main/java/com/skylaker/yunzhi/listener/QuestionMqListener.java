package com.skylaker.yunzhi.listener;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.skylaker.yunzhi.pojo.db.Question;
import com.skylaker.yunzhi.service.ILuceneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * 问题队列处理
 *
 * User: zhuyong
 * Date: 2018/6/4 11:10
 */
@Service
public class QuestionMqListener implements MessageListener {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "luceneServiceImpl")
    private ILuceneService luceneService;


    @Override
    public void onMessage(Message message) {
        try {
            String questionMessage = new String(message.getBody(), "UTF-8");
            logger.debug("收到问题处理消息:" + questionMessage);

            Question question = JSONObject.parseObject(questionMessage, new TypeReference<Question>(){});
            luceneService.addIndex(question);
            logger.debug("已将问题：" + question.getQid() + "交由Lucene进行索引！");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
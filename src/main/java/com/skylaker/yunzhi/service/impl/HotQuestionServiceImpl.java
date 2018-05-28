package com.skylaker.yunzhi.service.impl;

import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.service.IHotQuestionService;
import com.skylaker.yunzhi.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  问题热门指数处理逻辑类 <br/>
 *
 *  热门指数计算公式：问题回答数 * 10.0 + 问题回答点赞数 * 1.0
 * </p>
 *
 * User: zhuyong
 * Date: 2018/5/28 20:59
 */
@Service("notQuestionServiceImpl")
public class HotQuestionServiceImpl implements IHotQuestionService {
    //问题初始化热门指数
    private static final double QUESTION_HOT_INDEX = 0.0;
    //回答一个问题热门指数增加的值
    private static final double ANSWER_HOT_INDEX = 10.0;
    //点赞一次对应回答的问题热门指数增加的值
    private static final double STAR_HOT_INDEX = 1.0;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public void initQuestionHotIndex(int qid) {
        redisUtil.addZsetValue(GlobalConstant.REDIS_ZSET_QUESTIONS_HOT, qid, this.QUESTION_HOT_INDEX);
    }

    @Override
    public synchronized void updateQuestionHotIndexOfAnswer(int qid) {
        redisUtil.increaseZsetScore(GlobalConstant.REDIS_ZSET_QUESTIONS_HOT, qid , this.ANSWER_HOT_INDEX);
    }
}
package com.skylaker.yunzhi.service.impl;

import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.mappers.QuestionMapper;
import com.skylaker.yunzhi.pojo.Question;
import com.skylaker.yunzhi.pojo.QuestionResult;
import com.skylaker.yunzhi.pojo.User;
import com.skylaker.yunzhi.service.IQuestionService;
import com.skylaker.yunzhi.utils.BaseUtil;
import com.skylaker.yunzhi.utils.RedisUtil;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/26 15:48
 * Description:
 *      问题相关逻辑处理
 */
@Service("questionServiceImpl")
public class QuestionServiceImpl extends BaseService<Question> implements IQuestionService {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public QuestionResult addQuestion(Question question) {
        if(BaseUtil.isNullOrEmpty(question.getTitle())){
            return QuestionResult.NULL_TITLE;
        }

        //TODO 后期对于话题的判断
        User user = BaseUtil.getSessionUser();
        if(null == user){
            throw new UnknownAccountException();
        }

        question.setUserid(user.getId());
        question.setCreatetime(new Date());
        question.setUpdatetime(new Date());

        //TODO 如何做到同步
        //保存问题到DB
        super.save(question);
        //缓存问题信息
        saveQuestionToRedis(question);

        return QuestionResult.SUCCESS;
    }

    /**
     * 新增问题，注册到Redis
     *
     * @param question
     */
    private void saveQuestionToRedis(Question question){
        //保存问题时间戳
        redisUtil.addZsetValue(GlobalConstant.REDIS_ZSET_QUESTIONS_TIME,
                question.getQid(), Double.valueOf(System.currentTimeMillis()));

        //初始化热门问题
        redisUtil.addZsetValue(GlobalConstant.REDIS_ZSET_QUESTIONS_HOT, question.getQid(), 0.0);
    }
}
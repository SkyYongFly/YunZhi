package com.skylaker.yunzhi.service.impl;

import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.mappers.AnswerMapper;
import com.skylaker.yunzhi.pojo.*;
import com.skylaker.yunzhi.service.IAnswerService;
import com.skylaker.yunzhi.service.IHotQuestionService;
import com.skylaker.yunzhi.utils.BaseUtil;
import com.skylaker.yunzhi.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/28 20:30
 * Description:
 *      问题回答具体逻辑处理类
 */
@Service("answerServiceImpl")
public class AnswerServiceImpl extends BaseService<Answer> implements IAnswerService {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    @Qualifier("hotQuestionServiceImpl")
    private IHotQuestionService hotQuestionService;

    @Autowired
    private AnswerMapper answerMapper;


    /**
     * 新增问题回答
     *
     * @param answer
     * @return
     */
    @Transactional
    @Override
    public BaseResult addAnswer(Answer answer) {
        //设置回答用户信息
        answer.setUserid(BaseUtil.getSessionUser().getId());
        //设置回答创建时间
        answer.setCreatetime(new Date());
        //初始化问题点赞数
        answer.setStar(0);

        //保存回答
        super.save(answer);
        //保存相关信息到redis
        saveInfoIntoRedis(answer);

        return BaseResult.SUCCESS;
    }

    /**
     * <p>
     *  保存相关信息到redis <br/>
     *
     *  1、新增回答记录到问题zset中 ，需要初始化点赞数为 0 <br/>
     *  2、修改回答对应的问题热门指数 <br/>
     * </p>
     *
     * @param answer 新增的问题回答
     */
    private void saveInfoIntoRedis(Answer answer) {
        //新增回答记录到问题zset中
        redisUtil.addZsetValue(answer.getQid() + GlobalConstant.REDIS_ZSET_QUESTION_ANSWERS, answer.getAid(), 0.0);

        //增加问题热门指数
        hotQuestionService.updateQuestionHotIndexOfAnswer(answer.getQid());
    }


    /**
     * 获取问题的所有回答，按照点赞数量倒序排序
     *
     * @param page  分页查询页码
     * @param qid   问题ID
     *
     * @return  {list}
     */
    @Override
    public AnswersList getQuestionAllAnswers(int page, int qid) {
        if(null == Integer.valueOf(page) || page < 1){
            page = 1;
        }

        //设置分页信息
        PageInfo pageInfo = new PageInfo(page, GlobalConstant.ANSWERS_NUM, qid);
        //查询问题回答
        List<AnswerDetail> answerDetails = answerMapper.getQuestionAllAnswers(pageInfo);
        //获取问题回答的总数
        Long sum = redisUtil.getZsetCount(BaseUtil.getRedisQuestionAnswersKey(qid), Double.valueOf(0), Double.POSITIVE_INFINITY);

        return new AnswersList(answerDetails,  sum);
    }
}
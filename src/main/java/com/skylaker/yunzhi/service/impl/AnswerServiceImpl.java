package com.skylaker.yunzhi.service.impl;

import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.mappers.AnswerMapper;
import com.skylaker.yunzhi.pojo.com.PageInfo;
import com.skylaker.yunzhi.pojo.db.Answer;
import com.skylaker.yunzhi.pojo.db.AnswersList;
import com.skylaker.yunzhi.pojo.res.BaseResult;
import com.skylaker.yunzhi.service.IAnswerService;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 问题回答具体逻辑处理类
 *
 * User: zhuyong
 * Date: 2018/5/28 20:30
 */
@Service("answerServiceImpl")
public class AnswerServiceImpl extends BaseService<Answer> implements IAnswerService {
    @Autowired
    private AnswerMapper answerMapper;


    /**
     * <p>
     *   新增问题回答<br/>
     *
     *   1、注意这里要清除指定问题信息缓存，避免下次查询问题对应的回答数没有同步更新
     * </p>
     *
     * @param   answer 新添加回答
     * @return
     */
    @Transactional
    @CacheEvict(value = "questionCache", key = "#answer.qid", beforeInvocation = true)
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
        redisService.saveAnswerInfo(answer);

        return BaseResult.SUCCESS;
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
        List<Answer> answers = answerMapper.getQuestionAllAnswers(pageInfo);

        //设置回答用户头像信息
        for (int i = 0, len = answers.size(); i < len; i++) {
            answers.get(i).setUserheadimg(redisService.getUserHeadImg(answers.get(i).getUserid()));
        }

        //获取问题回答的总数
        Long sum = redisService.getQuestionAnswersCount(qid);

        return new AnswersList(answers,  sum);
    }

    /**
     * <p>
     *    回答点赞操作 <br/>
     *
     *    1、需要判断当前用户对目标回答是否点赞过 <br/>
     *    2、如果点赞过不允许重复点赞<br/>
     *    3、没有点赞过需要保存相关点赞信息<br/>
     *    4、避免频繁点击，导致数据同步问题，需要加锁处理
     * </p>
     *
     *
     * @param   aid  回答ID
     * @param   qid  问题ID
     *
     * @return
     */
    @Override
    public synchronized Long starAction(Integer aid, Integer qid) {
        //TODO 加锁性能上会有影响，需要优化
        //TODO 更新热门指数
        //判断用户是否哦对当前回答点赞过
        boolean hasStared =  redisService.hasStaredAnswer(aid);
        //获取问题点赞数
        Long stars = redisService.getAnswerStars(qid, aid);

        //redis中进行点赞相关的处理
        redisService.staredAnswer(qid ,aid);

        //TODO 这一步需要改为异步，避免频繁IO对数据库的影响
        if(hasStared){
            //已经点赞过
            //数据库中回答点赞数减 1
            answerMapper.decreaseStarsNum(aid);
            return stars - 1;
        }else{
            //没有点赞过
            //数据库中回答点赞数加 1
            answerMapper.increaseStarsNum(aid);
            return stars + 1;
        }
    }
}
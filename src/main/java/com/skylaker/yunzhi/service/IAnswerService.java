package com.skylaker.yunzhi.service;

import com.skylaker.yunzhi.pojo.db.Answer;
import com.skylaker.yunzhi.pojo.db.AnswersList;
import com.skylaker.yunzhi.pojo.res.BaseResult;
import com.skylaker.yunzhi.pojo.res.IResult;

/**
 * 问题回答处理逻辑定义接口
 *
 * User: zhuyong
 * Date: 2018/5/28 20:27
 */
public interface IAnswerService {
    /**
     * 新增问题回答
     *
     * @param answer
     * @return
     */
    IResult addAnswer(Answer answer);

    /**
     * 获取问题的所有回答
     *
     * @param page  分页查询页码
     * @param qid   问题ID
     * @return
     */
    AnswersList getQuestionAllAnswers(int page, int qid);

    /**
     * 对问题回答进行点赞操作
     *
     *
     * @param   aid  回答ID
     * @param   qid  问题ID
     *
     * @return
     */
    Long starAction(Integer aid , Integer qid);
}
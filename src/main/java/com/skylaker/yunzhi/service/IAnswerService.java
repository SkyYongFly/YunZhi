package com.skylaker.yunzhi.service;

import com.skylaker.yunzhi.pojo.Answer;
import com.skylaker.yunzhi.pojo.AnswersList;
import com.skylaker.yunzhi.pojo.BaseResult;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/28 20:27
 * Description:
 *      问题回答处理逻辑定义接口
 */
public interface IAnswerService {
    /**
     * 新增问题回答
     *
     * @param answer
     * @return
     */
    BaseResult addAnswer(Answer answer);

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
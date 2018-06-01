package com.skylaker.yunzhi.service;

import com.skylaker.yunzhi.pojo.Question;
import com.skylaker.yunzhi.pojo.QuestionDetail;
import com.skylaker.yunzhi.pojo.QuestionResult;
import com.skylaker.yunzhi.pojo.QuestionsList;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/26 15:42
 * Description:
 *      问题处理逻辑接口
 */
public interface IQuestionService {

    /**
     * 新增问题
     *
     * @param question
     * @return
     */
    QuestionResult addQuestion(Question question);

    /**
     * 获取系统中的最新问题
     *
     * @return
     */
    List<Question> getNewestQuestions();

    /**
     * 查询问题详细信息
     *
     * @param qid 问题ID
     * @return
     */
    QuestionDetail getQuestionDetail(int qid);

    /**
     * 获取系统中的最新的问题列表
     *
     * @param   page    分页信息，第几页
     * @param   time    用户请求时间戳
     * @return
     */
    List<QuestionDetail> getNewestQuestionsDetails(int page, long time);

    /**
     * 查询指定时间之前的所有问题数量
     *
     * @param  time 指定时间戳
     * @return
     */
    Long getNewestQuestionsCount(long time);

    /**
     * 获取问题回答数
     *
     * @param   qid 问题ID
     * @return
     */
    Long getQuestionAnswers(int qid);

    /**
     * 查询用户提问的问题分页信息，第几页
     *
     * @param page  分页信息，第几页
     * @return
     */
    QuestionsList getUserQuestions(int page);
}

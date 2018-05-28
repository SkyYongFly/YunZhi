package com.skylaker.yunzhi.service;

import com.skylaker.yunzhi.pojo.Question;
import com.skylaker.yunzhi.pojo.QuestionDetail;
import com.skylaker.yunzhi.pojo.QuestionResult;

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
    Question getQuestionDetail(String qid);

    /**
     * 获取系统中的最新的问题列表
     *
     * @param   page    分页信息，第几页
     * @param   time    用户请求时间戳
     * @return
     */
    List<QuestionDetail> getNewestQuestionsDetails(int page, long time);
}

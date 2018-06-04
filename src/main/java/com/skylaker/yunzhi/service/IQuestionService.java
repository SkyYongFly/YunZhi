package com.skylaker.yunzhi.service;

import com.skylaker.yunzhi.pojo.db.Question;
import com.skylaker.yunzhi.pojo.res.IResult;
import com.skylaker.yunzhi.pojo.res.QuestionResult;
import com.skylaker.yunzhi.pojo.db.QuestionsList;

import java.util.List;

/**
 * 问题处理逻辑接口
 *
 * User: zhuyong
 * Date: 2018/5/26 15:42
 * Description:
 */
public interface IQuestionService {

    /**
     * 新增问题
     *
     * @param question
     * @return
     */
    IResult addQuestion(Question question);

    /**
     * 查询问题详细信息
     *
     * @param qid 问题ID
     * @return
     */
    Question getQuestion(int qid);

    /**
     * 获取系统中的最新问题
     *
     * @return
     */
    List<Question> getNewestQuestions();

    /**
     * 获取系统中的最新的问题列表
     *
     * @param   page    分页信息，第几页
     * @param   time    用户请求时间戳
     * @return
     */
    QuestionsList getNewestQuestionsDetails(int page, long time);

    /**
     * 获取最热门的问题集合
     *
     * @param page  页码
     * @param token 缓存标识
     * @return
     */
    QuestionsList getHotQuestionsByPage(int page, String token);

    /**
     * 查询用户提问的问题分页信息，第几页
     *
     * @param page  分页信息，第几页
     * @return
     */
    QuestionsList getUserQuestions(int page);
}

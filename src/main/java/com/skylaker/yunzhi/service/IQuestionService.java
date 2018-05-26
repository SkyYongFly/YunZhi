package com.skylaker.yunzhi.service;

import com.skylaker.yunzhi.pojo.Question;
import com.skylaker.yunzhi.pojo.QuestionResult;

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
}

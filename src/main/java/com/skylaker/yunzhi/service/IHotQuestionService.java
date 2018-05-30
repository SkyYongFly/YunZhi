package com.skylaker.yunzhi.service;

import com.skylaker.yunzhi.pojo.HotQuestionsList;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/28 20:53
 * Description:
 *      热门问题相关逻辑定义接口
 */
public interface IHotQuestionService {
    /**
     * 初始化问题的热门指数
     *
     * @param qid   问题ID
     */
    void initQuestionHotIndex(int qid);

    /**
     * 问题新增一条评论修改热门指数
     *
     * @param qid 问题ID
     */
    void updateQuestionHotIndexOfAnswer(int qid);

    /**
     * 获取最热门的问题集合
     *
     * @param page  页码
     * @param token 缓存标识
     * @return
     */
    HotQuestionsList getHotQuestionsDetailsByPage(int page, String token);
}

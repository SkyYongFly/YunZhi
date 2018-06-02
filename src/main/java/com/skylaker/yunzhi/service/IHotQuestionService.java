package com.skylaker.yunzhi.service;

import com.skylaker.yunzhi.pojo.db.QuestionsList;

/**
 * 热门问题相关逻辑定义接口
 *
 * User: zhuyong
 * Date: 2018/5/28 20:53
 */
public interface IHotQuestionService {
    /**
     * 获取最热门的问题集合
     *
     * @param page  页码
     * @param token 缓存标识
     * @return
     */
    QuestionsList getHotQuestionsByPage(int page, String token);
}

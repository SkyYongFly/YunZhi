package com.skylaker.yunzhi.service;

import com.skylaker.yunzhi.pojo.Answer;
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
}
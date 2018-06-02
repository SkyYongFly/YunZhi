package com.skylaker.yunzhi.pojo.db;

import java.io.Serializable;
import java.util.List;

/**
 * 问题列表查询结果
 *
 * User: zhuyong
 * Date: 2018/5/29 22:37
 */
public class QuestionsList implements Serializable {
    private static final long serialVersionUID = 1L;

    //查询的当前展示页最新问题集合
    private List<Question> questions;
    //需要展示的问题总数量
    private Long           sum;
    //缓存标识
    private String         token;

    public QuestionsList(List<Question> questions, Long sum) {
        this.questions = questions;
        this.sum = sum;
    }

    public QuestionsList(List<Question> questionsList, Long num, String token) {
        this.questions = questionsList;
        this.sum = num;
        this.token = token;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }
}
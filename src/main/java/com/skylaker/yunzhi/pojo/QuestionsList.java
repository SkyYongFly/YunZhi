package com.skylaker.yunzhi.pojo;

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
    private List<QuestionDetail> questions;

    //需要展示的问题总数量
    private Long sum;


    public QuestionsList(List<QuestionDetail> questions, Long sum) {
        this.questions = questions;
        this.sum = sum;
    }

    public List<QuestionDetail> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDetail> questions) {
        this.questions = questions;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }
}
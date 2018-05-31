package com.skylaker.yunzhi.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 问题回答集合封装POJO
 *
 * User: zhuyong
 * Date: 2018/5/31 10:30
 */
public class AnswersList implements Serializable {
    private static final long serialVersionUID = 1L;

    //问题所有回答集合（当前查询页）
    private List<AnswerDetail> answersDetailList;
    //回答总数量
    private Long sum;


    public AnswersList(List<AnswerDetail> answersDetailList, Long sum) {
        this.answersDetailList = answersDetailList;
        this.sum = sum;
    }

    public List<AnswerDetail> getAnswersDetailList() {
        return answersDetailList;
    }

    public void setAnswersDetailList(List<AnswerDetail> answersDetailList) {
        this.answersDetailList = answersDetailList;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }
}
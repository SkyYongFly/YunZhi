package com.skylaker.yunzhi.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 热门问题POJO
 *
 * User: zhuyong
 * Date: 2018/5/30 15:30
 */
public class HotQuestionsList  implements Serializable {
    private static final long serialVersionUID = 1L;

    //热门回答集合
    private List<QuestionDetail> hotQuestionDetaislList;
    //需要展示的问题总数量
    private Long sum;
    //缓存标识
    private String token;


    public HotQuestionsList(List<QuestionDetail> hotQuestionDetaislList, Long sum, String token){
        this.hotQuestionDetaislList = hotQuestionDetaislList;
        this.sum = sum;
        this.token = token;
    }

    public List<QuestionDetail> getHotQuestionDetaislList() {
        return hotQuestionDetaislList;
    }

    public void setHotQuestionDetaislList(List<QuestionDetail> hotQuestionDetaislList) {
        this.hotQuestionDetaislList = hotQuestionDetaislList;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
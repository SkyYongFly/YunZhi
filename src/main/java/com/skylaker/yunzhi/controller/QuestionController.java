package com.skylaker.yunzhi.controller;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.pojo.Question;
import com.skylaker.yunzhi.pojo.QuestionDetail;
import com.skylaker.yunzhi.pojo.TableData;
import com.skylaker.yunzhi.service.IQuestionService;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/24 22:26
 * Description:
 *      问题相关逻辑处理
 */
@Controller
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    @Qualifier("questionServiceImpl")
    private IQuestionService questionService;


    /**
     * 新增问题
     *
     * @param  question 提交的问题
     * @return  {enum}  操作结果
     */
    @RequestMapping(value = "/addQuestion", method = RequestMethod.POST)
    public @ResponseBody JSONObject addQuestion(@RequestBody Question question){
        return BaseUtil.getResult(questionService.addQuestion(question));
    }


    /**
     * 获取系统中的最新问题
     *
     * @return
     */
    @RequestMapping(value = "/getNewestQuestions", method = RequestMethod.GET)
    public @ResponseBody TableData getNewestQuestions(){
        return new TableData(questionService.getNewestQuestions());
    }

    /**
     * （分页）获取最新的问题列表，包括问题提问用户信息、问题标题、内容
     *
     * @return
     */
    @RequestMapping(value = "/getNewestQuestionsDetails", method = RequestMethod.GET)
    public @ResponseBody List<QuestionDetail> getNewestQuestionsDetails(@RequestParam("page")int page, @RequestParam("time") long time){
        return questionService.getNewestQuestionsDetails(page, time);
    }

    /**
     * 查询某个问题的详细信息
     *
     * @param qid 问题ID
     * @return
     */
    @RequestMapping(value = "/getQuestionDetail", method = RequestMethod.GET)
    public @ResponseBody Question getQuestionDetail(@RequestParam("qid")String qid){
        return questionService.getQuestionDetail(qid);
    }
}
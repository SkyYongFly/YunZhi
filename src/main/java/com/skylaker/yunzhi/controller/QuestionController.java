package com.skylaker.yunzhi.controller;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.pojo.*;
import com.skylaker.yunzhi.service.IHotQuestionService;
import com.skylaker.yunzhi.service.IQuestionService;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @Resource(name = "hotQuestionServiceImpl")
    private IHotQuestionService hotQuestionService;

    //TODO 注意查询从缓存查找，无则再查数据库

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
    public @ResponseBody NewestQuestionsList getNewestQuestionsDetails(@RequestParam("page")int page, @RequestParam("time") long time){
        //获取当前页面显示的问题信息
        List<QuestionDetail> questionsList = questionService.getNewestQuestionsDetails(page, time);
        //获取要展示的问题总数量
        Long sum = questionService.getNewestQuestionsCount(time);

        return new NewestQuestionsList(questionsList, sum);
    }

    /**
     * 获取当前时刻最热门问题（需要考虑热门问题并行修改对分页查询的影响）
     *
     * @param page      分页查询页数
     * @param token     标识：标识当前页面是否请求过最热门问题，用于决定是否生成临时缓存来记录热门问题
     * @return
     */
    @RequestMapping(value = "/getHotQuestionsDetails", method = RequestMethod.GET)
    public @ResponseBody HotQuestionsList getHotQuestionsDetails(@RequestParam("page")int page, @RequestParam("token") String token){
        return hotQuestionService.getHotQuestionsDetailsByPage(page, token);
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
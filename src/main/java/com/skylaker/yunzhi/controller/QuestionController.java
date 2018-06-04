package com.skylaker.yunzhi.controller;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.pojo.com.TableData;
import com.skylaker.yunzhi.pojo.db.Question;
import com.skylaker.yunzhi.pojo.db.QuestionsList;
import com.skylaker.yunzhi.service.IQuestionService;
import com.skylaker.yunzhi.service.aop.IndexAnnotation;
import com.skylaker.yunzhi.service.aop.LegalAnnotation;
import com.skylaker.yunzhi.service.aop.LogAnnotation;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 问题相关逻辑处理
 *
 * User: zhuyong
 * Date: 2018/5/24 22:26
 */
@Controller
@RequestMapping("/question")
public class QuestionController {
    @Resource(name = "questionServiceImpl")
    private IQuestionService questionService;

    //TODO 注意查询从缓存查找，无则再查数据库

    /**
     * 新增问题
     *
     * @param  question 提交的问题
     * @return  {enum}  操作结果
     */
    @LegalAnnotation(type = GlobalConstant.QUESTION)
    @IndexAnnotation(type = GlobalConstant.QUESTION, action = GlobalConstant.ADD)
    @LogAnnotation(type = "问题", action = "提问问题")
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
    public @ResponseBody
    TableData getNewestQuestions(){
        return new TableData(questionService.getNewestQuestions());
    }

    /**
     * （分页）获取最新的问题列表，包括问题提问用户信息、问题标题、内容
     *
     * @return
     */
    @LogAnnotation(type = "问题", action = "查看最新的问题")
    @RequestMapping(value = "/getNewestQuestionsDetails", method = RequestMethod.GET)
    public @ResponseBody
    QuestionsList getNewestQuestionsDetails(@RequestParam("page")int page, @RequestParam("time") long time){
        return  questionService.getNewestQuestionsDetails(page, time);
    }

    /**
     * 获取当前时刻最热门问题（需要考虑热门问题并行修改对分页查询的影响）
     *
     * @param page      分页查询页数
     * @param token     标识：标识当前页面是否请求过最热门问题，用于决定是否生成临时缓存来记录热门问题
     * @return
     */
    @LogAnnotation(type = "问题", action = "查看热门问题")
    @RequestMapping(value = "/getHotQuestionsDetails", method = RequestMethod.GET)
    public @ResponseBody QuestionsList getHotQuestionsDetails(@RequestParam("page")int page, @RequestParam("token") String token){
        return questionService.getHotQuestionsByPage(page, token);
    }

    /**
     * 查询某个问题的详细信息
     *
     * @param qid 问题ID
     * @return
     */
    @LogAnnotation(type = "问题", action = "查看问题详情")
    @RequestMapping(value = "/getQuestionDetail", method = RequestMethod.GET)
    public @ResponseBody Question getQuestion(@RequestParam("qid")int qid){
        return questionService.getQuestion(qid);
    }

    /**
     * 查询用户提问的问题
     *
     * @param  page  分页查询页码
     * @return
     */
    @LogAnnotation(type = "问题", action = "查看自己提问的问题")
    @RequestMapping(value = "/getUserQuestions", method = RequestMethod.GET)
    public @ResponseBody QuestionsList getUserQuestions(@RequestParam("page")int page){
        return questionService.getUserQuestions(page);
    }
}
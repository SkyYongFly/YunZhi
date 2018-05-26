package com.skylaker.yunzhi.controller;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.pojo.Question;
import com.skylaker.yunzhi.service.IQuestionService;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
}
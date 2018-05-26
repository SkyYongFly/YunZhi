package com.skylaker.yunzhi.controller;

import com.skylaker.yunzhi.pojo.BaseResult;
import com.skylaker.yunzhi.pojo.Question;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/24 22:26
 * Description:
 *      问题相关逻辑处理
 */
@RequestMapping("/question")
public class QuestionController {

    /**
     * 新增问题
     *
     * @param  question 提交的问题
     * @return  {enum}  操作结果
     */
    @RequestMapping(value = "/addQuestion", method = RequestMethod.POST)
    public @ResponseBody BaseResult addQuestion(@RequestBody Question question){

        return null;
    }
}
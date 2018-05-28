package com.skylaker.yunzhi.controller;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.pojo.Answer;
import com.skylaker.yunzhi.pojo.BaseResult;
import com.skylaker.yunzhi.service.IAnswerService;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/28 20:24
 * Description:
 *      问题回答请求控制器
 */
@Controller
@RequestMapping("/answer")
public class AnswerController {
    @Autowired
    @Qualifier("answerServiceImpl")
    private IAnswerService answerService;


    /**
     * 新增问题回答请求
     *
     * @param answer
     * @return
     */
    @RequestMapping(value = "/addAnswer", method = RequestMethod.POST)
    public @ResponseBody JSONObject addAnswer(@RequestBody Answer answer){
        if(BaseUtil.isNullOrEmpty(String.valueOf(answer.getQid())) || BaseUtil.isNullOrEmpty(answer.getText())){
            return BaseUtil.getResult(BaseResult.FAILTURE);
        }

        return BaseUtil.getResult(answerService.addAnswer(answer));
    }

}
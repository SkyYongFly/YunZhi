package com.skylaker.yunzhi.controller;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.pojo.db.Answer;
import com.skylaker.yunzhi.pojo.db.AnswersList;
import com.skylaker.yunzhi.pojo.res.BaseResult;
import com.skylaker.yunzhi.service.IAnswerService;
import com.skylaker.yunzhi.service.aop.IndexAnnotation;
import com.skylaker.yunzhi.service.aop.LegalAnnotation;
import com.skylaker.yunzhi.service.aop.LogAnnotation;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 问题回答请求控制器
 *
 * User: zhuyong
 * Date: 2018/5/28 20:24
 */
@Controller
@RequestMapping("/answer")
public class AnswerController {
    @Resource(name = "answerServiceImpl")
    private IAnswerService answerService;


    /**
     * 新增问题回答请求
     *
     * @param answer
     * @return
     */
    @LegalAnnotation(type = GlobalConstant.ANSWER)
    @IndexAnnotation(type = GlobalConstant.ANSWER, action = GlobalConstant.ADD)
    @LogAnnotation(type = "问题回答", action = "新增回答")
    @RequestMapping(value = "/addAnswer", method = RequestMethod.POST)
    public @ResponseBody JSONObject addAnswer(@RequestBody Answer answer){
        if(BaseUtil.isNullOrEmpty(String.valueOf(answer.getQid())) || BaseUtil.isNullOrEmpty(answer.getText())){
            return BaseUtil.getResult(BaseResult.FAILTURE);
        }

        return BaseUtil.getResult(answerService.addAnswer(answer));
    }

    /**
     *  获取问题的所有回答，按照回答点赞数倒序排序
     *
     * @param page  分页查询页码
     * @param qid   问题ID
     *
     * @return
     */
    @LogAnnotation(type = "问题回答", action = "查询问题所有回答")
    @RequestMapping(value = "/getQuestionAllAnswers", method = RequestMethod.GET)
    public @ResponseBody AnswersList getQuestionAllAnswers(@RequestParam("page")int page, @RequestParam("qid")int qid){
        if(BaseUtil.isNullOrEmpty(Integer.toString(qid))){
            return null;
        }

        return answerService.getQuestionAllAnswers(page, qid);
    }


    /**
     * 对回答进行点赞操作，返回最新的点赞数
     *
     * @param   aid   回答ID
     * @param   qid   问题ID
     * @return
     */
    @LogAnnotation(type = "问题回答", action = "给回答点赞")
    @RequestMapping(value = "/starAction", method = RequestMethod.GET)
    public @ResponseBody JSONObject starAction(@RequestParam("aid")Integer aid, @RequestParam("qid")Integer qid){
        if(BaseUtil.isNullOrEmpty(String.valueOf(aid))){
            return BaseUtil.getResult(BaseResult.FAILTURE);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("stars", answerService.starAction(aid, qid));

        return jsonObject;
    }
}
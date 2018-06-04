package com.skylaker.yunzhi.service.aop;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.pojo.db.Answer;
import com.skylaker.yunzhi.pojo.db.Question;
import com.skylaker.yunzhi.pojo.res.BaseResult;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * 内容检索索引分析处理逻辑，主要定义AOP拦截，具体交给异步队列Lucene处理
 *
 * User: zhuyong
 * Date: 2018/6/4 10:38
 */
@Service
@Aspect
public class IndexService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private RabbitTemplate rabbitTemplate;

    //拦截标注了索引注解的方法
    @Pointcut("@annotation(com.skylaker.yunzhi.service.aop.IndexAnnotation)")
    private void point(){};


    /**
     * 定义后置通知，获取需要进行索引的内容
     *
     * @param joinPoint
     * @return
     */
    @AfterReturning(pointcut = "point()", returning = "res")
    public void resolveIndexMethod(JoinPoint joinPoint, Object res){
        //获取执行结果
        if(null != res){
            if(res instanceof JSONObject){
                JSONObject jsonObject = (JSONObject) res;
                int code = (int) jsonObject.get("code");

                if(BaseResult.SUCCESS.getCode() != code){
                    logger.info("检索对象执行失败，取消检索处理！");
                    return;
                }
            }
        }

        //获取参数
        Object[] args = joinPoint.getArgs();
        if(null == args || 0 == args.length){
            return;
        }

        //获取注解信息
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        IndexAnnotation indexAnnotation = method.getAnnotation(IndexAnnotation.class);
        //对象类型以及操作
        String type = indexAnnotation.type();
        String action = indexAnnotation.action();

        if(GlobalConstant.QUESTION.equals(type)){
            //获取新增的问题
            Question question = (Question) args[0];
            //发送到MQ处理
            rabbitTemplate.convertAndSend("exchange_topic_index",
                    "index.question." + action, BaseUtil.getJSONObJect(question).toJSONString());

        }else if(GlobalConstant.ANSWER.equals(type)){
            //获取回答
            Answer answer = (Answer) args[0];
            //交由回答MQ处理
            rabbitTemplate.convertAndSend("exchange_topic_index",
                    "index.answer." + action, BaseUtil.getJSONObJect(answer).toJSONString());

        }else{
            //do other
        }
    }

}
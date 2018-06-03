package com.skylaker.yunzhi.service.aop;

import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.exception.LegalException;
import com.skylaker.yunzhi.pojo.db.Answer;
import com.skylaker.yunzhi.pojo.db.Question;
import com.skylaker.yunzhi.service.IRedisService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.management.QueryEval;
import java.lang.reflect.Method;
import java.rmi.dgc.Lease;

/**
 * 内容合法性验证逻辑
 *
 * User: zhuyong
 * Date: 2018/6/3 14:42
 */
@Service
@Aspect
public class LegalService {
    @Resource(name = "redisServiceImpl")
    private IRedisService redisService;

    private Logger logger = LoggerFactory.getLogger(LegalService.class);


    /**
     * 拦截标注合法性验证注解内容
     */
    @Pointcut("@annotation(com.skylaker.yunzhi.service.aop.LegalAnnotation)")
    private void point(){};


    /**
     * 方法执行之前验证内容合法性，如果不合法抛异常处理
     *
     * @param joinPoint
     * @return
     */
    @Around("point()")
    public Object aroud(ProceedingJoinPoint joinPoint) throws LegalException{
        //获取方法参数
        Object[] args = joinPoint.getArgs();

        //获取注解的方法类型
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        LegalAnnotation legalAnnotation =  method.getAnnotation(LegalAnnotation.class);
        String type = legalAnnotation.type();

        //内容合法性验证
        //TODO 实际需要调用敏感词校验接口，这里只做示例
        if(GlobalConstant.QUESTION.equals(type)){ //问题合法性判断
            Question question = (Question) args[0];

            if(!redisService.legalValidate(question.getTitle()) || !redisService.legalValidate(question.getText())){
                logger.info("用户提问问题非法！");
                throw new LegalException("用户提问问题非法");
            }
        }else if(GlobalConstant.ANSWER.equals(type)){ //回答合法性判断
            Answer answer = (Answer) args[0];

            if(!redisService.legalValidate(answer.getText())){
                logger.info("用户回答内容非法！");
                throw new LegalException("用户回答内容非法");
            }
        }else{
            //TODO
        }

        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return throwable;
        }
    }
}
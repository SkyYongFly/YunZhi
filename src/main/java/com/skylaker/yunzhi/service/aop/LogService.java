package com.skylaker.yunzhi.service.aop;

import com.skylaker.yunzhi.pojo.com.LogInfo;
import com.skylaker.yunzhi.pojo.db.User;
import com.skylaker.yunzhi.utils.BaseUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * 系统日志具体处理类
 *
 * User: zhuyong
 * Date: 2018/6/3 12:16
 */
@Service
@Aspect
public class LogService {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 对标注日志处理注解的方法进行处理
     */
    @Pointcut("@annotation(com.skylaker.yunzhi.service.aop.LogAnnotation)")
    private void point(){};


    /**
     * 环绕通知：拦截具体方法，获取日志信息，进行处理
     *
     * @param   joinPoint
     * @return
     */
    @Around("point()")
    public Object around(ProceedingJoinPoint joinPoint){
        //获取日志注解信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);

        try {
            Object result = joinPoint.proceed();

            //TODO 可以根据参数获取更具体的信息
            //Object[] args = joinPoint.getArgs();

            //设置日志信息
            LogInfo log = new LogInfo(logAnnotation.type(), logAnnotation.action());

            User user = BaseUtil.getSessionUser();
            if(null != BaseUtil.getSessionUser()){
                log.setUserid(user.getId());
                log.setUsername(user.getUsername());
            }else{
                log.setUserid(-1);
                log.setUsername("访客用户");
            }

            //发送到MQ进行处理
            rabbitTemplate.convertAndSend("exchange_direct_log", "loginfo", log.getJSONString());

            return result;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return throwable;
        }
    }
}
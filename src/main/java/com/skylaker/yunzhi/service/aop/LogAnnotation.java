package com.skylaker.yunzhi.service.aop;

import java.lang.annotation.*;

/**
 * 日志操作注解
 *
 * User: zhuyong
 * Date: 2018/6/3 12:04
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    //业务操作类型，例如人员操作、问题操作
    String type() default "";

    //具体操作类型，例如提问问题、写回答
    String action() default "";
}
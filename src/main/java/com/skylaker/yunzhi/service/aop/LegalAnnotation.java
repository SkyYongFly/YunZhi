package com.skylaker.yunzhi.service.aop;

import java.lang.annotation.*;

/**
 * 提问问题、回答合法性验证注解
 *
 * User: zhuyong
 * Date: 2018/6/3 14:38
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LegalAnnotation {
    //标明是提问问题还是回答
    String type() default "";
}
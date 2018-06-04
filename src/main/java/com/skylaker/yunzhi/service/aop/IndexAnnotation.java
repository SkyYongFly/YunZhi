package com.skylaker.yunzhi.service.aop;

import java.lang.annotation.*;

/**
 * 需要进行Lucene索引分析的标识注解
 *
 * User: zhuyong
 * Date: 2018/6/4 10:34
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IndexAnnotation {
    //标识需要进行索引分析的对象类型
    String type() default "";

    //具体操作：添加、删除、修改
    String action()  default "";
}
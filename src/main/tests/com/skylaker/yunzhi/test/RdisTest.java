package com.skylaker.yunzhi.test;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/17
 * Time: 23:17
 * Description:
 */
public class RdisTest {
    @Test
    public void testString(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("springredis.xml");
        RedisTemplate<String, Object> redisTemplate = context.getBean("redisTemplate", RedisTemplate.class);

       redisTemplate.opsForValue().set("name", "xiaoming");
        System.out.println(redisTemplate.opsForValue().get("name"));

    }
}
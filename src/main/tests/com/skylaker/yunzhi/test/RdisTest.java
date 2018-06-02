package com.skylaker.yunzhi.test;

import com.skylaker.yunzhi.service.impl.RegisterServiceImpl;
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

    @Test
    public void testRegister(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        RegisterServiceImpl registerService = context.getBean("registerServiceImpl", RegisterServiceImpl.class);

        for (int i = 0; i < 20; i++){
            System.out.println(registerService.getVercode("1769213396" + i));
        }
    }

    @Test
    public void testRegister2(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        RegisterServiceImpl registerService = context.getBean("registerServiceImpl", RegisterServiceImpl.class);

        for (int i = 0; i < 200000; i++){
            MyThread myThread = new MyThread(registerService);
            myThread.run();

//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }

    class MyThread implements Runnable{
        RegisterServiceImpl registerService = null;

        MyThread(RegisterServiceImpl registerService){
            this.registerService = registerService;
        }

        @Override
        public void run() {
            System.out.println(registerService.getVercode("17692133968"));
        }
    }

    @Test
    public void testRedis(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
    }
}
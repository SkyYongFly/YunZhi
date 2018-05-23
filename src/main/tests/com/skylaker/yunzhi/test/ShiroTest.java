package com.skylaker.yunzhi.test;

import com.skylaker.yunzhi.utils.BaseUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/9
 * Time: 19:25
 * Description:
 *      Shiro框架测试类
 */
public class ShiroTest {
    @Test
    public void testShiro_1(){
        //1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro_realm.ini");

        //2、得到SecurityManager实例 并绑定给SecurityUtils
        SecurityManager manager = factory.getInstance();
        SecurityUtils.setSecurityManager(manager);

        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

        try{
            //4、登录，即身份验证
            subject.login(token);
        }catch (AuthenticationException e){
            //5、身份验证失败
        }

        //断言用户已经登录
        Assert.assertEquals(true , subject.isAuthenticated());

        //6、退出
        subject.logout();
    }

    @Test
    public void testMd5(){
    }

}
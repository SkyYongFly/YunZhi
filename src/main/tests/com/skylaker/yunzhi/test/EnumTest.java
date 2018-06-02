package com.skylaker.yunzhi.test;

import com.skylaker.yunzhi.pojo.res.LoginResult;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/12
 * Time: 12:38
 * Description:
 */
public class EnumTest {
    @Test
    public void testEnum(){
        System.out.println(LoginResult.getMessage(2));
    }

    @Test
    public void testEnum2(){
        System.out.println(LoginResult.SUCCESS);
        System.out.println(LoginResult.SUCCESS == LoginResult.SUCCESS);
    }
}
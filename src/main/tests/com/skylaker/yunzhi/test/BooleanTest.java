package com.skylaker.yunzhi.test;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/12
 * Time: 10:48
 * Description:
 */
public class BooleanTest {
    @Test
    public  void testBoolean(){
        System.out.println(Boolean.TRUE == Boolean.TRUE);
        System.out.println(Boolean.TRUE == true);
        System.out.println(Boolean.TRUE.equals(Boolean.TRUE));
        System.out.println(Boolean.TRUE.equals(true));
    }
}
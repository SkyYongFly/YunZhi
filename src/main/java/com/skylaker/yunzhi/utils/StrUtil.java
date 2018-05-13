package com.skylaker.yunzhi.utils;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/13
 * Time: 17:42
 * Description: 字符串工具类
 */
public class StrUtil {

    /**
     * 判断字符串是否为空
     *
     * @param   str       需要判断的字符串
     * @return  {boolean} 空：true ； 非空 ：false
     */
    public static boolean isNullOrEmpty(String str){
        if(null == str || "".equals(str)){
            return  true;
        }

        return false;
    }
}
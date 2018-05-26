package com.skylaker.yunzhi.utils;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.pojo.IResult;
import com.skylaker.yunzhi.pojo.RegisterInfo;
import com.skylaker.yunzhi.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.util.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/13
 * Time: 17:42
 * Description: 字符串验证等常用函数工具类
 */
public class BaseUtil {

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

    /**
     * 验证是否为手机号
     *
     * @param   phone       手机号
     * @return  {boolean}   是：true ； 否：false
     */
    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        }

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * 验证用户名格式是否正确
     * 用户名格式：2到10位数字、字母、汉字组成
     *
     * @param   username    用户名
     * @return  {boolean}   正确：true ；不正确：false
     */
    public static boolean validateUserName(String username) {
        String regex = "^(?!_)(?!.*?_$)[a-zA-Z0-9_\\u4e00-\\u9fa5]{2,10}$";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(username);
        return m.matches();
    }

    /**
     * 验证密码是否正确
     * 密码要求：6到20位非重复数字或者字母组成，不能纯数字或者字母
     *
     * @param   password    密码
     * @return  {boolean}   正确：true ；不正确：false
     */
    public static boolean validatePassword(String password) {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * 获取系统请求操作结果，JSON格式数据
     *
     * @param   iResult
     * @return
     */
    public static JSONObject getResult(IResult iResult){
        //返回异常结果JSON数据
        JSONObject result = new JSONObject();
        result.put("code", iResult.getCode());
        result.put("message", iResult.getMessage());

        return result;
    }

    /**
     * 获取session中缓存的用户
     *
     * @return
     */
    public static User getSessionUser(){
        Subject subject = SecurityUtils.getSubject();
        return  (User) subject.getSession().getAttribute(GlobalConstant.SESSION_USER_NAME);
    }
}
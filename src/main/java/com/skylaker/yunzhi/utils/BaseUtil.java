package com.skylaker.yunzhi.utils;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.config.GlobalConstant;
import com.skylaker.yunzhi.pojo.res.IResult;
import com.skylaker.yunzhi.pojo.db.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.persistence.Column;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串验证等常用函数工具类
 *
 * User: zhuyong
 * Date: 2018/5/13 17:42
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

    /**
     * 获取当前用户查询最热门问题时缓存热门问题的zset
     *
     * @return  热门问题zset键值
     */
    public static String getCacheHotQuestionsKey(){
        return  GlobalConstant.REDIS_ZSET_QUESTIONS_HOT  + "_" +  BaseUtil.getSessionUser().getId();
    }

    /**
     * 获取redis缓存问题回答信息的key名称
     *
     * @param   qid  问题ID
     * @return
     */
    public static Object getRedisQuestionAnswersKey(Integer qid) {
        return GlobalConstant.REDIS_ZSET_QUESTION_ANSWERS + qid;
    }

    /**
     * 获取保存用户点赞的回答信息的redis键名
     *
     * @return
     */
    public static String getUserStaredAnswersKey(){
        return  GlobalConstant.REDIS_SET_STAR_ANSWERS + BaseUtil.getSessionUser().getId();
    }

    /**
     * 获取保存用户提问问题的redis键名
     *
     * @return
     */
    public static String getUserQuestionsKey(Integer userId){
        return  GlobalConstant.REDIS_SET_USER_QUESTIONS + userId;
    }

    /**
     * 获取UUID
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * 获取properties配置中属性
     *
     * @return
     */
    public static String getPropertyValue(String propertiesName, Object property){
        Properties properties = new Properties();
        InputStream inputStream = BaseUtil.class.getClassLoader().getResourceAsStream(propertiesName);

        try {
            properties.load(inputStream);

            return (String) properties.get(property);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 利用反射获取对象属性值组成的JSON对象，针对与持久层属性
     *
     * @param   object
     * @return
     */
    public static JSONObject getJSONObJect(Object object){
        Class<?> objClass = object.getClass();
        Field[] fields = objClass.getDeclaredFields();

        JSONObject jsonObject = new JSONObject();

        Map<Object, Object> fieldMap = getObjFieldMap(object);

        Set<Map.Entry<Object, Object>> entrySet = fieldMap.entrySet();
        for (Map.Entry<Object, Object> entry : entrySet){
            //添加成员变量属性信息键值对
            jsonObject.put(entry.getKey().toString(), entry.getValue());
        }

        return jsonObject;
    }

    /**
     * 利用反射获取对象属性名称以及值
     *
     * @param object
     * @return
     */
    public static Map<Object, Object> getObjFieldMap(Object object){
        Class<?> objClass = object.getClass();
        Field[] fields = objClass.getDeclaredFields();

        Map<Object, Object> resultMap = new ConcurrentHashMap<>();

        for(Field field : fields){
            //获取成员变量属性名称
            String fieldName = field.getName();

            //得到获取成员变量属性值的方法
            String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Object fieldValue = null;
            try {
                Method method = objClass.getDeclaredMethod(methodName, null);
                //判断是否是持久层属性字段
                Annotation annotation = field.getAnnotation(Column.class);
                if(null != annotation){
                    fieldValue = method.invoke(object);
                }
            } catch (NoSuchMethodException e) {
                continue;
            } catch (IllegalAccessException e) {
                continue;
            } catch (InvocationTargetException e) {
                continue;
            }

            //添加成员变量属性信息键值对
            if(null != fieldName && null != fieldValue){
                resultMap.put(fieldName, fieldValue);
            }
        }

        return  resultMap;
    }
}
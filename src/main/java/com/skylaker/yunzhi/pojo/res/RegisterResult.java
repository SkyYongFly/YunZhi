package com.skylaker.yunzhi.pojo.res;

import com.alibaba.fastjson.JSONObject;
import com.skylaker.yunzhi.pojo.res.IResult;
import com.skylaker.yunzhi.pojo.res.LoginResult;

/**
 * 用户注册结果返回信息封装实体
 *
 * User: zhuyong
 * Date: 2018/5/20 11:56
 */
public enum RegisterResult implements IResult {
    //成功
    SUCCESS("成功", 1) ,

    //不正确的手机号
    INVALIDATE_PHONE("手机号格式不正确", 2),

    //手机号已经注册过
    PHONE_HAS_REGISTER("手机号已经注册过", 3),

    //请求过于频繁
    TO_MUCH_COUNT("请求过于频繁", 4),

    //用户名格式不正确
    INVALIDATE_USERNAME("用户名格式不正确", 5),

    //密码格式不正确
    INVALIDATE_PASSWORD("密码格式不正确", 6),

    //验证码不正确
    INVALIDATE_VERCODE("验证码不正确", 7),

    //未知原因注册失败
    REGISTER_FAILURE("服务器错误导致注册失败", 8);



    //结果信息
    private  String message;
    //结果编码
    private  int code;

    private RegisterResult(String message, int code){
        this.message = message;
        this.code = code;
    }

    public static String getMessage(int code){
        for (LoginResult resultCode : LoginResult.values()){
            if(resultCode.getCode() == code){
                return resultCode.getMessage();
            }
        }

        return  null;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("message", message);
        json.put("code", code);

        return  json.toString();
    }
}

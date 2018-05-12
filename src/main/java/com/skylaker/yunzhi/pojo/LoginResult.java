package com.skylaker.yunzhi.pojo;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/12
 * Time: 12:52
 * Description:
 */
public enum LoginResult {
    //成功
    SUCCESS("成功", 1) ,

    //密码错误
    INCORRECT_PWD("密码错误", 2),

    //输错密码次数过多
    TO_MUCH_ERROR("输错密码达5次", 3),

    //账号不存在
    NO_ACCOUNT("账号不存在", 4);

    //结果信息
    private  String message;
    //结果编码
    private  int code;

    private LoginResult(String message, int code){
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

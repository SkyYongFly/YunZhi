package com.skylaker.yunzhi.pojo;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/28
 * Description: 通用返回结果状态
 */
public enum BaseResult implements IResult{
    //成功
    SUCCESS("成功", 1) ,

    //密码错误
    FAILTURE("失败", 2);

    //结果信息
    private  String message;
    //结果编码
    private  int code;

    private BaseResult(String message, int code){
        this.message = message;
        this.code = code;
    }

    public static String getMessage(int code){
        for (BaseResult resultCode : BaseResult.values()){
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

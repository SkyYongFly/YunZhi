package com.skylaker.yunzhi.pojo.res;

/**
 * 问题操作结果返回状态
 *
 * User: zhuyong
 * Date: 2018/5/26
 */
public enum QuestionResult implements IResult {
    //成功
    SUCCESS("成功", 1) ,

    //问题标题为空
    NULL_TITLE("标题为空", 2),

    //问题话题为空
    NULL_TOPIC("话题为空", 3),

    //未知原因
    FAILTURE("未知原因", 4);

    //结果信息
    private  String message;
    //结果编码
    private  int code;

    private QuestionResult(String message, int code){
        this.message = message;
        this.code = code;
    }

    public static String getMessage(int code){
        for (QuestionResult resultCode : QuestionResult.values()){
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
}

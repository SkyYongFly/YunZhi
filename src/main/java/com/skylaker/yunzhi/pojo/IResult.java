package com.skylaker.yunzhi.pojo;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/26 14:33
 * Description:
 *      操作结果返回内容定义接口
 */
public interface IResult {
    //返回标识
    int code = 0;

    //返回信息
    String message = null;

    /**
     * 获取结果标识
     *
     * @return
     */
    int getCode();

    /**
     * 获取结果信息
     *
     * @return
     */
    String getMessage();
}

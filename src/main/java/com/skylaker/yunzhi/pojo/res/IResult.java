package com.skylaker.yunzhi.pojo.res;

/**
 * 操作结果返回内容定义接口
 *
 * User: zhuyong
 * Date: 2018/5/26 14:33
 */
public interface IResult {
    /**
     * 获取结果标识
     *
     * @return
     */
    Integer getCode();

    /**
     * 获取结果信息
     *
     * @return
     */
    String getMessage();
}

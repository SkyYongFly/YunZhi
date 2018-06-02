package com.skylaker.yunzhi.pojo.com;

import java.util.List;

/**
 * Layui table返回数据封装
 *
 * User: zhuyong
 * Date: 2018/5/27 11:25
 */
public class TableData <T>{
    private int code = 0;

    private String msg = "";

    private int count = 1000;

    private List<T> data;


    public TableData(List<T> data){
        this.data = data;
        this.count = data.size();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
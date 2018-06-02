package com.skylaker.yunzhi.pojo.com;

import org.apache.ibatis.type.Alias;

/**
 * 分页查询分页信息封装POJO
 *
 * User: zhuyong
 * Date: 2018/5/31 10:47
 */
@Alias("pageinfo")
public class PageInfo {
    //第几页
    private Integer pageIndex;
    //每页显示数量
    private Integer pageSize;
    //每页开始位置
    private Integer start;

    //业务记录ID
    private Integer id;
    //用户ID
    private Integer userid;


    public PageInfo() {
    }

    public PageInfo(Integer pageIndex, Integer pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;

        this.start = (pageIndex - 1) * pageSize;
    }

    public PageInfo(Integer pageIndex, Integer pageSize, Integer id) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.id = id;

        this.start = (pageIndex - 1) * pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
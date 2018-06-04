package com.skylaker.yunzhi.service;

import com.skylaker.yunzhi.pojo.res.IResult;

import java.util.List;

/**
 * Lucene处理逻辑接口定义
 *
 * User: zhuyong
 * Date: 2018/6/4 13:39
 */
public interface ILuceneService {
    /**
     * 添加索引
     *
     * @param object
     * @return
     */
    IResult addIndex(Object object);

    /**
     * 根据关键词检索内容
     *
     * @return
     * @param words
     */
    List<Object> getSearchResult(String words);
}
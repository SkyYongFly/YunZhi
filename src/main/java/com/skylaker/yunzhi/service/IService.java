package com.skylaker.yunzhi.service;

import java.util.List;

/**
 * 基础通用接口，主要定义SQL操作
 *
 * User: zhuyong
 * Date: 2018/5/26 16:36
 */
public interface IService<T> {
    T selectByKey(Object key);

    int save(T entity);

    int delete(Object key);

    int updateAll(T entity);

    int updateNotNull(T entity);

    List<T> selectByExample(Object example);

    int getCount(Object example);
}
package com.skylaker.yunzhi.service.impl;

import com.skylaker.yunzhi.service.IRedisService;
import com.skylaker.yunzhi.service.IService;
import com.skylaker.yunzhi.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.beans.Transient;
import java.util.List;

/**
 * 逻辑处理公共类
 *
 * User: zhuyong
 * Date: 2018/5/26 16:34
 */
public class BaseService<T> implements IService<T> {
    @Resource(name = "redisServiceImpl")
    protected IRedisService redisService;

    @Autowired
    private Mapper<T> mapper;

    public Mapper<T> getMapper() {
        return mapper;
    }

    @Override
    public T selectByKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public int save(T entity) {
        return mapper.insert(entity);
    }

    @Override
    public int delete(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    @Override
    public int updateAll(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    @Override
    public int updateNotNull(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }

    @Override
    public int getCount(Object example) {
        return mapper.selectCountByExample(example);
    }

}
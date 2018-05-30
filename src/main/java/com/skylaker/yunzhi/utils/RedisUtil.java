package com.skylaker.yunzhi.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: zhuyong
 * Date: 2018/5/20 19:50
 * Description:
 *      redis操作工具类
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 判断hash是否存在指定键
     *
     * @param   hashKey
     * @param   key
     * @return
     */
    public boolean hasHashKey(Object hashKey, Object key){
        return redisTemplate.opsForHash().hasKey(hashKey, key);
    }

    /**
     * 获取hash指定键值
     *
     * @param hashKey
     * @param key
     * @return
     */
    public Object getHashValue(Object hashKey, Object key){
        return redisTemplate.opsForHash().get(hashKey, key);
    }

    /**
     * 添加hash键值
     *
     * @param hashKey
     * @param key
     * @param value
     */
    public void setHashValue(Object hashKey, Object key, Object value){
        redisTemplate.opsForHash().put(hashKey, key, value);
    }

    /**
     * 判断某个值是否存在于指定的set中
     * @param setKey
     * @param value
     * @return
     */
    public boolean existInSet(Object setKey, Object value) {
        return  redisTemplate.opsForSet().isMember(setKey, value);
    }

    /**
     * 往set中添加值
     *
     * @param setKey        目标set
     * @param itemValue     要添加的值
     */
    public void addSetValue(Object setKey, Object itemValue){
        redisTemplate.opsForSet().add(setKey, itemValue);
    }

    /**
     * 往 ZSET 中添加值
     *
     * @param zsetKey
     * @param itemKey
     * @param value
     */
    public void addZsetValue(Object zsetKey, Object itemKey, Double value){
        redisTemplate.opsForZSet().add(zsetKey, itemKey, value);
    }

    /**
     * zset 中指定键的值增加分值
     *
     * @param zsetKey   目标zset
     * @param itemKey   要添加分值的项
     * @param score     添加的分值
     */
    public void increaseZsetScore(Object zsetKey, Object itemKey, double score){
        redisTemplate.opsForZSet().incrementScore(zsetKey, itemKey, score);
    }

    /**
     * 返回指定索引区间内最大的几条记录对应的键
     *  @param zsetKey   目标zset
     * @param start     开始位置
     * @param end       结束位置
     */
    public Set<Object> getZsetMaxKeys(Object zsetKey, long start, long end){
         return redisTemplate.opsForZSet().reverseRange(zsetKey, start, end);
    }

    /**
     * 指定区间内分页查询zset键值信息
     *
     * @param zsetKey     目标zset
     * @param minValue    最小值
     * @param maxValue    最大值
     * @param index       开始索引位置
     * @param count       数量
     * @return
     */
    public Set<Object> getZsetMaxKeysInScoresWithPage(Object zsetKey, double minValue, double maxValue, long index, long count){
        return redisTemplate.opsForZSet().reverseRangeByScore(zsetKey, minValue, maxValue, index, count);
    }

    /**
     * 获取指定区间zset元素数量
     *
     * @param zsetKey   目标zset
     * @param minValue  最小值
     * @param maxValue  最大值
     *
     * @return  {Long}  元素数量
     */
    public Long getZsetCount(Object zsetKey, double minValue, double maxValue) {
         return redisTemplate.opsForZSet().count(zsetKey, minValue, maxValue);
    }
}
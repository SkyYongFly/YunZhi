package com.skylaker.yunzhi.utils;

import com.skylaker.yunzhi.config.GlobalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * redis操作工具类
 *
 * User: zhuyong
 * Date: 2018/5/20 19:50
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 判断指定zset是否存在
     *
     * @param zsetKey
     * @return
     */
    public boolean existZset(Object zsetKey){
        Long num = redisTemplate.opsForZSet().zCard(zsetKey);

        return Long.valueOf(0) == num ? false : true;
    }

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
     * 删除指定键
     *
     * @param key
     */
    public void deleteKey(String key){
        redisTemplate.delete(key);
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
     *
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
     * 返回指定索引区间内最大的几条记录对应的键
     *  @param zsetKey   目标zset
     * @param start     开始位置
     * @param end       结束位置
     */
    public Set<Object> getZsetMaxKeys(Object zsetKey, long start, long end){
         return redisTemplate.opsForZSet().reverseRange(zsetKey, start, end);
    }

    /**
     * 返回指定索引区间内最大的几条记录对应的键值对信息
     *
     *  @param zsetKey   目标zset
     * @param start     开始位置
     * @param end       结束位置
     */
    public Set<ZSetOperations.TypedTuple<Object>> getZsetMaxKeysOfScores(Object zsetKey, long start, long end){
         return redisTemplate.opsForZSet().reverseRangeWithScores(zsetKey, start, end);
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
     * 指定区间内分页查询zset键值信息，同时返回键、值信息
     *
     * @param zsetKey     目标zset
     * @param minValue    最小值
     * @param maxValue    最大值
     * @param index       开始索引位置
     * @param count       数量
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> getZsetMaxKeysInScoresOfScoreInfoWithPage(Object zsetKey, double minValue, double maxValue, long index, long count){
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(zsetKey, minValue, maxValue, index, count);
    }

    /**
     * 指定区间内查询zset键值信息
     *
     * @param zsetKey     目标zset
     * @param minValue    最小值
     * @param maxValue    最大值
     * @return
     */
    public Set<Object> getZsetMaxKeysInScores(Object zsetKey, double minValue, double maxValue){
        return redisTemplate.opsForZSet().reverseRangeByScore(zsetKey, minValue, maxValue);
    }

    /**
     * 指定区间内查询zset键值信息，同时返回键、值信息
     *
     * @param zsetKey     目标zset
     * @param minValue    最小值
     * @param maxValue    最大值
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> getZsetMaxKeysOfScoreInScores(Object zsetKey, double minValue, double maxValue){
        return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(zsetKey, minValue, maxValue);
    }

    /**
     * 指定区间内查询zset键值信息，同时返回键、值信息，由小到大排序
     *
     * @param zsetKey     目标zset
     * @param minValue    最小值
     * @param maxValue    最大值
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> getZsetMaxKeysOfScoreInScoresBySorted(Object zsetKey, double minValue, double maxValue){
        return redisTemplate.opsForZSet().rangeByScoreWithScores(zsetKey, minValue, maxValue);
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

    /**
     * 按照分值大小从大到小排序，然后将有序zset复制到指定zset中
     *
     * @param zsetKey      原zset
     * @param destionKey   目标zset
     *
     * @return  {Long}  合并元素数量
     */
    public Long cacheSortedZset(Object zsetKey, Object destionKey) {
        //将问题热门指数集合按照指数由小到大排序
        //注意这里的起始热门指数值不为0
        Set<ZSetOperations.TypedTuple<Object>> sortedCollection =
                getZsetMaxKeysOfScoreInScoresBySorted(zsetKey, GlobalConstant.MIN_HOT_INDEX, Double.POSITIVE_INFINITY);

        if(null == sortedCollection || 0 == sortedCollection.size()){
            return Long.valueOf(0);
        }

        //将排序集合设置到缓存集合中
        //注意这个添加后集合顺序由大到小
        return  redisTemplate.opsForZSet().add(destionKey, sortedCollection);
    }

    /**
     * 获取指定有序集合中某个key的分值
     *
     * @param zsetKey   有序集合键名
     * @param itemKey   需要查询的子项key
     * @return
     */
    public Double getZsetKeyValue(Object zsetKey, Object itemKey) {
        return redisTemplate.opsForZSet().score(zsetKey, itemKey);
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
     *  从无序集合set中去除指定项
     *
     * @param setKey     无序集合键名
     * @param itemKey    子项键名
     */
    public Long removeSetValue(Object setKey, Integer itemKey) {
        return redisTemplate.opsForSet().remove(setKey, itemKey);
    }

    /**
     * 获取无序集合元素数量
     *
     * @param setKey
     * @return
     */
    public Long getSetCount(Object setKey) {
        return redisTemplate.opsForSet().size(setKey);
    }
}
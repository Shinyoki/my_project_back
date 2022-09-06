package com.senko.framework.config.redis;

import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * RedisTemplate封装
 *
 * @author senko
 * @date 2022/9/4 8:22
 */
@Component
@SuppressWarnings("unused")
public class RedisHandler {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // ===========键值对===============

    /**
     * 是否存在
     * @param key   键
     * @return      是否存在
     */
    public Boolean containsKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 缓存对象
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存对象
     *
     * @param key     键
     * @param val     值
     * @param ttl 超时时间
     */
    public void set(String key, Object val, long ttl) {
        redisTemplate.opsForValue().set(key, val, ttl, TimeUnit.SECONDS);
    }

    /**
     * 缓存对象
     *
     * @param key      键
     * @param val      值
     * @param ttl  超时时间
     * @param timeUnit 时间单位
     */
    public void set(String key, Object val, long ttl, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, val, ttl, timeUnit);
    }

    /**
     * 自增
     *
     * @param key   键
     * @param delta 自增步长
     * @return 自增后的值
     */
    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 自减
     *
     * @param key   键
     * @param delta 自减步长
     * @return 自减后的值
     */
    public Long decrement(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    /**
     * 自增1，但超过1时会设置key的过期时间为ttl
     * 适用于限流场景
     * @param key       键
     * @param ttl       超时时间 单位：秒
     * @return          自增后的值
     */
    public Long incrementAndExpire(String key, long ttl) {

        // 自增
        Long count = increment(key, 1);
        // 是否超过1，超过则设置过期时间
        if (Objects.nonNull(count) && count > 1) {
            expire(key, ttl);
        }
        return count;

    }

    /**
     * 获取缓存对象
     *
     * @param key     键
     * @param ttl 超时时间
     */
    public Boolean expire(String key, long ttl) {
        return redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
    }

    /**
     * 获取剩余过期时间
     *
     * @param key 键
     * @return 剩余过期时间
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 获取缓存对象
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除缓存对象
     *
     * @param key 键
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除缓存对象
     *
     * @param keys 键集合
     */
    public Long deleteBath(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    // =========哈希桶===========

    /**
     * 是否存在该哈希桶
     *
     * @param key map名
     * @return true or false
     */
    public Boolean hashHasMap(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 存储键值对
     *
     * @param mapName map名
     * @param key     键
     * @param val     值
     */
    public void hashSet(String mapName, String key, Object val) {
        redisTemplate.opsForHash()
                .put(mapName, key, val);
    }

    /**
     * 获取键值对
     *
     * @param mapName map名
     * @param key     键
     * @return 值
     */
    public Object hashGet(String mapName, String key) {
        return redisTemplate.opsForHash()
                .get(mapName, key);
    }

    /**
     * 获取map中所有键值对
     *
     * @param mapName map名
     * @return map中所有键值对
     */
    public Map<?, ?> hashGetMap(String mapName) {
        return redisTemplate.opsForHash()
                .entries(mapName);
    }

    /**
     * 存储map
     *
     * @param mapName map名
     * @param map     map
     */
    public void hashSetMap(String mapName, Map<?, ?> map) {
        redisTemplate.opsForHash()
                .putAll(mapName, map);
    }

    /**
     * 删除map中的键值对
     *
     * @param mapName map名
     * @param key     键
     */
    public void hashDeleteKey(String mapName, String key) {
        redisTemplate.opsForHash()
                .delete(mapName, key);
    }

    public void hashDeleteMultiKey(String mapName, Collection<String> keys) {
        redisTemplate.opsForHash()
                .delete(mapName, keys.toArray());
    }

    /**
     * 给map中的键值对自增
     *
     * @param mapName map名
     * @param key     键
     * @param delta   自增步长
     * @return 自增后的值
     */
    public Long hashIncrement(String mapName, String key, Long delta) {
        return redisTemplate.opsForHash()
                .increment(mapName, key, delta);
    }

    /**
     * 给map中的键值对自减
     *
     * @param mapName map名
     * @param key     键
     * @param delta   自减步长
     * @return 自减后的值
     */
    public Long hashDecrement(String mapName, String key, Long delta) {
        return redisTemplate.opsForHash()
                .increment(mapName, key, -delta);
    }

    // ===========Sorted Set==========

    /**
     * 添加Set元素，并指定排序优先级
     *
     * @param key   键
     * @param value 值
     * @param score 优先级
     */
    public Double zSetIncrement(String key, Object value, Double score) {
        return redisTemplate.opsForZSet()
                .incrementScore(key, value, score);
    }

    public Double zSetDecrement(String key, Object value, Double score) {
        return redisTemplate.opsForZSet()
                .incrementScore(key, value, -score);
    }

    /**
     * 获取元素的Score
     *
     * @param key   键
     * @param value 值
     * @return Score
     */
    public Double zScore(String key, Object value) {
        return redisTemplate.opsForZSet()
                .score(key, value);
    }

    /**
     * zSet封装为  Element : Score 形式的Map
     * @param key       键
     * @param start     起始index
     * @param end       结束index
     */
    @Nullable
    public Map<Object, Double> zReverseRangeWithScore(String key, long start, long end) {
        Set<ZSetOperations.TypedTuple<Object>> typedTuples = redisTemplate.opsForZSet()
                .reverseRangeWithScores(key, start, end);
        if (Objects.isNull(typedTuples)) {
            return null;
        }
        return typedTuples.stream()
                .collect(Collectors.toMap(ZSetOperations.TypedTuple::getValue, ZSetOperations.TypedTuple::getScore));
    }

    @Nullable
    public Map<Object, Double> zAllScore(String key) {
        Set<ZSetOperations.TypedTuple<Object>> typedTuples = redisTemplate.opsForZSet()
                .rangeWithScores(key, 0, -1);
        if (Objects.isNull(typedTuples)) {
            return null;
        }
        return typedTuples.stream()
                .collect(Collectors.toMap(ZSetOperations.TypedTuple::getValue, ZSetOperations.TypedTuple::getScore));
    }

    // ==========Set============

    /**
     * 获取Set中所有元素
     * @param key       键
     */
    public Set<Object> sMembers(String key) {
        return redisTemplate.opsForSet()
                .members(key);
    }

    /**
     * 向Set中添加元素
     * @param key       键
     * @param values    值
     * @return          添加成功的个数
     */
    public Long sAdd(String key, Object... values) {
        return redisTemplate.opsForSet()
                .add(key, values);
    }

    /**
     * 向Set中添加元素
     * @param key       键
     * @param ttl       过期时间
     * @param values    值
     * @return          添加成功的个数
     */
    public Long sAddAndExpire(String key, long ttl, Object... values) {
        Long result = redisTemplate.opsForSet()
                .add(key, values);
        expire(key, ttl);
        return result;
    }

    /**
     * 是否存在于Set中
     * @param key       键
     * @param value     值
     * @return          是否存在
     */
    public Boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet()
                .isMember(key, value);
    }

    /**
     * set中的元素数量
     * @param key       键
     * @return          元素数量
     */
    public Long sSize(String key) {
        return redisTemplate.opsForSet()
                .size(key);
    }

    // =============List==========

    /**
     * 查询区间范围中的元素
     * @param key       键
     * @param start     起始index
     * @param end       结束index
     * @return          元素列表
     */
    public List<Object> lRange(String key, long start, long end) {
        return redisTemplate.opsForList()
                .range(key, start, end);
    }

    /**
     * 查询List的元素数量
     * @param key       键
     * @return          元素数量
     */
    public Long lSize(String key) {
        return redisTemplate.opsForList()
                .size(key);
    }

    /**
     * 查询相应index的元素
     * @param key       键
     * @param index     index
     * @return          元素
     */
    public Object lGet(String key, long index) {
        return redisTemplate.opsForList()
                .index(key, index);
    }

    /**
     * 右push添加元素
     * @param key       键
     * @param values    值
     * @return          添加成功的个数
     */
    public Long lAddRight(String key, Object values) {
        return redisTemplate.opsForList()
                .rightPush(key, values);
    }

    public Long lAddRightAndExpire(String key, long ttl, Object values) {
        Long count = lAddRight(key, values);
        expire(key, ttl);
        return count;
    }

    /**
     * 右push添加 多个元素
     * @param key       键
     * @param values    值
     * @return          添加成功的个数
     */
    public Long lAddRightAll(String key, Object... values) {
        return redisTemplate.opsForList()
                .rightPushAll(key, values);
    }

    public Long lAddRightAllAndExpire(String key, long ttl, Object... values) {
        Long count = lAddRightAll(key, values);
        expire(key, ttl);
        return count;
    }

    /**
     * 移除List中与value相等的元素
     * @param key       键
     * @param count     删除的数量
     * @param value     值
     * @return          删除的个数
     */
    public Long lDelete(String key, long count, Object value) {
        return redisTemplate.opsForList()
                .remove(key, count, value);
    }

    // =============Bit==========
    /**
     * 设置bit位
     * @param key       键
     * @param offset    偏移量
     * @param bit       值
     * @return          是否设置成功
     */
    public Boolean bitAdd(String key, int offset, boolean bit) {
        return redisTemplate.opsForValue()
                .setBit(key, offset, bit);
    }

    /**
     * 获取bit位
     * @param key       键
     * @param offset    偏移量
     * @return          值
     */
    public Boolean bitGet(String key, int offset) {
        return redisTemplate.opsForValue()
                .getBit(key, offset);
    }

}

package com.study.redis.apply.utils;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Slf4j
@Component // spring维护该bean对象
public class CacheClient {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 封装set方法一：将任意Java对象序列化为json并存储在string类型的key中，并且可以设置TTL过期时间
    public void set(String key, Object value, Long time, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, unit);
    }

    // 封装set方法二：- 将任意Java对象序列化为json并存储在string类型的key中，并且可以设置逻辑过期时间，用于处理缓存击穿问题
    public void setWithLogicalExpire(String key, Object value, Long time, TimeUnit unit) {
        // 设置逻辑过期时间
        RedisData redisData = new RedisData();
        redisData.setData(value);
        redisData.setExpireTime(LocalDateTime.now().plusSeconds(unit.toSeconds(time)));
        // 写入Redis
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(redisData));
    }

    /**
     * 封装get方法一：根据指定的key查询缓存，并反序列化为指定类型，利用缓存空值的方式解决缓存穿透问题
     * <p>
     * 1. 使用泛型, 此处定义了两个泛型：R(Java实例对象类型) 和 T(redis的key的id对应类型)；
     * 泛型的名字可可以随意取，如 “T, R, ID, DD” 都可以
     * 2. 需要在该方法中实现一段特定逻辑，通过传入函数实现: Function<T, R> dbFallBack
     * Function<T, R>：表示特定逻辑的函数，其中T是传入参数的泛型，R是返回类型的泛型
     */
    public <T, R> R queryWithPassThrough(String keyPrefix, T id, Class<R> type, Function<T, R> dbFallBack, Long time, TimeUnit unit) {
        // 在redis中存储时对应的key
        String key = keyPrefix + id;
        // 1.从redis查询商铺缓存
        String ObjectStr = stringRedisTemplate.opsForValue().get(key);
        // 2.判断是否存在
        if (StrUtil.isNotBlank(ObjectStr)) { // 只有shopStr有具体的字符串数据时，才会进入下面
            // 3.存在，直接返回
            return JSONUtil.toBean(ObjectStr, type); // type就是Class类型的，不用写为 “Object.class” 这样
        }
        if (ObjectStr != null) {
            // 返回错误信息
            return null;
        }
        // 4.不存在，根据id查询数据库
        R r = dbFallBack.apply(id);
        // 5.不存在，返回错误
        if (r == null) {
            // 为了解决缓存穿透问题，所以将数据库查询为空的数据也存储到redis中，存储为空值(设置过期时间不宜太长，2分钟比较合适)
            stringRedisTemplate.opsForValue().set(key, "", RedisConstants.CACHE_NULL_TTL, TimeUnit.MINUTES);
            // 返回错误信息
            return null;
        }
        // 6.存在，写入redis(为了保证数据的一致性，添加超时时间)
        this.set(key, r, time, unit);
        // 7.返回
        return r;
    }

    // 创建一个线程池，静态成员变量，所有实例对象共享
    private static final ExecutorService CACHE_REBUILD_EXECUTOR = Executors.newFixedThreadPool(10);

    // 使用逻辑过期，解决缓存击穿问题（方法2）
    public <T, R> R queryWithLogicalExpire(String prefixKey, T id, Class<R> type, String lockPrefixKey, Function<T, R> dbFallBack, Long time, TimeUnit unit) {
        String key = prefixKey + id;
        // 1.从redis查询商铺缓存
        String objectStr = stringRedisTemplate.opsForValue().get(key);
        // 2.判断是否存在
        if (StrUtil.isBlank(objectStr)) {
            // 3.未命中，直接返回
            return null;
        }
        // 4.命中，需要先将json字符串反序列化为对象
        RedisData redisData = JSONUtil.toBean(objectStr, RedisData.class);
        JSONObject data = (JSONObject) redisData.getData();
        R r = JSONUtil.toBean(data, type);
        LocalDateTime expireTime = redisData.getExpireTime();
        // 5.判断是否过期
        if (expireTime.isAfter(LocalDateTime.now()))
            // 5.1 未过期，直接返回商铺信息
            return r;
        // 5.2 已过期，需要缓存重建
        // 6. 缓存重建
        String lockKey = lockPrefixKey + id;
        // 6.1 获取互斥锁
        boolean isLock = tryLock(lockKey);
        // 6.2 判断是否获取互斥锁成功
        if (isLock) {
            // 6.3 成功，开启独立线程，实现缓存重建
            CACHE_REBUILD_EXECUTOR.submit(() -> {
                try {
                    // 查询数据库获取数据
                    R r1 = dbFallBack.apply(id);
                    // 重建缓存，为了方柏霓观察设置为20秒后过期，实际开发中一般设置为30分钟
                    this.setWithLogicalExpire(key, r1, time, unit);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    // 释放锁
                    releaseLock(lockKey);
                }
            });
        }
        // 6.4 失败，返回过期的商铺信息
        return r;
    }

    /**
     * 获取锁
     */
    public boolean tryLock(String key) {
        // setIfAbsent方法使用的就是 setnx 命令
        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", 10, TimeUnit.SECONDS);
        // 理论上 setIfAbsent 返回只有 true 和 false，但有可能出现问题会返回 null，出现空指针问题
        return BooleanUtil.isTrue(flag);
    }

    /**
     * 释放锁
     */
    public void releaseLock(String key) {
        stringRedisTemplate.delete("lock");
    }

}

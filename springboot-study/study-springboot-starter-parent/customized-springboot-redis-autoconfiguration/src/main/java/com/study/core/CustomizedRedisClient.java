package com.study.core;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 实现类
 */
public class CustomizedRedisClient implements CustomizedRedis{

    private JedisPool jedisPool;

    public CustomizedRedisClient(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public String set(String key, String value) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.set(key, value);
        } catch (Exception e) {
            return result;
        } finally {
            jedisPool.returnResource(jedis);
        }
        return result;
    }

    @Override
    public String get(String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.get(key);
        } catch (Exception e) {
            return result;
        } finally {
            jedisPool.returnResource(jedis);
        }
        return result;
    }
}

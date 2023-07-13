package com.study.redis.client;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisConnectionUtil {

    private static final JedisPool jedisPool;

    static {
        // 连接池的配置
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 最大连接数
        poolConfig.setMaxTotal(8);
        // 最大空闲连接
        poolConfig.setMaxIdle(8);
        // 最小空闲连接
        poolConfig.setMinIdle(0);
        // 设置最多等待时间1000毫秒
        poolConfig.setMaxWaitMillis(1000);
        // 创建连接池对象
        jedisPool = new JedisPool(poolConfig, "192.168.80.4", 6379, 1000, "123456");
    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

}

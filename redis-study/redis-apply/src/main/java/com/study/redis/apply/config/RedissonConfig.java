package com.study.redis.apply.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Bean("redissonClient")
    public RedissonClient redissonClient() {
        // 配置
        Config config = new Config();
        config.useSingleServer() // 单个redis服务端
                .setAddress("redis://192.168.80.4:6379") // redis服务端连接地址
                .setPassword("123456"); // 密码
        // 创建RedissonClient对象
        return Redisson.create(config);
    }

    @Bean("redissonClient2")
    public RedissonClient redissonClient2() {
        // 配置
        Config config = new Config();
        config.useSingleServer() // 单个redis服务端
                .setAddress("redis://192.168.80.4:6380"); // redis服务端连接地址
        // 创建RedissonClient对象
        return Redisson.create(config);
    }

    @Bean("redissonClient3")
    public RedissonClient redissonClient3() {
        // 配置
        Config config = new Config();
        config.useSingleServer() // 单个redis服务端
                .setAddress("redis://192.168.80.4:6381"); // redis服务端连接地址
        // 创建RedissonClient对象
        return Redisson.create(config);
    }

}

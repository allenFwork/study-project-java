package com.study.config;

import com.study.core.CustomizedRedis;
import com.study.core.CustomizedRedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@ConditionalOnClass(value = {Jedis.class, JedisPool.class, JedisPoolConfig.class})
@EnableConfigurationProperties(value = CustomizedRedisProperties.class)
@Configuration
public class RedisAutoConfiguration {

    @Autowired
    private CustomizedRedisProperties customizedRedisProperties;

    @Bean
//    @ConditionalOnProperty(prefix = "customized.spring.redis", name = "USEHA", havingValue =)
    @ConditionalOnProperty(prefix = "customized.spring.redis", name = "USEHA")
    public JedisPool jedisPool() {
        System.out.println("自定义启动类加载中||JedisPool对象创建中 ... ");
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(customizedRedisProperties.getMaxTotal());
        jedisPoolConfig.setMaxIdle(customizedRedisProperties.getMaxIdel());
        jedisPoolConfig.setMinIdle(customizedRedisProperties.getMinIdel());
        jedisPoolConfig.setTestOnBorrow(customizedRedisProperties.isTestOnBorrow());
        jedisPoolConfig.setTestOnReturn(customizedRedisProperties.isTestOnReturn());

        JedisPool jedisPool = new JedisPool(jedisPoolConfig, customizedRedisProperties.getHost());
        return jedisPool;
    }

    @Bean
    @ConditionalOnClass(value = JedisPool.class)
    public CustomizedRedis customizedRedis(JedisPool jedisPool) {
        System.out.println("加载单机版本的redis操作类||CustomizedRedis对象创建中 ... ");
        CustomizedRedis customizedRedis = new CustomizedRedisClient(jedisPool);
        return customizedRedis;
    }

}

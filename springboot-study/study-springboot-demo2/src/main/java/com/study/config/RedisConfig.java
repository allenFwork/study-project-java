package com.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
 */
@Configuration
public class RedisConfig {

    /**
     * 通过自动装配：(spring会默认帮忙管理bean对象)
     * 读取application.properties中的配置信息，将其封装到 RedisConnectionFactory对象中，
     * 然后创建 RedisTemplate对象，将其交由spring容器管理
     * spring容器自动配置了RedisTemplate兑现，如果自己有配置了，就会以自己配制的为主
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        // 设置序列化的方法，spring的RedisTemplate默认使用JdkSerializationRedisSerializer作为序列化方法
        redisTemplate.setDefaultSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

}


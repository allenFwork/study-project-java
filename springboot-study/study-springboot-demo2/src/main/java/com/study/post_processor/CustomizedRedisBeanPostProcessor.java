package com.study.post_processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * 实现 BeanPostProcessor接口, bean对象的后置处理器：
 * 用于处理RedisTemplate类型的bean对象的后置处理器
 */
@Component // 需要将此类交由spring识别处理
public class CustomizedRedisBeanPostProcessor implements BeanPostProcessor {

    /**
     * bean的生命周期
     * postProcessBeforeInitialization方法在bean对象执行完成构造方法后执行
     */
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof RedisTemplate) {
            RedisTemplate redisTemplate = (RedisTemplate) bean;
            // 修改原来RedisTemplate类型Bean对象的序列化方法
            redisTemplate.setDefaultSerializer(new Jackson2JsonRedisSerializer(Object.class));
            return redisTemplate;
        }
        if (bean instanceof CustomizedBeanDefinition2) {
            System.out.println("CustomizedRedisBeanPostProcessor||Bean对象的后置处理器||postProcessBeforeInitialization ...");
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof CustomizedBeanDefinition2) {
            System.out.println("CustomizedRedisBeanPostProcessor||Bean对象的后置处理器||postProcessAfterInitialization ...");
        }
        return bean;
    }

}

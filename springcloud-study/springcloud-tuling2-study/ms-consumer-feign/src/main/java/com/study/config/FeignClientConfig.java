package com.study.config;

import com.study.interceptor.CustomizedInterceptor;
import feign.Contract;
import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * Feign的配置类
 * 1. 不需要使用 @Configuration注解修饰该配置类,
 * 2. 如果添加了 @Configuration注解,那么必须将其他任何 @ComponentScan注解中排除掉该配置类
 */
public class FeignClientConfig {

    // 配置协议
    @Bean
    public Contract feignContract() {
        return new Contract.Default();
    }

    // 配置日志级别
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }
    
    @Bean
    public RequestInterceptor customizedInterceptor() {
        return new CustomizedInterceptor();
    }

}

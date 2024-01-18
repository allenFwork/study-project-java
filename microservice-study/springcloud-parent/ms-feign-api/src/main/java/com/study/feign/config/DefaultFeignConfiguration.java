package com.study.feign.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * 该配置类通过启动项目的 @EnableFeignClients(defaultConfiguration = DefaultFeignConfiguration.class) 加载该配置
 * 所以不需要添加 @Configuration 注解，没有通过包扫描的途径被spring框架管理起来
 */
public class DefaultFeignConfiguration {
    @Bean
    public Logger.Level logLevel() {
        return Logger.Level.BASIC;
    }
}

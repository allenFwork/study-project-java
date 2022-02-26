package com.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 此配置类不能再 @ComponentScan 下面，否则将会变成所有调用的均衡策略
 */
@Configuration
public class PowerIRuleConfig {

    @Bean
    public IRule createIRule() {
        return new RandomRule();
    }

}
package com.study.config;

import com.netflix.loadbalancer.IRule;
import com.study.load_balance.CustomizedLoadBalanceRule;
import org.springframework.context.annotation.Bean;

public class RibbonLoadBalanceConfig {

    @Bean
    public IRule createIRule() {
        return new CustomizedLoadBalanceRule();
    }

}

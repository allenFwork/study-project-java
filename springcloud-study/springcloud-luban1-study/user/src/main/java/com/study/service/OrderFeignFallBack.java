package com.study.service;

import org.springframework.stereotype.Component;

/**
 * feign使用hystrix
 */
@Component
public class OrderFeignFallBack implements OrderFeignClient {

    @Override
    public Object getOrder() {
        return null;
    }

}
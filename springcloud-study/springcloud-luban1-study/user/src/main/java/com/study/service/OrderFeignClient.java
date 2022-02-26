package com.study.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "ORDER-SERVER", fallback = OrderFeignFallBack.class)
public interface OrderFeignClient {

    @RequestMapping("/getOrder.do")
    public Object getOrder();

}

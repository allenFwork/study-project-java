package com.study.interfaces;

import com.study.entity.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "ms-provider-order", path = "/order")
public interface OrderApi {

    @RequestMapping("/queryOrderByOrderId/{orderId}")
    Order queryOrdersByOrderId(@PathVariable("orderId") String orderId);

    @RequestMapping("getRegistryInfo")
    String getRegistryInfo();

}

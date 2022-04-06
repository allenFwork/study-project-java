package com.study.controller;

import com.study.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/order")
@RestController
public class OrderController {

    @Autowired
    private ServiceInstance serviceInstance; // org.springframework.cloud.client.ServiceInstance

    @RequestMapping("/queryOrderByOrderId/{orderId}")
    public Order queryOrdersByUserId(@PathVariable("orderId") String orderId) {
        Order order = new Order();
        order.setOrderId("123456789");
        order.setQuantity(10);
        return order;
    }

    @RequestMapping("getRegistryInfo")
    public String getRegistryInfo() {
        return serviceInstance.getHost() + ": " + serviceInstance.getPort();
    }

}

package com.study.controller;

import com.study.entity.Order;
import com.study.interfaces.OrderApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/feign")
@RestController
public class TestOrderController {

    @Autowired
    private OrderApi orderApi;

    @RequestMapping(value = "/queryOrder/{orderId}", method = RequestMethod.GET)
    public Order queryOrder(@PathVariable("orderId") String orderId) {
        // 通过 Feign框架调用
        return orderApi.queryOrdersByOrderId(orderId);
    }


}

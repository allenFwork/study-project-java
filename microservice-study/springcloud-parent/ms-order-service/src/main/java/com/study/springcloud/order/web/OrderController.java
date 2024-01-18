package com.study.springcloud.order.web;

import com.study.springcloud.order.pojo.Order;
import com.study.springcloud.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Value("${pattern.dateformat}")
    private String dateformat;

    @GetMapping("now")
    public String now() {
        System.out.println("从nacos服务器上读取的配置信息：pattern.dateformat: " + dateformat);
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateformat));
    }

    @GetMapping("{orderId}")
    public Order queryOrderByUserId(@PathVariable("orderId") Long orderId) {
        // 根据id查询订单并返回
        return orderService.queryOrderById(orderId);
    }
}

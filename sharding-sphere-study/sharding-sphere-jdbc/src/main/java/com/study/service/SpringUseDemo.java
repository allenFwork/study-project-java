package com.study.service;

import com.study.entity.Order;
import com.study.entity.OrderItem;
import com.study.repository.OrderItemRepository;
import com.study.repository.OrderRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 通过 spring 框架使用 Sharding-Sphere 实现分库分表功能
 */
@Service
public class SpringUseDemo {

    @Resource
    private OrderItemRepository orderItemRepository;

    @Resource
    private OrderRepository orderRepository;

    public void use() {
        // 创建表
        orderRepository.createIfNotExitsTable();
        orderItemRepository.createIfNotExitsTable();
        // 清空表
        orderRepository.truncateTable();
        orderItemRepository.truncateTable();
        List<Long> orderIds = new ArrayList<>(10);
        System.out.println("1.Insert ------------------ ");

        for (int i = 0; i < 10; i++) {
            int rand = new Random().nextInt(20);
            Order order = new Order();
            order.setUserId(rand);
            order.setOrderId(rand + 1);
            order.setStatus("INSERT_TEST");
            orderRepository.insert(order);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(rand);
            orderItem.setUserId(rand + 1);
            orderItem.setStatus("INSERT_TEST");
            orderItemRepository.insert(orderItem);
        }
        System.out.println(orderItemRepository.selectAll());

    }

}

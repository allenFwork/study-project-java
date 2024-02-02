package com.study.distributed.transaction.order.service;

import com.study.distributed.transaction.order.entity.Order;

public interface OrderService {

    /**
     * 创建订单
     */
    Long create(Order order);
}
package com.study.repository;

import com.study.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderItemRepository {

    void createIfNotExitsTable();

    void truncateTable();

    int insert(OrderItem orderItem);

    void delete(Long orderId);

    void dropTable();

    List<OrderItem> selectAll();

}

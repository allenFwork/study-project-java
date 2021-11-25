package com.study.repository;

import com.study.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderRepository {

    void createIfNotExitsTable();

    void truncateTable();

    int insert(Order order);

    void delete(Long orderId);

    void dropTable();

}

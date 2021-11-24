package com.study.repository;

import com.study.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderRepository {

    int deleteByPrimaryKey(Long orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    void truncateTable();

    int insert(String orderId);
    
}

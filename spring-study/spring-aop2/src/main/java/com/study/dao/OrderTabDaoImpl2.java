package com.study.dao;

import org.springframework.stereotype.Repository;

@Repository("orderTabDaoImpl2")
public class OrderTabDaoImpl2 implements OrderTabDao {

    @Override
    public void update(String sql) {
        System.out.println("---------------- OrderTabDaoImpl2 update() ... -------------------");
        System.out.println(sql);
    }

}

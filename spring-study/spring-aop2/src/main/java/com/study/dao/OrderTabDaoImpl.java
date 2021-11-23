package com.study.dao;

import org.springframework.stereotype.Repository;

@Repository("orderTabDaoImpl")
public class OrderTabDaoImpl implements OrderTabDao {

    @Override
    public void update(String sql) {
        System.out.println("---------------- OrderTabDaoImpl update() ... -------------------");
        System.out.println(sql);
    }

}

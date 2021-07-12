package com.study.dao;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public List<Integer> query(){
        System.out.println("----------- UserDaoImpl query() ... -------------");
        List list = new ArrayList();
        list.add(1);
        list.add(2);
        return list;
    }

    @Override
    public void update() {
        System.out.println("----------- UserDaoImpl update() ... -------------");
    }

}

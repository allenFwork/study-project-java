package com.study.service;

import java.util.List;

//@Service
public class IndexServiceImpl2 implements IndexService {

    @Override
    public List query() {
        System.out.println("--------------- IndexServiceImpl query() ... -------------------");
        return null;
    }

    @Override
    public List query(String id) {
        System.out.println("----------- IndexServiceImpl query(String id) ... --------------");
        return null;
    }

    @Override
    public void update() {
        System.out.println("---------------- IndexServiceImpl update() ... -----------------");

    }
}

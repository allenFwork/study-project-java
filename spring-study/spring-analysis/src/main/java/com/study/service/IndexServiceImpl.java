package com.study.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {

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

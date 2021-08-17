package com.study.demo.rpc.server.service.impl;

import com.study.demo.rpc.server.service.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceImpl implements Service {

    static ArrayList<String> list = new ArrayList<>();

    static {
        list.add("张三");
        list.add("李四");
    }

    @Override
    public List<String> listAll() {
        return list;
    }

    @Override
    public String listById(Integer id) {
        return list.get(id);
    }
}

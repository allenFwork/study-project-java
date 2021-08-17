package com.study.demo.rpc.server.service;

import java.util.List;

public interface Service {
    List<String> listAll();

    String listById(Integer id);
}

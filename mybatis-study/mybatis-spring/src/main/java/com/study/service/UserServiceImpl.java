package com.study.service;

import com.study.mapper.UserMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserServiceImpl implements InitializingBean{

    @Autowired
    UserMapper userMapper;

    public void query() {
        System.out.println("------------- UserServiceImpl query() ... -------------------");
        userMapper.query();
        System.out.println("------------- UserServiceImpl query() ... -------------------");
    }

    @PostConstruct
    public void PostConstruct() {
        System.out.println("---------- UserServiceImpl PostConstruct() ... ---------------");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("-------- UserServiceImpl afterPropertiesSet() ... -----------");
    }
}

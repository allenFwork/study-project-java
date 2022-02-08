package com.study.controller;

import com.study.mapper.User;
import com.study.mapper.UserMapper;
import com.study.mapper.UserMapper2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MybatisTestController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserMapper2 userMapper2;

    @RequestMapping("/mybatis/findAll")
    public List<User> findAll() {
        return userMapper.list();
    }

    @RequestMapping("/mybatis/findAll2")
    public List<User> findAll2() {
        return userMapper2.queryAll();
    }

    @RequestMapping(value = "/mybatis/findOne/{userId}")
    public User findOne(@PathVariable("userId") Integer userId) {
        return userMapper.findOne(userId);
    }

    @RequestMapping(value = "/mybatis/findOne2/{userId}")
    public User findOne2(@PathVariable("userId") Integer userId) {
        return userMapper2.findByUserId(userId);
    }

    @RequestMapping(value = "/mybatis/save")
    public int save(User user) {
        return userMapper.save(user);
    }

    @RequestMapping(value = "/mybatis/save2")
    public int save2(User user) {
        return userMapper2.saveUser(user);
    }

}

package com.study.springcloud.user.service;

import com.study.springcloud.user.mapper.UserMapper;
import com.study.springcloud.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User queryById(Long id) {
        return userMapper.findById(id);
    }
}
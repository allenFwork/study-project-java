package com.study.controller;

import com.study.entity.User;
import com.study.interfaces.UserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/feign")
@RestController
public class TestUserController {

    @Autowired
    private UserApi userApi;

    @RequestMapping(value = "/queryUser/{userId}", method = RequestMethod.GET)
    public User queryOrder(@PathVariable("userId") String userId) {
        // 通过 Feign框架调用
        List<User> userList = userApi.queryUserByUserId(1);
        return userList.get(0);
    }

}

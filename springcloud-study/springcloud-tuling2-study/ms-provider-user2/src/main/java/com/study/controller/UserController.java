package com.study.controller;

import com.study.entity.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

// 不要写成 @RestController("/user") 没有用的路径配置
@RestController
@RequestMapping(("/user"))
public class UserController {

    @RequestMapping(value = "/queryUsersByUserId/{userId}", method = RequestMethod.GET)
    public List<User> queryUsersByUserId(@PathVariable("userId") Integer userId) {
        System.out.println("this is ms-provider-user-8003, parameter is " + userId);
        List<User> userList = new ArrayList<>();
        User user2 = new User();
        user2.setName("batman");
        user2.setUserId(2);
        userList.add(user2);
        return userList;
    }

}

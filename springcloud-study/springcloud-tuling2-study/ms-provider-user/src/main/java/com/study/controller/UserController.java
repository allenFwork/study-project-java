package com.study.controller;

import com.study.entity.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

// 不要写成 @RestController("/user") 没有用的路径配置
@RestController
@RequestMapping(("/user"))
public class UserController {

    @RequestMapping(value = "/queryUsersByUserId/{userId}", method = RequestMethod.GET)
    public List<User> queryUsersByUserId(@PathVariable("userId") Integer userId) {
        System.out.println("this is ms-provider-user-8002, parameter is " + userId);
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setName("superman");
        user.setUserId(1);
        userList.add(user);
        // 模拟花费3秒钟的时间处理
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userList;
    }

}

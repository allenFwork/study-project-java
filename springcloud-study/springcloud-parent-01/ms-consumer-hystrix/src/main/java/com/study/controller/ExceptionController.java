package com.study.controller;

import com.study.entity.User;
import com.study.exception.CustomizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/catchException/{userId}")
    public User catchExceptionTest(@PathVariable("userId") Integer userId) {
        ResponseEntity<List> responseEntity = null;
        try {
            responseEntity = restTemplate.getForEntity("http://localhost:8002/user/queryUsersByUserId/" + userId, List.class);
        } catch (Exception e) {
            // 抛出自定义的全局异常,会被spring容器捕获,然后交由CustomizedExceptionHandler的dealException处理返回
            throw new CustomizedException();
        }
        List<LinkedHashMap> list = responseEntity.getBody();
        // java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to com.study.entity.User
//        return (User) list.get(0);
        LinkedHashMap item = list.get(0);
        User user = new User();
        user.setName((String) item.get("name"));
        user.setUserId((Integer) item.get("id"));
        return user;
    }

}

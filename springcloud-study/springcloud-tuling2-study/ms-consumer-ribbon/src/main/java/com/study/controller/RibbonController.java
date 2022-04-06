package com.study.controller;

import com.study.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ribbon")
public class RibbonController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/queryUserInfo/{userId}")
    public Map queryUser(@PathVariable("userId") Integer userId) {
        // 直接使用url进行调用时,必须要将创建 RestTemplate 类型bean对象上的 @LoadBalanced 注解去掉
//        ResponseEntity<List> responseEntity = restTemplate.getForEntity("http://localhost:8002/user/queryUsersByUserId/" + userId, List.class);
        ResponseEntity<List> responseEntity = restTemplate.getForEntity("http://ms-provider-user/user/queryUsersByUserId/" + userId, List.class);
        List<Map> userList = responseEntity.getBody();
        return userList.get(0);
    }

}

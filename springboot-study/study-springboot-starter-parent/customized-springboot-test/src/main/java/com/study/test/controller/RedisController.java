package com.study.test.controller;

import com.study.core.CustomizedRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedisController {

    @Autowired
    private CustomizedRedis customizedRedis;

    @RequestMapping("/set/{key}/{value}")
    public String redisController(@PathVariable(value = "key") String key, @PathVariable("value") String value) {
        customizedRedis.set(key, value);
        return "success";
    }

}

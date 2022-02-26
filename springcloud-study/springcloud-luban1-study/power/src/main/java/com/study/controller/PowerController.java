package com.study.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PowerController {

    @RequestMapping("/getPower.do")
    public Object getPower(String name) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("key1", "power");

        // 为了查看 hystrix 的工作结果,故意抛出异常
//        if (name == null) {
//            throw new RuntimeException();
//        }
        // 休眠2秒，测试 hystrix 的超市监听功能
//        Thread.sleep(2000);

        return map;
    }

}

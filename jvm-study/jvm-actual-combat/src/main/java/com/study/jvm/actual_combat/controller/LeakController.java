package com.study.jvm.actual_combat.controller;

import com.study.jvm.actual_combat.entity.UserEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 内存泄漏接口测试
 * -Xmx1g -Xmm1g
 */
@RestController
@RequestMapping("/leak")
public class LeakController {
    private static Map<Long, UserEntity> userCache = new HashMap<>();
    private static List<byte[]> bigHeap = new ArrayList<>();

    /**
     * 大量数据 + 处理慢
     */
    @GetMapping("/test")
    public void test1() throws InterruptedException {
        byte[] bytes = new byte[1024 * 1024 * 100]; // 100m
        Thread.sleep(10 * 1000L);
    }

    /**
     * 登录接口 传递名字和id,放入hashmap中
     */
    @PostMapping("/login")
    public void login(String name, Long id) {
        userCache.put(id, new UserEntity(id, name));
    }

    @GetMapping("/bigHeap")
    public void bigHeap() {
        //3g
        for (int i = 0; i < 1024; i++) {
            bigHeap.add(new byte[1024 * 1024 * 3]);
        }
    }
}

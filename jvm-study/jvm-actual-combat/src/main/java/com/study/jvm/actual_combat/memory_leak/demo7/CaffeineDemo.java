package com.study.jvm.actual_combat.memory_leak.demo7;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.time.Duration;

/**
 * 内存泄露问题：如果大量的数据在静态变量中被长期引用，数据就不会被释放，如果这些数据不再使用，就成为了内存泄漏。
 */
public class CaffeineDemo {
    public static void main(String[] args) throws InterruptedException {
        Cache<Object, Object> build = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMillis(100)) // 设置过期时间100毫秒
                .build();
        int count = 0;
        while (true) {
            build.put(count++, new byte[1024 * 1024 * 10]);
            Thread.sleep(100L);
        }
    }
}

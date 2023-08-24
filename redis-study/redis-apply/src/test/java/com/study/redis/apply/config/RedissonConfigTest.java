package com.study.redis.apply.config;

import com.study.redis.apply.RedisApplyApplicationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

@Slf4j
public class RedissonConfigTest extends RedisApplyApplicationTest {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedissonClient redissonClient2;

    @Autowired
    private RedissonClient redissonClient3;

    @Test
    void redissonClientTest() throws InterruptedException {
        // 获取锁(可重入)，指定锁的名称
        RLock lock = redissonClient.getLock("anyKey");
        // 尝试获取锁，参数分别是：获取锁的最大等待时间(期间会重试)，锁自动释放时间，时间单位
        boolean isLock = lock.tryLock(1, 10, TimeUnit.SECONDS);
        // 判断获取锁成功
        if (isLock) {
            try {
                System.out.println("执行业务...");
            } finally {
                // 释放锁
                lock.unlock();
            }
        }
    }

    private RLock lock;


    @BeforeEach
    void initSetUp() {
        // lock = redissonClient.getLock("test:order");

        RLock lock11 = redissonClient.getLock("test:order");
        RLock lock12 = redissonClient.getLock("test:order");
        RLock lock13 = redissonClient.getLock("test:order");
        // 创建联锁 multiLock
        lock = redissonClient.getMultiLock(lock11, lock12, lock13);
    }

    // 可重入锁测试
    @Test
    void method1() throws InterruptedException {
        boolean isLock = lock.tryLock();
        // lock.tryLock(long, long, TimeUnit.SECONDS)
        // 第一个参数：如果获取锁失败，等待最长多长时间再次进行获取锁；第二个参数：时间单位
        isLock = lock.tryLock(20, TimeUnit.SECONDS);
        if (!isLock) {
            log.error("获取锁失败 ... 1 ");
            return;
        }
        try {
            log.info("获取锁成功 ... 1");
            method2();
            log.info("开始执行业务 ... 1");
        } finally {
            log.warn("准备释放锁 ... 1");
            lock.unlock();
        }
    }

    void method2() throws InterruptedException {
        boolean isLock = lock.tryLock();
        if (!isLock) {
            log.error("获取锁失败 ... 2 ");
            return;
        }
        try {
            log.info("获取锁成功 ... 2");
            log.info("开始执行业务 ... 2");
        } finally {
            log.warn("准备释放锁 ... 2");
            lock.unlock();
        }
    }
}

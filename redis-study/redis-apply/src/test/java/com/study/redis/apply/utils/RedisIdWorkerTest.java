package com.study.redis.apply.utils;

import com.study.redis.apply.RedisApplyApplicationTest;
import com.study.redis.apply.service.impl.ShopServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedisIdWorkerTest extends RedisApplyApplicationTest {

    @Autowired
    private RedisIdWorker redisIdWorker;

    // 用来测试高并发的线程池
    private ExecutorService executorService = Executors.newFixedThreadPool(500);

    @Test
    void idWorkerTest() {
        CountDownLatch countDownLatch = new CountDownLatch(300);
        Runnable task = () -> {
            for (int i = 0; i < 100; i++) {
                long id = redisIdWorker.nextId("order");
                System.out.println("id = " + id);
            }
            countDownLatch.countDown();
        };
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 300; i++) {
            executorService.submit(task);
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("任务结束，花费了 " + (end - begin) / 1000 + "秒");
    }

}

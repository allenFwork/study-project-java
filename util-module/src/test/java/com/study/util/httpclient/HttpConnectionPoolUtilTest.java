package com.study.util.httpclient;


import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class HttpConnectionPoolUtilTest {

    ExecutorService executor = Executors.newFixedThreadPool(100);
    final String TEST_URL = "http://10.110.147.31/bomdata/bomdataplatform/bomFactSbbController/getDistinctSbbByMtm?mtm=21HGS1JC1T"; //测试链接，这里使用搜狐的开放IP查询接口

    @Test
    public void test1() throws InterruptedException {
        // 任务重复次数
        final int testCount = 1400000;  // 1400000
        // 请求成功数量
        AtomicInteger successCount = new AtomicInteger(0);
        // 用于判断线程池中任务是否全部执行完毕
        CountDownLatch latch = new CountDownLatch(testCount);
        Long time1 = System.currentTimeMillis();
        for (int i = 0; i < testCount; i++) {
            executor.submit(() -> {
                String res = HttpConnectionPoolUtil.doGetByPool(TEST_URL);
                System.out.println(res);
                if (null != res && !res.isEmpty()) successCount.addAndGet(1);
                latch.countDown();
            });
        }
        // 等待线程池中的线程全部执行完
        latch.await();
        Long time2 = System.currentTimeMillis();
        System.out.println("耗时：" + (time2 - time1) + "毫秒,成功：" + successCount.get());
        executor.shutdown();

        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void test2() throws InterruptedException {
        // 任务重复次数
        final int testCount = 1400000;  // 1400000
        // 请求成功数量
        AtomicInteger successCount = new AtomicInteger(0);
        // 用于判断线程池中任务是否全部执行完毕
        CountDownLatch latch = new CountDownLatch(testCount);
        Long time1 = System.currentTimeMillis();
        HttpConnectionPoolUtil2 httpConnectionPoolUtil2 = new HttpConnectionPoolUtil2();
        for (int i = 0; i < testCount; i++) {
            executor.submit(() -> {
                String res = httpConnectionPoolUtil2.doGetByPool(TEST_URL);
                System.out.println(res);
                if (null != res && !res.isEmpty()) successCount.addAndGet(1);
                latch.countDown();
            });
        }
        // 等待线程池中的线程全部执行完
        latch.await();
        Long time2 = System.currentTimeMillis();
        System.out.println("耗时：" + (time2 - time1) + "毫秒,成功：" + successCount.get());
        executor.shutdown();

        try {
            httpConnectionPoolUtil2.clean();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


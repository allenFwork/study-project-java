package com.study.util.httpclient;


import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class HttpClientTest {

    ExecutorService executor = Executors.newFixedThreadPool(30);
    final String TEST_URL = "http://10.110.147.31/bomdata/bomdataplatform/bomFactSbbController/getDistinctSbbByMtm?mtm=21HGS1JC1T"; //测试链接，这里使用搜狐的开放IP查询接口

    @Test
    public void HttpGetTest() throws InterruptedException {
        final int testCount = 10000;                                 // 任务重复次数
        AtomicInteger successCount = new AtomicInteger(0); // 请求成功数量
        CountDownLatch latch = new CountDownLatch(testCount);        // 用于判断线程池中任务是否全部执行完毕
        Long time1 = System.currentTimeMillis();
        for (int i = 0; i < testCount; i++) {
            executor.submit(() -> {
                String res = HttpClientUtil.doGet(TEST_URL);
                System.out.println(res);
                if (null != res && !res.isEmpty()) successCount.addAndGet(1);
                latch.countDown();
            });
        }
        latch.await(); //等待线程池中的线程全部执行完
        Long time2 = System.currentTimeMillis();
        System.out.println("耗时：" + (time2 - time1) + "毫秒,成功：" + successCount.get());
        executor.shutdown();
    }

}


package com.study.java8.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建线程，并启动该线程，最终获取该线程执行完的结果
 */
@Slf4j(topic = "c.CreateThreadDemo3")
public class CreateThreadDemo3 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("running");
                Thread.sleep(2000); // 等待2秒
                return 100;
            }
        });
        Thread t = new Thread(task, "allen_thread");
        t.start();

        // 主线程阻塞在这里等待着结果
        log.debug("{}", task.get());
    }
}

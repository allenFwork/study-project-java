package com.study.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 测试使用10000个线程执行任务,查看其性能
 */
public class ThreadPkTest {

    public static void main(String[] args) throws InterruptedException {
        Long start = System.currentTimeMillis();
        final List<Integer> list = new ArrayList<>();
        final Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            Thread thread = new Thread(() -> list.add(random.nextInt()));
            thread.start();
            thread.join();
        }
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(list.size());
    }

}

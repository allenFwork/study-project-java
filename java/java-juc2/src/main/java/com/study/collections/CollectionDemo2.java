package com.study.collections;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 需求：并发环境下用到集合数据结构
 * 并发集合：ConcurrentLinkedDeque
 */
public class CollectionDemo2 {

    public static void main(String[] args) throws InterruptedException {

        // 创建并发集合 ConcurrentLinkedDeque
        ConcurrentLinkedDeque<String> list = new ConcurrentLinkedDeque();

        // 多线程向集合中添加数据
        Thread[] addThreads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            addThreads[i] = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    list.add(Thread.currentThread().getName() + ":Element " + j);
                }
            });
            addThreads[i].start();
            addThreads[i].join();
        }
        System.out.println("after addThreads size:" + list.size());

        // 多线程从集合中移除数据
        Thread[] pollThreads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            pollThreads[i] = new Thread(() -> {
                for (int j = 0; j < 5000; j++) {
                    list.pollLast();
                    list.pollFirst();
                }
            });
            pollThreads[i].start();
            pollThreads[i].join();
        }
        System.out.println("after pollThreads size:" + list.size());
    }

}

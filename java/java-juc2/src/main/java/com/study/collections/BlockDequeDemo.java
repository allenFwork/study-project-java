package com.study.collections;

import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * 并发集合：LinkedBlockingDeque
 *
 * try/catch 快捷键： ctrl + alt + t
 */
public class BlockDequeDemo {

    public static void main(String[] args) {
        // 阻塞式双端队列，创建一个给定容量的队列，队列满了就会阻塞
        LinkedBlockingDeque<String> list = new LinkedBlockingDeque(3);
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 5; j++) {
                    String str = new String(i + ":" + j);
                    try {
                        // 向阻塞队列中添加元素
                        list.put(str.toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("client:" + str + (new Date()));
                }
            }
        });
        thread.start();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                try {
                    // 从阻塞队列中移除元素
                    String str = list.take();
                    System.out.println("main:take " + str + " size:" + list.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("end");

    }
}

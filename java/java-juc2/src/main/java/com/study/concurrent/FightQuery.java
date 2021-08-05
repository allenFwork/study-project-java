package com.study.concurrent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 并发工具：CountDownLatch
 * 需求：多家航空公司的航班查询
 */
public class FightQuery {

    private static List<String> company = Arrays.asList("东方航空", "南方航空", "海南航空");
    private static List<String> fightList = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        String origin = "BJ";
        String dest = "SH";
        Thread[] threads = new Thread[company.size()];

        // step1: 设置 CountDownLatch 的值 （可以当作一个计数器）
        CountDownLatch countDownLatch = new CountDownLatch(company.size());

        for (int i = 0; i < threads.length; i++) {
            String name = company.get(i);
            threads[i] = new Thread(() -> {
                System.out.printf("%s 查询从%s到%s的机票\n", name, origin, dest);
                // 随机产生票数
                int value = new Random().nextInt(10);
                try {
                    TimeUnit.SECONDS.sleep(value);
                    fightList.add(name + "--" + value);
                    System.out.printf("%s公司查询成功！\n", name);
                    // step2: 每个线程准备完全后，对countDownLatch值进行减1处理 (计数器减一)
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            threads[i].start();
        }

        // step3: 等待 countDownLatch值变为0，才继续向下执行，否则阻塞在这里
        countDownLatch.await();

        System.out.println("==============查询结果如下：================");
        fightList.forEach(System.out::println);
    }
}

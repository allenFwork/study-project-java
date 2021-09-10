package com.study.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 使用线程池跑10000个任务,查看其性能
 */
public class ThreadPoolPkTest {

    public static void main(String[] args) throws InterruptedException {
        Long start = System.currentTimeMillis();
        final List<Integer> list = new ArrayList<>();
        /**
         * Executors.newSingleThreadExecutor()底层代码：
         * new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>())
         */
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        final Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            final int j = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    list.add(j);
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            });
        }
        /**
         * shutdown和awaitTermination为接口ExecutorService定义的两个方法，一般情况配合使用来关闭线程池。
         */
        // shutdown方法：平滑的关闭ExecutorService，当此方法被调用时，
        // ExecutorService停止接收新的任务并且等待已经提交的任务（包含提交正在执行和提交未执行）执行完成。
        // 当所有提交任务执行完毕，线程池即被关闭。
        executorService.shutdown();
        // awaitTermination方法：接收人timeout和TimeUnit两个参数，用于设定超时时间及单位。当等待超过设定时间时，
        // 会监测ExecutorService是否已经关闭，若关闭则返回true，否则返回false。一般情况下会和shutdown方法组合使用。
        boolean flag = executorService.awaitTermination(1, TimeUnit.DAYS);
        System.out.println("是否执行完了线程池中所有的任务: " + flag);
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(list.size());
    }

}

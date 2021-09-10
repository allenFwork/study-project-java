package com.study.threadpool.customized;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {

    public static void main(String[] args) {
        /**
         * LinkedBlockingDeque：无界队列，代表队列的容量是无限的，可以一直加
         * ArrayBlockingQueue：有界队列
         * SynchronousQueue：阻塞队列，不会有等待
         */
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,3, 10, new LinkedBlockingDeque<>());
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("测试自定义的线程池1 ... ");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("测试自定义的线程池2 ... ");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("测试自定义的线程池3 ... ");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("测试自定义的线程池4 ... ");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("测试自定义的线程池5 ... ");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("测试自定义的线程池6 ... ");
            }
        });

    }

}

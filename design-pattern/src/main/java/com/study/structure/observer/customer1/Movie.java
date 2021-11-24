package com.study.structure.observer.customer1;

import java.util.concurrent.TimeUnit;

/**
 * 被观察者 - 电影
 */
public class Movie implements Runnable{
    // 表示电影中播放到了某种关键高潮场景的状态
    private volatile boolean flag = false;

    public void play(){
        // 模拟电影播放了5秒后到了高潮场景
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
    }

    public boolean getFlag() {
        return flag;
    }

    @Override
    public void run() {
        System.out.println("--------------被观察者（电影）开始播放--------------");
        play();
    }
}

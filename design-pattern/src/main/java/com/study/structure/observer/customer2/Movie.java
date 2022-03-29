package com.study.structure.observer.customer2;

import java.util.concurrent.TimeUnit;

/**
 * 被观察者 - 电影
 */
public class Movie implements Runnable {

    private Person person;

    public void setPerson(Person person) {
        this.person = person;
    }

    public void play() {
        // 模拟电影播放了5秒后到了高潮场景
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        person.work();
    }

    @Override
    public void run() {
        System.out.println("--------------被观察者（电影）开始播放--------------");
        play();
    }

}

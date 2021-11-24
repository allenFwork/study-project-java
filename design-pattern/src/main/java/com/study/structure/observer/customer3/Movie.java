package com.study.structure.observer.customer3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 被观察者 - 电影
 */
public class Movie implements Runnable {

    private List<Person> people = new ArrayList<>();

    public void addPerson(Person person) {
        people.add(person);
    }

    public void play() {
        // 模拟电影播放了5秒后到了高潮场景
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MovieEvent movieEvent = new MovieEvent();
        movieEvent.setCircumstance("高潮场景");
        for (Person person : people) {
            person.perform(movieEvent);
        }

        // 模拟电影播放了5秒后到了悲伤场景
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        movieEvent.setCircumstance("悲伤场景");
        for (Person person : people) {
            person.perform(movieEvent);
        }
    }

    @Override
    public void run() {
        System.out.println("--------------被观察者（电影）开始播放--------------");
        play();
    }
}

package com.study.structure.observer;

import com.study.structure.observer.jdk.Man;
import com.study.structure.observer.jdk.Woman;

public class ObserverTest {

    public static void main(String[] args) {

        // 1. 观察者设计模式测试-版本1
//        Movie movie = new Movie();
//        Person person = new Person(movie);
//
//        Thread movieThread = new Thread(movie);
//        Thread personThread = new Thread(person);
//
//        movieThread.start();
//        personThread.start();

        // 2. 观察者设计模式测试-版本2
//        com.study.structure.observer.customer2.Movie movie = new com.study.structure.observer.customer2.Movie();
//        com.study.structure.observer.customer2.Person person = new com.study.structure.observer.customer2.Person();
//
//        movie.setPerson(person);
//
//        Thread movieThread = new Thread(movie);
//        movieThread.start();

        // 3. 观察者设计模式测试-版本3
//        com.study.structure.observer.customer3.Movie movie = new com.study.structure.observer.customer3.Movie();
//        com.study.structure.observer.customer3.Man man = new com.study.structure.observer.customer3.Man();
//        com.study.structure.observer.customer3.Woman woman = new com.study.structure.observer.customer3.Woman();
//
//        movie.addPerson(man);
//        movie.addPerson(woman);
//
//        Thread movieThread = new Thread(movie);
//        movieThread.start();

        // 4. 使用jdk的api实现
        com.study.structure.observer.jdk.Movie movie = new com.study.structure.observer.jdk.Movie();
        Man man = new Man();
        Woman woman = new Woman();

        movie.addObserver(man);
        movie.addObserver(woman);

        movie.play();
    }

}

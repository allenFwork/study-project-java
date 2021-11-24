package com.study.structure.observer;

import com.study.observer.jdk.Man;
import com.study.observer.jdk.Movie;
import com.study.observer.jdk.Woman;

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
//        Movie movie = new Movie();
//        Person person = new Person();
//
//        movie.setPerson(person);
//
//        Thread movieThread = new Thread(movie);
//        movieThread.start();

        // 3. 观察者设计模式测试-版本3
//        Movie movie = new Movie();
//        Man man = new Man();
//        Woman woman = new Woman();
//
//        movie.addPerson(man);
//        movie.addPerson(woman);
//
//        Thread movieThread = new Thread(movie);
//        movieThread.start();

        Movie movie = new Movie();
        Man man = new Man();
        Woman woman = new Woman();

        movie.addObserver(man);
        movie.addObserver(woman);

        movie.paly();
    }

}

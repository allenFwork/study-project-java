package com.study.structure.observer.customer1;

/**
 * 观察者 - 人
 */
public class Person implements Runnable {

    private Movie movie;

    public Person(Movie movie) {
        this.movie = movie;
    }

    public void work() {
        System.out.println("---------------观察者观看电影高潮激动极了--------------");
    }

    @Override
    public void run() {
        // 一直观看着电影
        while (!movie.getFlag()) {
            System.out.println("------观察者（人） 一直监听着 被观察者（电影）---------");
        }
        // 当电影到达了高潮时，通过了上述循环，开始向下执行（处理事件），高潮处人应该做的处理
        work();
    }
}

package com.study.structure.observer.jdk;


import java.util.Observable;

/**
 * 被观察者 继承 java.util.Observable
 */
public class Movie extends Observable {

    /**
     * 播放电影
     */
    public void play() {
        // setChanged() 表示电影进行到了某个场景，状态改变了
        setChanged();
        // 通知 观察者
        notifyObservers();
    }

}

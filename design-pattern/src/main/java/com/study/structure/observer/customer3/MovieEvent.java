package com.study.structure.observer.customer3;

/**
 * 抽象出来的事件
 * 用来描述事件的情况
 */
public class MovieEvent {

    // 电影的时间
    private String time;
    // 电影的场景
    private String circumstance;
    // ...

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCircumstance() {
        return circumstance;
    }

    public void setCircumstance(String circumstance) {
        this.circumstance = circumstance;
    }
}

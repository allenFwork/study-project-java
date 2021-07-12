package com.study.structure.decorate.entity;

/**
 * 果汁 - 具体饮料
 */
public class Juice extends Bevarage {

    public Juice(String description){
        super.description = description;
    }

    @Override
    public Double price() {
        System.out.println(description + "的价格是： 10.0元");
        return 10.0;
    }

}

package com.study.structure.decorate.entity;

/**
 * 咖啡 - 具体饮料
 */
public class Coffe extends Bevarage {

    public Coffe(String description){
        super.description = description;
    }

    @Override
    public Double price() {
        System.out.println(description + "的价格是： 15.0元");
        return 15.0;
    }
}

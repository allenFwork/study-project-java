package com.study.structure.decorate.entity;

/**
 * 奶茶 - 具体饮料
 */
public class MilkTea extends Bevarage{

    public MilkTea(String description){
        super.description = description;
    }

    @Override
    public Double price() {
        System.out.println(description + "的价格是： 20.0元");
        return 20.0;
    }
}

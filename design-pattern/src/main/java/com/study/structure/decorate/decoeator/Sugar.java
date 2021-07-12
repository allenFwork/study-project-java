package com.study.structure.decorate.decoeator;

import com.study.structure.decorate.entity.Bevarage;

/**
 * 装饰者 - 糖
 * 1. 继承装饰者抽象类，表明这是一个装饰者
 * 2. 可以添加到不同的饮料中，形成不容的口味
 * 3. 糖就是装饰者，饮料就是被装饰的对象
 */
public class Sugar extends CondimentDecorator{

    // 被装饰的对象
    Bevarage bevarage;

    public Sugar(Bevarage bevarage) {
        this.bevarage = bevarage;
    }

    @Override
    public void setDescription(String description) {
        super.setDescription(description + "加糖");
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public Double price() {
        System.out.println("糖价格为1元");
        return bevarage.price() + 1.0;
    }
}

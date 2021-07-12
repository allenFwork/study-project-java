package com.study.entity;

/**
 * 获取spring容器中的对象，并进行操作（方法一）
 */
public abstract class SingleBean1 {

    public void sayHello(){
        getHi().sayHi();
    }

    public abstract Hi getHi();

}

package com.study.structure.proxy.staticProxy;

/**
 * @description 目标对象
 * @author sw
 */
public class TargetObject implements Target {

    public void sayHello() {
        System.out.println("this is targetObject! Hello World!");
    }

    public String test(String input) {
        return "this is targetObject! hello world";
    }

}

package com.study.structure.proxy.dynamicProxy;

public class UserServiceImpl implements UserService {
    @Override
    public void good() {
        System.out.println("-------------------- void good() ---------------------");
    }

    @Override
    public void good(String string) {
        System.out.println("-------------- void good(String string) ---------------");
    }

    @Override
    public String goodMorning() {
        System.out.println("----------------- void goodMorning() -------------------");
        return "good morning";
    }

    @Override
    public String goodMorning(String name) {
        System.out.println("----------- String goodMorning(String name) --------------");
        return "good morning " + name ;
    }

    @Override
    public String goodMorning(String name, int age) {
        System.out.println("------- String goodMorning(String name, int age) ----------");
        return "good morning " + name + ", you are " + age  ;
    }

    @Override
    public int count(int a, int b) {
        System.out.println("------- int count(int a, int b) ----------");
        return (a + b)  ;
    }
}

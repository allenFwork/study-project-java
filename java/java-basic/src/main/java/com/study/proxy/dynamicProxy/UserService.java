package com.study.proxy.dynamicProxy;

public interface UserService {

    void good();

    void good(String string);

    String goodMorning();

    String goodMorning(String name);

    String goodMorning(String name, int age);

    int count(int a, int b);

}

package com.study;

public class ThreadLocalTest {

    public static void main(String[] args) {
        // ThreadLocal 每个线程都有独立的自己的数据
        ThreadLocal<String> threadLocal = new ThreadLocal<String>() {
            @Override
            protected String initialValue() {
                return "default";
            }
        };
        threadLocal.set("userInfo");
        threadLocal.get();
    }

}

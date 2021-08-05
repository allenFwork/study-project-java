package com.study.singleton;

/**
 * 单例模式：懒汉式
 * 在调用的过程中实例化对象
 */
public class LazySingleton {

    private static LazySingleton instance = null;

    private LazySingleton() {

    }

    public static LazySingleton getInstance() {
        if (null == instance)
            instance = new LazySingleton();
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                System.out.println(LazySingleton.getInstance());
            }).start();
        }
    }
}
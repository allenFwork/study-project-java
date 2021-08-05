package com.study.singleton;

/**
 * 单例模式：饿汉式
 * 加载类时就产生实例对象
 */
public class HungrySingleton {

    // 加载到jvm时，就产生了实例对象
    private static HungrySingleton instance = new HungrySingleton();

    private HungrySingleton() {

    }

    // 返回实例对象
    public static HungrySingleton getInstance() {
        return instance;
    }

    public static void main(String[] args) {

        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                System.out.println(HungrySingleton.getInstance());
            }).start();
        }

    }

}

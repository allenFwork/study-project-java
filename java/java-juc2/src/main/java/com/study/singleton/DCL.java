package com.study.singleton;

/**
 * Double-Check-Locking
 * DCL模式：双重枷锁
 */
public class DCL {

    private static DCL instance = null;

    private DCL() {
    }

    public static DCL getInstance() {
        if (null == instance)
            synchronized (DCL.class) {
                if (null == instance)
                    instance = new DCL();
            }
        return instance;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                System.out.println(DCL.getInstance());
            }).start();
        }
    }
}
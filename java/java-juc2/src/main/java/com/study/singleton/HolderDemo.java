package com.study.singleton;

/**
 * Holder模式：目前应用比较广泛的单例模式
 */

public class HolderDemo {

    private HolderDemo() {
    }

    /**
     * 定义内部类
     */
    private static class Holder {
        private static HolderDemo instance = new HolderDemo();
    }
    //懒加载
    //synchronized
    //<init>

    public static HolderDemo getInstance() {
        return Holder.instance;
    }
}
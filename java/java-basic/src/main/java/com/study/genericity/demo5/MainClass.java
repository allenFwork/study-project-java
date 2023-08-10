package com.study.genericity.demo5;

public class MainClass {

    public static void main(String[] args) {

        /**
         * 下面1中使用的类泛型被限定了,2中其实也已经被限定了
         * 1.class ChildGeneric1 implements ParentGeneric<String>,所以限定了getKey()也是String类型的，此处这么些正确
         * 2.class ChildGeneric1 implements ParentGeneric, 所以限定了getKey()默认是Object类型,下面的写法会报错, 得写成：
         *      Object key = childGeneric1.getKey();
         */
        ChildGeneric1 childGeneric1 = new ChildGeneric1();
        String key = childGeneric1.getKey();
        System.out.println(key);

        // 泛型的类型没有被限定，完全由用户来定
        ChildGeneric2<String, Integer> childGeneric2 = new ChildGeneric2("key", 100);
        String key2 = childGeneric2.getKey();
        Integer value2 = childGeneric2.getValue();
        System.out.println("key2 = " + key2 + ", value2 = " + value2);

    }

}

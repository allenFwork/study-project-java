package com.study.genericity.demo2;

/**
 * 泛型类的使用
 */
public class GenericMainClass {

    public static void main(String[] args) {

        // 泛型类在创建对象的时候，指定操作的具体数据类型
        Generic<String> strGeneric = new Generic<>("泛型类的使用测试");
        String key1 = strGeneric.getKey();
        System.out.println(key1);

        Generic<Integer> intGeneric = new Generic<>(100);
        int key2 = intGeneric.getKey();
        System.out.println(key2);

        // 1.泛型类在创建对象的时候，如果没有指定类型，将按照Object类型来操作
        Generic generic = new Generic(100);
        System.out.println(generic.getKey());
        generic = new Generic("ABC");
        System.out.println(generic.getKey());

        /**
         * 2.泛型类，不支持基本数据类型
         * 因为泛型类在使用的时候，即使传了类型参数，运行时大部分时候还是以Object类型解析的，只有在具体使用的时候，才会将其转化为传入的数据类型；
         * 但是如果传入的是基本类型，它们不是Object类型的子类，无法作为Object类型解析，所以出错
         */
        // Generic<int> generic1 = new Generic<int>(100); 编译报错

        // 3.同一泛型类，根据不同的数据类型创建的对象，本质上是同一类型（类类型）
        System.out.println(strGeneric.getClass()); // class com.study.genericity.demo2.Generic
        System.out.println(intGeneric.getClass()); // class com.study.genericity.demo2.Generic
        // 查看两个类的内存地址是否是同一个：true
        System.out.println("查看两个类的内存地址是否是同一个：" + (strGeneric.getClass() == intGeneric.getClass()));
    }

}

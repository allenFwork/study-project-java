package com.study.genericity.demo1;

import java.util.ArrayList;

/**
 * 泛型学习
 */
public class Demo1 {

    public static void main(String[] args) {
        // 正常情况下，不使用泛型时，编写代码
        ArrayList list = new ArrayList();
        list.add("java");
        list.add(100);
        list.add(true);

        for (int i = 0; i < list.size(); i++) {
            Object o = list.get(i);
            String str = (String) o;
            System.out.println(str);
        }
        /**
         * 上述代码执行过程中，会出现运行时异常，如下：
         * Exception in thread "main" java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.String
         * 	at com.study.genericity.demo1.Demo1.main(Demo1.java:19)
         */

        // 使用泛型
        // 上述异常编译正确，无法检测出来，所以想到使用泛型来限制，从而在编译时就将类似的错误检测出来，如下 ArrayList<String>
        ArrayList<String> strList = new ArrayList<>();
        strList.add("java");
        // strList.add(100); 编译报错
        // strList.add(true); 编译报错

        ArrayList<Integer> intList = new ArrayList<>();
        intList.add(100); // 自动装箱，将 int类型的 100转化为了Integer类型
        intList.add(200);

    }

}

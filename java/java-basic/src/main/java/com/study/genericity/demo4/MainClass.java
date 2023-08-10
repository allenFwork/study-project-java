package com.study.genericity.demo4;

public class MainClass {

    public static void main(String[] args) {
        // 创建 ChildGeneric1 类型对象时，会
        ChildGeneric1<String> childGeneric1 = new ChildGeneric1<>();
        childGeneric1.setValue("abc");
        System.out.println(childGeneric1.getValue());
    }

}

package com.study.jvm.demo1.clinit;

public class Demo9 {
    public static void main(String[] args) {
//        new Child();
        // 即使此刻是通过Child访问静态变量a的，但是该变量是Parent，所以只会执行Parent的clinit方法
        System.out.println(Child.a); // 打印结果：1
    }

}

class Parent {
    static int a = 0;

    static {
        a = 1;
        System.out.println("执行 Parent 的 clinit方法 ... ");
    }
}

class Child extends Parent {
    static {
        a = 2;
        System.out.println("执行 Child 的 clinit方法 ... ");
    }
}

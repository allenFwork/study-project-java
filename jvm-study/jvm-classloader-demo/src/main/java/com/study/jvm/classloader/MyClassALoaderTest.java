package com.study.jvm.classloader;

public class MyClassALoaderTest {
    public static void main(String[] args) throws ClassNotFoundException {
        // 通过 -Xbootclasspath/a 测试 启动类加载器加载 MyClassA 类
        Class<?> clazz = Class.forName("com.study.jvm.demo.MyClassA");
        System.out.println(clazz);       // class com.study.jvm.demo.MyClassA
        ClassLoader classLoader = clazz.getClassLoader();
        System.out.println(classLoader); // null
    }
}

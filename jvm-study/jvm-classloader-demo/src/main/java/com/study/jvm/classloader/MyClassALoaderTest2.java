package com.study.jvm.classloader;

import jdk.nashorn.internal.runtime.ScriptEnvironment;

public class MyClassALoaderTest2 {
    public static void main(String[] args) throws ClassNotFoundException {
        // 通过 -Djava.ext.dirs 测试 扩展类加载器加载 MyClassA 类
        Class<?> clazz = Class.forName("com.study.jvm.demo.MyClassA");
        System.out.println(clazz);       // class com.study.jvm.demo.MyClassA
        ClassLoader classLoader = clazz.getClassLoader();
        System.out.println(classLoader); // sun.misc.Launcher$ExtClassLoader@2503dbd3

        classLoader = ScriptEnvironment.class.getClassLoader();
        System.out.println(classLoader); // sun.misc.Launcher$ExtClassLoader@2503dbd3
    }
}

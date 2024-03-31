package com.study.jvm.classloader;

/**
 * 主动加载类文件到虚拟机中
 */
public class LoadClassDemo1 {
    public static void main(String[] args) throws ClassNotFoundException {
        // 方法一：通过Class.forName方法
        Class<?> clazz = Class.forName("com.study.jvm.demo.MyClassB");
        System.out.println(clazz);

        // 方法二：通过类加载器的loadClass方法
        ClassLoader classLoader = LoadClassDemo1.class.getClassLoader(); // 获取加载此类的应用程序类加载器对象
        clazz = classLoader.loadClass("com.study.jvm.demo.MyClassB");
        System.out.println(clazz);
    }
}

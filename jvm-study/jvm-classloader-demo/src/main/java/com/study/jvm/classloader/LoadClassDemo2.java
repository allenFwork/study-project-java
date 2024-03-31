package com.study.jvm.classloader;

/**
 * 主动加载类文件到虚拟机中
 */
public class LoadClassDemo2 {
    public static void main(String[] args) throws ClassNotFoundException {
        // 获取main方法所在类的类加载器，应用程序类加载器
        ClassLoader classLoader = LoadClassDemo2.class.getClassLoader();
        System.out.println(classLoader);

        // 使用应用程序类加载器加载
        Class<?> clazz = classLoader.loadClass("java.lang.String");
        // 无法获取到加载“java.lang.String”类的了加载器，因为它是通过启动类加载器加载的
        System.out.println(clazz.getClassLoader()); // null
    }
}

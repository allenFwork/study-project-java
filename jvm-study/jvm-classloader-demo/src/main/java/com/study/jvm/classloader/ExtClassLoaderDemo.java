package com.study.jvm.classloader;

import jdk.nashorn.internal.runtime.ScriptEnvironment;

/**
 * 扩展类加载器测试
 */
public class ExtClassLoaderDemo {
    public static void main(String[] args) {
        /*
         * jdk.nashorn.internal.runtime.ScriptEnvironment jdk自带的，
         * 通用但不是很重要的类，放在ext目录下，查看它的类加载器是否是扩展类加载器
         */
        ClassLoader classLoader = ScriptEnvironment.class.getClassLoader();
        System.out.println(classLoader); // sun.misc.Launcher$ExtClassLoader@45ee12a7
        // 因为 ExtClassLoader类 是 sun.misc.Launcher类的静态内部类，所以使用“$”分隔开
    }
}

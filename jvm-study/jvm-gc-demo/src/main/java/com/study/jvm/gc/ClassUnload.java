package com.study.jvm.gc;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

/**
 * 类的卸载
 * 测试类会在什么情况下被回收
 * 启动时添加VM参数：-XX:+TraceClassLoading -XX:+TraceClassUnloading，查看加载的类和卸载的类
 */
public class ClassUnload {
    public static void main(String[] args) throws InterruptedException {
        try {
            ArrayList<Class<?>> classes = new ArrayList<>();
            ArrayList<URLClassLoader> loaders = new ArrayList<>();
            ArrayList<Object> objs = new ArrayList<>();
            while (true) {
                // 当循环进入到下一次后，以下三个对象就没人再使用了，JVM会将其回收掉
                URLClassLoader loader = new URLClassLoader(new URL[]{new URL("file:D:\\programme\\idea\\idea_workspace\\study-project-java-2023\\jvm-study\\jvm-classloader-demo\\lib\\")});
                Class<?> clazz = loader.loadClass("com.study.jvm.demo.MyClassA");
                Object o = clazz.newInstance();

                // 第一个条件：将对象添加到集合中，不会再被回收
//                objs.add(o);
                // 第二个条件：将类加载器对象添加到集合中，类加载器不会再被回收
//                loaders.add(loader);
                // 第三个条件：该类对应的 java.lang.Class对象被引用，并放到集合中，不会被回收
//                classes.add(clazz);

                // 手动触发垃圾回收
                System.gc();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

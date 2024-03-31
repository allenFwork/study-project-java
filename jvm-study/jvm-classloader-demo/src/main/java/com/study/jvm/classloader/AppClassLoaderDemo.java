package com.study.jvm.classloader;

import com.study.jvm.demo.MyClassB;
import org.apache.commons.io.FileUtils;

import java.io.IOException;

/**
 * 应用程序类加载器测试
 */
public class AppClassLoaderDemo {
    public static void main(String[] args) throws IOException {
        // 当前项目中创建当前项目中的MyClassB类对象
        MyClassB myClassB = new MyClassB();
        ClassLoader classLoader = myClassB.getClass().getClassLoader();
        System.out.println(classLoader); // sun.misc.Launcher$AppClassLoader@18b4aac2

        // 加载Maven依赖中包含的类
        classLoader = FileUtils.class.getClassLoader();
        System.out.println(classLoader); // sun.misc.Launcher$AppClassLoader@18b4aac2

        System.in.read();
    }
}

package com.study.jvm.classloader;

import java.io.IOException;

public class BootstrapClassLoaderDemo {
    public static void main(String[] args) throws IOException {
        // java.lang.String 的类加载器应该是启动类加载器，但是下面打印结果却是null
        ClassLoader classLoader = String.class.getClassLoader();
        // 因为此程序是偏向于上层的应用，而启动类加载器是JVM底层的应用，上层无法获取下层的信息。可以通过Arthas来确认
        System.out.println(classLoader);

        System.in.read();
    }
}

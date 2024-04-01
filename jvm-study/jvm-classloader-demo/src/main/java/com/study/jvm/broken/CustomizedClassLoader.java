package com.study.jvm.broken;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;

/**
 * 打破双亲委派机制(方法一) - 自定义类加载器
 * 此处直接从写了loadClass方法，在其中直接调用defineClass来直接加载类，
 * 实际使用中应当重写findClass方法，通过它来寻找要加载的类文件数据
 */
public class CustomizedClassLoader extends ClassLoader {

    private String basePath;
    private final static String FILE_EXT = ".class";

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    private byte[] loadClassData(String name) {
        try {
            String tempName = name.replaceAll("\\.", Matcher.quoteReplacement(File.separator));
            FileInputStream fis = new FileInputStream(basePath + tempName + FILE_EXT);
            try {
                return IOUtils.toByteArray(fis);
            } finally {
                IOUtils.closeQuietly(fis);
            }

        } catch (Exception e) {
            System.out.println("自定义类加载器加载失败，错误原因：" + e.getMessage());
            return null;
        }
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        // 如果这个类是jdk环境的（jdk核心类库的），就使用默认的类加载器，即启动类加载器
        if (name.startsWith("java.")) {
            return super.loadClass(name);
        }
        // 通过类的名字，找到该类，然后写入到data数组中
        byte[] data = loadClassData(name);
        // 直接使用defineClass方法加载自己写的类，将其加载到内存中去（没有经过双亲委派）
        return defineClass(name, data, 0, data.length);
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        // 创建自定义的类加载器对象
        CustomizedClassLoader classLoader1 = new CustomizedClassLoader();
        classLoader1.setBasePath("D:\\code\\idea_code\\study-project-java-2023\\jvm-study\\jvm-classloader-demo\\lib\\");
        Class<?> clazz1 = classLoader1.loadClass("com.study.jvm.demo.MyClassA");
        System.out.println("MyClassA 的类加载器是：" + clazz1.getClassLoader());

        CustomizedClassLoader classLoader2 = new CustomizedClassLoader();
        classLoader2.setBasePath("D:\\code\\idea_code\\study-project-java-2023\\jvm-study\\jvm-classloader-demo\\lib\\");
        Class<?> clazz2 = classLoader2.loadClass("com.study.jvm.demo.MyClassA");
        System.out.println("MyClassA 的类加载器是：" + clazz2.getClassLoader());

        // 判断加载同一个类（com.study.jvm.demo.MyClassA）的两个类加载器是否是同一个对象
        System.out.println(clazz1 == clazz2); // false

        // 当前线程的类加载器是：sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println("当前线程的类加载器是：" + Thread.currentThread().getContextClassLoader());
        Thread.currentThread().setContextClassLoader(classLoader1); // 修改当前线程的上下文类加载器
        // 当前线程的类加载器是：com.study.jvm.broken.CustomizedClassLoader@9807454
        System.out.println("当前线程的类加载器是：" + Thread.currentThread().getContextClassLoader());

        // 自定义的类加载器默认的父类加载器是：sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println("自定义的类加载器默认的父类加载器是：" + classLoader1.getParent());

        System.in.read();
    }
}

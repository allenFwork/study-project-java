package com.study.jvm.demo1;

import java.io.IOException;

/**
 * 使用jdk自带的sa-jdi.jar包下的sun.jvm.hotspot.HSDB启动类来 类查看对象的内存信息
 */
public class HsdbDemo {
    public static final int i = 2;

    public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException {
        HsdbDemo hsdbDemo = new HsdbDemo();
        System.out.println(i);
        System.in.read();
    }
}




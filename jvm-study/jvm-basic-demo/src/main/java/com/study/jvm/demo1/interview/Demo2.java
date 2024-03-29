package com.study.jvm.demo1.interview;

public class Demo2 {

    /**
     * 二进制码对应的字节码命令：
     *  0 iconst_0
     *  1 istore_1
     *  2 iload_1
     *  3 iinc 1 by 1
     *  6 istore_1
     *  7 getstatic #2 <java/lang/System.out : Ljava/io/PrintStream;>
     * 10 iload_1
     * 11 invokevirtual #3 <java/io/PrintStream.println : (I)V>
     * 14 return
     */
    public static void main(String[] args) {
        int i = 0;
        i = i++;
        System.out.println(i); // 打印结果是0
    }

}

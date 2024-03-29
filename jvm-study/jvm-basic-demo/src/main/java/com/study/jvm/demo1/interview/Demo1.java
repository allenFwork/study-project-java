package com.study.jvm.demo1.interview;

public class Demo1 {

    /**
     * 二进制码对应的字节码命令：
     *  0 iconst_0
     *  1 istore_1
     *  2 iload_1
     *  3 iconst_1
     *  4 iadd
     *  5 istore_1
     *  6 getstatic #2 <java/lang/System.out : Ljava/io/PrintStream;>
     *  9 iload_1
     * 10 invokevirtual #3 <java/io/PrintStream.println : (I)V>
     * 13 return
     */
    public static void main(String[] args) {
        int i = 0;
        i = i + 1;
        System.out.println(i); // 打印结果是0
    }

}

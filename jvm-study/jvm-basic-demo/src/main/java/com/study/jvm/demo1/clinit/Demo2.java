package com.study.jvm.demo1.clinit;

/**
 * 加载 - 连接 - 初始化 - 使用 - 卸载
 * 类的初始化阶段测试案例
 *
 * 字节码指令：
 * 0 iconst_2                                                     将常量2放入操作数栈中
 * 1 putstatic #2 <com/study/jvm/demo1/clinit/Demo1.value : I>    从操作数栈中获取一个值，将该值设置到静态变量中区（此处的静态变量引用是#2，即value）
 * 4 iconst_1                                                     将常量1放入操作数栈中
 * 5 putstatic #2 <com/study/jvm/demo1/clinit/Demo1.value : I>    从操作数栈中获取一个值，将该值设置到静态变量中区（此处的静态变量引用是#2，即value）
 * 8 return
 */
public class Demo2 {

    static {
        value = 2;
    }

    public static int value = 1;

    public static void main(String[] args) {

    }

}

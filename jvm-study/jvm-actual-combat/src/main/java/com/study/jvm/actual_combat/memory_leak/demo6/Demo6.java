package com.study.jvm.actual_combat.memory_leak.demo6;

import java.util.ArrayList;
import java.util.List;

/**
 * 内存泄露问题：String的intern方法
 * JDK6中字符串常量池位于堆内存中的Perm Gen永久代中，
 * 如果不同字符串的intern方法被大量调用，字符串常量池会不停的变大超过永久代内存上限之后就会产生内存溢出问题。
 */
public class Demo6 {
    public static void main(String[] args) {
        while (true) {
            List<String> list = new ArrayList<String>();
            int i = 0;
            while (true) {
                // String.valueOf(i++).intern(); // JDK1.6 perm gen ，发现会被回收，所以不会溢出
                list.add(String.valueOf(i++).intern()); // 溢出
            }
        }
    }
}

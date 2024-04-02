package com.study.jvm.string_table;

/**
 * 字符串常量池
 */
public class StringTableDemo {
    public static void main(String[] args) {
        String a = "1";
        String b = "2";
        String c = "12";
        String d = a + b;
        System.out.println(c == d);  // false
        String d2 = "1" + "2";
        System.out.println(c == d2); // true
    }
}

package com.study.jvm.string_table;

/**
 * 字符串常量池
 */
public class StringTableDemo2 {
    // JDK6执行结果：false false
    // JDK8执行结果：true false
    public static void main(String[] args) {
        String str1 = new StringBuilder().append("think").append("123").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder().append("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }
}

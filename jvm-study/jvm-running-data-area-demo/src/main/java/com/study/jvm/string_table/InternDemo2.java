package com.study.jvm.string_table;

/**
 * 字符串常量池
 */
public class InternDemo2 {
    // JDK6执行结果：false false
    // JDK8执行结果：true false
    public static void main(String[] args) {
        String str1 = new StringBuilder().append("think").append("123").toString();
        // 前面返回常量池中的地址，后面返回堆中的地址
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder().append("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }
}

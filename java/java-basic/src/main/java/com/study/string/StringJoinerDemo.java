package com.study.string;

import java.util.StringJoiner;

public class StringJoinerDemo {

    public static void create() {
        // 1.创建StringJoiner对象，并且指定中间的间隔符号
        StringJoiner stringJoiner = new StringJoiner(" 分隔符 ");
        stringJoiner.add("a").add("b").add("c");
        System.out.println(stringJoiner); //a 分隔符 b 分隔符 c

        // 2.
        stringJoiner = new StringJoiner(" 分隔符 ", "前缀[ ", " ]后缀");
        stringJoiner.add("a").add("b").add("c");
        System.out.println(stringJoiner); //前缀[ a 分隔符 b 分隔符 c ]后缀
    }

    public static void main(String[] args) {
        create();
    }

}

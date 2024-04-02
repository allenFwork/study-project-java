package com.study.jvm.string_table;

import java.util.Scanner;

public class InternDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 接受字符串，并将其保存到常量池中，input1就是常量池中该字符串的地址
        String input1 = scanner.next().intern();
        String input2 = scanner.next().intern();

        // 如果两次输入的字符串是一样的，那么input1和input2会指向常量池中的同一个字符串
        System.out.println(input1 == input2);
    }
}

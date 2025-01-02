package com.study.datastructure.stack;

import java.util.LinkedList;

/**
 * 后缀表达式求值
 */
public class E02Leetcode150 {

    /*

        |   |
        | 13|
        | 4 |
        _____

        "4","13","5","/","+"

        - 遇到数字压入栈
        - 遇到运算符, 就从栈弹出两个数字做运算, 将结果压入栈
        - 遍历结束, 栈中剩下的数字就是结果
     */
    public int evalRPN(String[] tokens) {
        // JDK自带的java.util.LinkedList，既可以当作链表，也可以当作栈，因为该类实现了两种数据结构的所有方法
        LinkedList<Integer> stack = new LinkedList<>();
        for (String t : tokens) {
            switch (t) {
                case "+" -> {
                    // 栈中弹出两个数值
                    Integer b = stack.pop();
                    Integer a = stack.pop();
                    // 计算结果压入栈
                    stack.push(a + b);
                }
                case "-" -> {
                    Integer b = stack.pop();
                    Integer a = stack.pop();
                    stack.push(a - b);
                }
                case "*" -> {
                    Integer b = stack.pop();
                    Integer a = stack.pop();
                    stack.push(a * b);
                }
                case "/" -> {
                    Integer b = stack.pop();
                    Integer a = stack.pop();
                    stack.push(a / b);
                }
                default -> { // 数字
                    stack.push(Integer.parseInt(t));
                }
            }
        }
        return stack.pop();
    }
}

package com.study.generic;

import org.junit.Test;

public class StackTest {

    @Test
    public void test(){
        Stack stack = new Stack();
        stack.push(0);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }

    @Test
    public void test2() {
        StackT<Integer> stack = new StackT<Integer>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        StackT<String> stack2 = new StackT<>();
        stack2.push("1 呵呵");
        stack2.push("2 呵呵");
        stack2.push("3 呵呵");
        stack2.push("4 呵呵");
        System.out.println(stack2.pop());
        System.out.println(stack2.pop());
        System.out.println(stack2.pop());
        System.out.println(stack2.pop());
    }

    @Test
    public void test3() {
        int a = 1;
        int b = 2;
        swap(a, b); // 这里传到 swap 方法中的是具体的数值，不是地址
        System.out.println("a = " + a + ", b = " + b); // a = 1, b = 2
        int temp = a;
        a = b;
        b = temp;
        System.out.println("a = " + a + ", b = " + b); // a = 2, b = 1

    }

    public void swap(int a, int b) {
        int temp = a;
        a = b;
        b = temp;
    }

    @Test
    public void test4() {
        String a = "1";
        String b = "2";
        swap(a, b); //
        System.out.println("a = " + a + ", b = " + b); // a = 1, b = 2
        String temp = a;
        a = b;
        b = temp;
        System.out.println("a = " + a + ", b = " + b); // a = 2, b = 1

        String aa = new String("1");
        String bb = new String("2");
        swap(aa, bb); //
        System.out.println("aa = " + aa + ", bb = " + bb); // aa = 1, bb = 2

        change(a, b);
        System.out.println("a = " + a + ", b = " + b); // a = 2, b = 1

        /**
         * 依然没有交互
         * String 对象做为参数传递时，走的依然是引用传递，只不过String这个类比较特殊。
         * String 对象一旦创建，内容不可更改。每一次内容的更改都是重现创建出来的新对象。
         *
         * 结论:
         * 1. 值传递的时候，将实参的值，copy一份给形参。
         * 2. 引用传递的时候，将实参的地址值，copy一份给形参。
         * 也就是说，不管是值传递还是引用传递，形参拿到的仅仅是实参的副本，而不是实参本身。
         */
    }

    public void swap(String a, String b) {
        String temp = a;
        a = b;
        b = temp;
    }

    public void change(String a, String b) {
       a = "a change to A";
       b = "b change to B";
    }

}

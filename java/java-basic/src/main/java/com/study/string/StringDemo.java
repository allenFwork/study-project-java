package com.study.string;

public class StringDemo {

    // 1.创建对象
    public static void createObject() {
        //public String()：创建一个空白字符串对象，不含有任何内容
        String s1 = new String();
        System.out.println("s1:" + s1);

        //public String(char[] chs)：根据字符数组的内容，来创建字符串对象
        char[] chs = {'a', 'b', 'c'};
        String s2 = new String(chs);
        System.out.println("s2:" + s2);

        //public String(byte[] bys)：根据字节数组的内容，来创建字符串对象
        byte[] bys = {97, 98, 99};
        String s3 = new String(bys);
        System.out.println("s3:" + s3); // s3:abc

        //String s = “abc”; 直接赋值的方式创建字符串对象，内容就是abc
        String s4 = "abc";
        System.out.println("s4:" + s4);
    }

    // 2.字符串比较
    public static void stringCompare() {
        // 测试数据：
        String str1 = "abc";
        String str2 = "abc";
        String str3 = new String("abc");
        String str4 = new String(new byte[]{97, 98, 99});
        String str5 = new String(new char[]{'a', 'b', 'c'});

        // ==（等号）的作用
        System.out.println(str1 == str2); //false
        System.out.println(str1 == str3); //false
        System.out.println(str1 == str4); //false
        System.out.println(str1 == str5); //false

        // equals方法的作用
        System.out.println(str1.equals(str2)); //true
        System.out.println(str1.equals(str3)); //true
        System.out.println(str1.equals(str4)); //true
        System.out.println(str1.equals(str5)); //true
    }


    public static void main(String[] args) {
//        createObject();
        stringCompare();
    }

}
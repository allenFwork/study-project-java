package com.study.string;

public class StringBuilderDemo {

    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        //容量：最多装多少
        System.out.println("StringBuilder对象的默认容量：" + stringBuilder.capacity()); //16
        //长度：已经装了多少
        System.out.println("StringBuilder对象的长度：" + stringBuilder.length()); //0

        stringBuilder.append("abc");
        System.out.println("StringBuilder对象的默认容量：" + stringBuilder.capacity()); //16
        System.out.println("StringBuilder对象的长度：" + stringBuilder.length());       //3

        stringBuilder.append("defghuigklmnopqrstuvwxyz");
        System.out.println("StringBuilder对象的默认容量：" + stringBuilder.capacity()); //34
        System.out.println("StringBuilder对象的长度：" + stringBuilder.length());       //27

        stringBuilder.append("0123456789");
        System.out.println("StringBuilder对象的默认容量：" + stringBuilder.capacity()); //70
        System.out.println("StringBuilder对象的长度：" + stringBuilder.length());       //36

        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("abcdefghijklmnopqrstuvwxyz0123456789");
        System.out.println(stringBuilder2.capacity()); //36
        System.out.println(stringBuilder2.length());   //36
    }

}

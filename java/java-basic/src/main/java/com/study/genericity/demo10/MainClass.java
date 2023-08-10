package com.study.genericity.demo10;

import java.util.ArrayList;

public class MainClass {

    public static void main(String[] args) {

//        ArrayList[] lists = new ArrayList[5];
//        ArrayList<String>[] listArr = listArr;

        ArrayList<String>[] listArr = new ArrayList[5];

        ArrayList<Integer> intList = new ArrayList<>();
        intList.add(100);
        // listArr[0] = intList; 编译报错

        ArrayList<String> strList = new ArrayList<>();
        strList.add("abc");
        listArr[0] = strList;

        String s = listArr[0].get(0);
        System.out.println(s);

        System.out.println("-----------------------------------------------");

    }

}

package com.study.type;

import java.util.ArrayList;
import java.util.List;

public class ListDemo {

    public static void main(String[] args) {
        List<String> list = new ArrayList();
        list.add("1");
        list.add("2");
        for (String o : list) {
            System.out.println(o);
        }

        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        System.out.println(list.subList(0, 2));
        System.out.println(list.subList(0, list.size()));
        if (list.size() % 2 == 0) {
            for (int i = 0; i < list.size() / 2; i++) {
                System.out.println(list.subList(i * 2, (i + 1) * 2));
            }
        } else {
            int i = 0;
            for (; i < list.size() / 2; i++) {
                System.out.println(list.subList(i * 2, (i + 1) * 2));
            }
            System.out.println(list.subList(i * 2, list.size()));
        }

        int count = list.size() /2 ;
        if (list.size() % 2 == 0) {
            for (int i = 0; i < count / 2; i++) {
                System.out.println(list.subList(0, 2));
                list.subList(0, 2).clear();
            }
        } else {
            int i = 0;
            for (; i < count; i++) {
                System.out.println(list.subList(0, 2));
                list.subList(0, 2).clear();
            }
            System.out.println(list.subList(0, list.size()));
        }


        list = null;
        // java.lang.NullPointerException
        for (String s : list) {
            System.out.println(s);
        }

    }

}

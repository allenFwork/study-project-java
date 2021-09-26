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
        list = null;
        // java.lang.NullPointerException
        for (String s : list) {
            System.out.println(s);
        }
    }

}

package com.study.util;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * TreeMap的使用
 */
public class TreeMapDemo {
    public static void main(String[] args) {

        SortedMap<String, Integer> sortedMap = new TreeMap<>();

        sortedMap.put("1", 1);
        sortedMap.put("2", 2);
        sortedMap.put("3", 3);
        sortedMap.put("4", 4);
        sortedMap.put("5", 5);
        sortedMap.put("6", 6);
        sortedMap.put("7", 7);

        // 找大于等于4的子树
        SortedMap subMap = sortedMap.tailMap("4");
        // 获取树中第一个元素
        System.out.println(subMap.firstKey()); // 4

    }
}

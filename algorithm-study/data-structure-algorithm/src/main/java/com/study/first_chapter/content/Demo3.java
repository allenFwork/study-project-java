package com.study.first_chapter.content;

import java.util.Comparator;

/**
 * 1.6 函数对象学习
 */
public class Demo3 {

    public static <T> T findMax(T[] arr, Comparator<? super T> comparator) {
        int maxIndex = 0;
        for (int i = 0; i < arr.length; i++) {
            if (comparator.compare(arr[i], arr[maxIndex]) > 0) {
                maxIndex = i;
            }
        }
        return arr[maxIndex];
    }

    // 函数对象
    static class CaseInsensitiveCompare implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
//            return o1.compareToIgnoreCase(o2);
            return o1.compareTo(o2);
        }
    }

    public static void main(String[] args) {
        String[] arr = {"ZEBRA", "alligator", "crocodile"};
        System.out.println(findMax(arr, new CaseInsensitiveCompare()));
    }

}

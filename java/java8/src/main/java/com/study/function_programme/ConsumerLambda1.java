package com.study.function_programme;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 使用Lambda表达式创建Consumer: 基础类型操作
 */
public class ConsumerLambda1 {

    public static void main(String[] args) {
        // 奇数集合
        List<Integer> oddList = new ArrayList<>();
        // 偶数集合
        List<Integer> evenList = new ArrayList<>();

        /**
         * 设置实现功能:
         * 一个用于将数字添加到列表的方法，如果数字为奇数，则将添加到具有奇数的列表中；
         * 如果数字为偶数，则将其添加到具有偶数的另一个列表中。
         */
        Consumer<Integer> storeNumber = n -> {
            if (n % 2 == 0)
                evenList.add(n);
            else
                oddList.add(n);
        };

        // 设置实现的功能: 打印集合中的每个元素
        Consumer<List<Integer>> printList = list -> list.forEach(x -> System.out.println(x));

        // 执行
        storeNumber.accept(10);
        storeNumber.accept(11);
        storeNumber.accept(12);
        storeNumber.accept(13);
        storeNumber.accept(14);
        storeNumber.accept(15);

        System.out.println(" ======== Old number ========");
        printList.accept(oddList);
        System.out.println(" ======== Even number ========");
        printList.accept(evenList);
    }

}

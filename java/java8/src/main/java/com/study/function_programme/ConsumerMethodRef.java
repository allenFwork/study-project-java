package com.study.function_programme;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 使用方法引用创建Consumer:
 * 在例子中，有一个有两个方法的实用类Utility，其中一个方法将替换Map中的值，
 * 第二个方法显示Map中的数据。我们将使用方法引用来创建Consumer。
 */
public class ConsumerMethodRef {

    static class Utility {
        static void update(Map<Integer, String> persons) {
            persons.replaceAll((key, value) -> "Sherry".concat(value));
        }

        static void displayData(Map<Integer, String> persons) {
            // 普通的遍历方法
            for (Map.Entry<Integer, String> entry : persons.entrySet()) {
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
            // forEach的使用方法
            persons.entrySet().forEach(entry -> System.out.println(entry.getKey() + " - " + entry.getValue()));
        }
    }

    public static void main(String[] args) {
        Map<Integer, String> persons = new HashMap<Integer, String>();
        persons.putIfAbsent(101, "Jack");
        persons.putIfAbsent(102, "Tom");

        Consumer<Map<Integer, String>> updatePersons = Utility::update;
        Consumer<Map<Integer, String>> displayPersons = Utility::displayData;

        System.out.println("======== 显示最初版本 ========");
        displayPersons.accept(persons);
        System.out.println("============ 更新 ============");
        updatePersons.accept(persons);
        System.out.println("======== 显示更新版本 ========");
        displayPersons.accept(persons);
    }

}

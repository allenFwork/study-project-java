package com.study.lambda;

public class CustomizedObject {

    private int id;
    private String name;

    public CustomizedObject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // 创建一个自定义的对象，进行方法的回调
    public static void create(Integer id, String name, MethodCallback methodCallback) {
        CustomizedObject customizedObject = new CustomizedObject(id , name);
        methodCallback.callback(customizedObject);
    }

    public static void main(String[] args) {

        // 1. 通过匿名构造类进行方法的回调
        CustomizedObject.create(1, "superman", new MethodCallback() {
            @Override
            public void callback(CustomizedObject customizedObject) {
                System.out.println("He can fly ... ");
            }
        });
        CustomizedObject.create(2, "batman", new MethodCallback() {
            @Override
            public void callback(CustomizedObject customizedObject) {
                System.out.println("He hides his shadow ...");
            }
        });


        /*
         * lambda表达式定义：
         *  一个lambda表达式实现了接口里的有且仅有的唯一一个抽象方法，那么对于这种接口就叫做函数式接口
         *  lambda表达式实质就是 实现接口并且实现接口里的方法
         */

        // 2. lambda表达式
        CustomizedObject.create(3, "superGirl", (CustomizedObject customizedObject) -> {
            System.out.println("she can fly ...");
        });
        // 简化
        CustomizedObject.create(3, "superGirl", customizedObject -> {
            System.out.println("she can fly ...");
        });

    }

}

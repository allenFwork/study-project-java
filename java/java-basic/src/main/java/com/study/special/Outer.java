package com.study.special;


/**
 * Java中Class.this和this的区别（转）
 * 当inner class（内部类）必顺使用到outer class（外部类）的this instance（实例）时，或者匿名内部类要使用外部类的实例。
 */
public class Outer {

    String data = "外部类別";
    private String outerValue;

    public Outer(String outerValue) {
        this.outerValue = outerValue;
    }

    public class Inner {

        String data = "內部类別";

        public String getOuterData() {
            System.out.println("Inner: data = " + data);
            // 获取外部类对象实例,从该实例中获取对应的data属性
            System.out.println("Outer: data = " + Outer.this.data);
            System.out.println("Outer: outerValue = " + outerValue);
            // 此处的this只的是Inner对象实例
            System.out.println("this.data = " + this.data);
            return Outer.this.data;
        }

    }

    /**
     * 通过该方法能够从外部调用Inner内的方法
     */
    public void executeInnerMethod() {
        new Inner().getOuterData();
    }

}

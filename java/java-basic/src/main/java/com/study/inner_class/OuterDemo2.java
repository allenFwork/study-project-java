package com.study.inner_class;

// 静态内部类
public class OuterDemo2 {

    String name = "outer name";
    private String outerMemberVariable;

    public void setOuterMemberVariable(String outerMemberVariable) {
        this.outerMemberVariable = outerMemberVariable;
    }

    public void outerMemberMethod() {
        System.out.println("外部类方法被调用||Outer.outerMemberMethod ... ");
    }

    // 静态内部类 Inner
    public static class Inner {

        String name = "inner name";
        static String name2 = "inner static name";
        private String innerMemberVariable;

        public Inner(String innerMemberVariable) {
            this.innerMemberVariable = innerMemberVariable;
        }

        public void innerMemberMethod() {
            System.out.println("内部类方法被调用||Outer.Inner.innerMemberMethod ... ");
        }

    }


    public static void main(String[] args) {

        // 外部类就是一个正常的、普通的类
        OuterDemo2 outerDemo2 = new OuterDemo2();
        outerDemo2.setOuterMemberVariable("outer value");
        outerDemo2.outerMemberMethod();
        System.out.println(outerDemo2.name);

        // 创建静态内部类对象
        OuterDemo2.Inner inner = new OuterDemo2.Inner("inner value");
        inner.innerMemberMethod();
        System.out.println(inner.innerMemberVariable);
        // 获取静态内部类的静态变量数据
        System.out.println(OuterDemo2.Inner.name2);

    }

}

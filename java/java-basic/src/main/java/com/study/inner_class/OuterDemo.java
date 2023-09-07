package com.study.inner_class;

// 成员内部类
public class OuterDemo {

    String name = "outer name";
    private String outerMemberVariable;

    public void setOuterMemberVariable(String outerMemberVariable) {
        this.outerMemberVariable = outerMemberVariable;
    }

    public void outerMemberMethod() {
        System.out.println("外部类方法被调用||Outer.outerMemberMethod ... ");
    }

    // 成员内部类 Inner
    class Inner {

        String name = "inner name";
        // static String name2; 报错，因为在成员内部类中，JDK16以前不能定义静态变量，JDK16开始可以定义静态变量
        private String innerMemberVariable;

        public void setInnerMemberVariable(String innerMemberVariable) {
            this.innerMemberVariable = innerMemberVariable;
        }

        public void innerMemberMethod() {
            System.out.println("内部类方法被调用||Outer.Inner.innerMemberMethod ... ");
        }

        public void nameCompare() {
            String name = "inner method name";
            System.out.println(name);
            System.out.println(this.name);
            System.out.println(OuterDemo.this.name);
        }

    }

    private class Inner2 {

        private String inner2MemberVariable;

        public void setInner2MemberVariable(String inner2MemberVariable) {
            this.inner2MemberVariable = inner2MemberVariable;
        }

        public void inner2MemberMethod() {
            System.out.println("私有内部类方法被调用||Outer.Inner2.inner2MemberMethod ... ");
        }

    }


    public static void main(String[] args) {

        // 外部类就是一个正常的、普通的类
        OuterDemo outerDemo = new OuterDemo();
        outerDemo.setOuterMemberVariable("outer value");
        outerDemo.outerMemberMethod();
        System.out.println(outerDemo.name);

        System.out.println("------------------------------------------------");
        // 内部类的使用
        OuterDemo.Inner inner = new OuterDemo().new Inner();
        inner.setInnerMemberVariable("inner value");
        inner.innerMemberMethod();
        System.out.println(inner.name);

        System.out.println("------------------------------------------------");
        // 私有内部类的使用
        OuterDemo.Inner2 inner2 = new OuterDemo().new Inner2();
        inner2.setInner2MemberVariable("inner2 value");
        inner2.inner2MemberMethod();

        System.out.println("------------------------------------------------");
        // name变量值的对比：
        inner.nameCompare();

    }

}

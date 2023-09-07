package com.study.inner_class;

// 局部内部类
public class OuterDemo3 {

    private String outerMemberVariable;

    public void method() {
        //局部内部类
        class Inner {
            private String innerMemberVariable;
        }
    }

}

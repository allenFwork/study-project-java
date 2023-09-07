package com.study.inner_class;

interface Swim {
    void swimming();
}

// 匿名内部类
public class OuterDemo4 {

    public static void main(String[] args) {
        //使用匿名内部类: 定义完直接调用
        new Swim() {
            @Override
            public void swimming() {
                System.out.println("自由泳 ... ");
            }
        }.swimming();

        //使用匿名内部类：接口 变量 = new 实现类(); 再通过变量调用
        Swim swim = new Swim() {
            @Override
            public void swimming() {
                System.out.println("蛙泳 ... ");
            }
        };
        swim.swimming();

        //使用匿名内部类：方法形参中使用
        goSwimming(new Swim() {
            @Override
            public void swimming() {
                System.out.println("去游泳，自由泳 ... ");
            }
        });
    }

    public static void goSwimming(Swim swim) {
        swim.swimming();
    }

}



package com.study.proxy.statcProxy;

/**
 * 静态代理1：继承实现
 */
public class ProxyObject1 extends TargetObject {

    public void sayHello() {
        System.out.println("static proxy : extends : before ... ");
        super.sayHello();
        System.out.println("static proxy : extends : after  ...");
    }

    public String test(String input) {
        System.out.println("static proxy : extends : before ... ");
        String str = super.test(input);
        System.out.println("static proxy : extends : after  ...");
        return str;
    }


}

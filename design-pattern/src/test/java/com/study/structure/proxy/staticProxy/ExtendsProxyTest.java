package com.study.structure.proxy.staticProxy;


public class ExtendsProxyTest {

    public static void main(String[] args) {
        ProxyObject1 proxyObject = new ProxyObject1();
        proxyObject.sayHello();
        proxyObject.test("extends");
    }

}

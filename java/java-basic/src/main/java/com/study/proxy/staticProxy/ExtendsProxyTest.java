package com.study.proxy.staticProxy;

import com.study.proxy.statcProxy.ProxyObject1;

public class ExtendsProxyTest {

    public static void main(String[] args) {
        ProxyObject1 proxyObject = new ProxyObject1();
        proxyObject.sayHello();
        proxyObject.test("extends");
    }

}

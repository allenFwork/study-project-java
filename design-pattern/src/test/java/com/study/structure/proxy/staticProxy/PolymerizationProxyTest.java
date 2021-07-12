package com.study.structure.proxy.staticProxy;

public class PolymerizationProxyTest {

    public static void main(String[] args) {
        ProxyObject2 proxyObject = new ProxyObject2(new TargetObject());
        proxyObject.sayHello();
        proxyObject.test(null);
    }

}

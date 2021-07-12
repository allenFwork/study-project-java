package com.study.test.proxy.staticProxy;

import com.study.proxy.statcProxy.ProxyObject2;
import com.study.proxy.statcProxy.TargetObject;

public class PolymerizationProxyTest {

    public static void main(String[] args) {
        ProxyObject2 proxyObject = new ProxyObject2(new TargetObject());
        proxyObject.sayHello();
        proxyObject.test(null);
    }

}

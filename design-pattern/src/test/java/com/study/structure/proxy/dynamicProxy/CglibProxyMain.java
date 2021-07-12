package com.study.structure.proxy.dynamicProxy;

import com.study.structure.proxy.dynamicProxy.cglib.MyMethodInterceptor;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

public class CglibProxyMain {

    public static void main(String[] args) {

        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "Z:\\document\\study-code\\cglib");

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TargetObject.class);
        enhancer.setCallback(new MyMethodInterceptor());
        TargetObject proxyObject = (TargetObject) enhancer.create();
        proxyObject.sayHello();

//        try {
//            System.in.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

}

package com.study.test.proxy.dynamicProxy;

import com.study.proxy.dynamicProxy.Target;
import com.study.proxy.dynamicProxy.TargetObject;
import com.study.proxy.dynamicProxy.jdk.MyInvocationHandler;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;

public class JdkProxyMain {

    public static void main(String[] args) {

        /**
         * 配置系统属性sun.misc.ProxyGenerator.saveGeneratedFile为true，
         * 代理类生成时将自动将生成的代理类写入硬盘.
         * 生成代理类的 .class文件 到 根目录下的 com.sun.proxy 下
         */
//        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        // jdk 动态代理使用
        Target target = new TargetObject();
        Target proxyObject = (Target) Proxy.newProxyInstance(JdkProxyMain.class.getClassLoader(), target.getClass().getInterfaces(), new MyInvocationHandler(target));
        proxyObject.sayHello();
        testProxyGenetate();

    }

    // 生成代理类的.class 文件到磁盘
    public static void testProxyGenetate() {
        byte[] newProxyClass = ProxyGenerator.generateProxyClass("$Proxy0", TargetObject.class.getInterfaces());
        System.out.println(newProxyClass);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File("Z:\\study\\code\\jdk\\$Proxy0.class"));
            try {
                fileOutputStream.write(newProxyClass);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}

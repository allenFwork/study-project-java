package com.study.dubbo.client;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.study.dubbo.server.UserService;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 客户端的简单模拟
 * dubbo的客户端进行配置，只使用代码进行配置，不适用xml配置
 */
public class SimpleClient {

    /**
     * 基于 url 构建远程服务 (手动输入调用地址)
     */
    public UserService buildRemoteService(String remoteUrl) {
        ReferenceConfig<UserService> referenceConfig = new ReferenceConfig();
        // 设置接口
        referenceConfig.setInterface(UserService.class);
        // 设置调用的地址
        referenceConfig.setUrl(remoteUrl);
        // 设置客户端的此服务的名称
        referenceConfig.setApplication(new ApplicationConfig("simple-client-app"));
        // 返回 UserService接口类型的实现类对象（代理对象，远程调用）
        return referenceConfig.get();
    }

    /**
     * 基于 url 构建远程服务（通过注册中心）
     */
    public UserService buildRemoteService2() {
        ReferenceConfig<UserService> referenceConfig = new ReferenceConfig();
        // 设置接口
        referenceConfig.setInterface(UserService.class);
        // 设置注册中心(获取远程调用的地址信息和接口信息)
        referenceConfig.setRegistry(new RegistryConfig("multicast://224.1.2.3:11111"));
        // 设置客户端的此服务的名称
        referenceConfig.setApplication(new ApplicationConfig("simple-client-app"));
        // 设置负载均衡策略
        referenceConfig.setLoadbalance("roundrobin");
        // 返回 UserService接口类型的实现类对象（代理对象，远程调用）
        return referenceConfig.get();
    }

    public static void main(String[] args) throws IOException {

        SimpleClient simpleClient = new SimpleClient();

        // 1.通过直接设置远程地址进行调用
//        // dubbo协议，com.study.dubbo.server.UserService接口
//        UserService userService = simpleClient.buildRemoteService("dubbo://127.0.0.1:20880/com.study.dubbo.server.UserService");
//        UserService userService1 = simpleClient.buildRemoteService("dubbo://127.0.0.1:20881/com.study.dubbo.server.UserService");
//        UserService userService2 = simpleClient.buildRemoteService("dubbo://127.0.0.1:20882/com.study.dubbo.server.UserService");
//        List<UserService> list = new ArrayList<>();
//        list.add(userService);
//        list.add(userService1);
//        list.add(userService2);
//        // 通过轮询的方式调用
//        int count = 0;
//        String command;
//        while (!"exist".equals(command = read())) {
//            System.out.println(list.get(count++ % list.size()).getUser(111));
//        }

        // 2.通过注册中心获取远程调用信息调用
        UserService userService = simpleClient.buildRemoteService2();
        // 通过轮询的方式调用
        int count = 0;
        String command;
        while (!"exist".equals(command = read())) {
            System.out.println(userService.getUser(111));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(userService.getUser(111));

    }

    public static String read() throws IOException {
        LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(System.in));
        return lineNumberReader.readLine();
    }

}

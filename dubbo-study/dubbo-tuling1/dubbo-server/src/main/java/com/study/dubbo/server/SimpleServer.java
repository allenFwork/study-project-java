package com.study.dubbo.server;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.study.dubbo.server.impl.UserServiceImpl;

import java.io.IOException;

/**
 * 服务端：提供简单的服务
 * dubbo的服务端配置，只使用代码进行配置，不使用xml配置
 */
public class SimpleServer {

    /**
     * 设置服务，暴露服务的接口
     */
    public void openServer(int port) {

        // 1.服务配置
        ServiceConfig<UserService> serviceConfig = new ServiceConfig();
        // 1.1 构建应用配置对象
        ApplicationConfig config = new ApplicationConfig();
        // 1.2 设置应用的名字
        config.setName("simple-server-app");
        // 1.3 将应用配置对象赋值到服务配置对象中
        serviceConfig.setApplication(config);

        // 2.设置通信协议
        // 2.1 创建协议配置对象：dubbo协议、端口号
        ProtocolConfig protocolConfig = new ProtocolConfig("dubbo", port);
        // 2.2 设置线程数
        protocolConfig.setThreads(200);
        // 2.3 将协议配置赋值给服务配置对象
        serviceConfig.setProtocol(protocolConfig);

        // 3.服务配置设置暴露的接口
        serviceConfig.setInterface(UserService.class);

        // 4.服务配置设置具体的实现
        UserServiceImpl ref = new UserServiceImpl();
        serviceConfig.setRef(ref);

        // 5.dubbo必须要设置一个注册中心
        // 此处设置一个空的注册中心
//        serviceConfig.setRegistry(new RegistryConfig(RegistryConfig.NO_AVAILABLE));
        // 使用广播注册中心, UDP,虚拟IP：定时的向 224.1.2.3:11111地址传递消息
        serviceConfig.setRegistry(new RegistryConfig("multicast://224.1.2.3:11111"));
//        serviceConfig.setRegistry(new RegistryConfig("zookeeper://192.168.0.147:2181"));

        // 6.使用该方法，将服务暴露出去，开始提供服务，开张做生意
        serviceConfig.export();

        System.out.println("服务已开启!端口:" + serviceConfig.getExportedUrls().get(0).getPort());
//        ref.setPort(serviceConfig.getExportedUrls().get(0).getPort());
    }

    public static void main(String[] args) throws IOException {
        /**
         * 传入 20880，表示开启一个服务，端口号为 20880
         * 传入 -1，表示开启多个服务，端口号默认从20880开始
         */
//        new SimpleServer().openServer(20880);
        new SimpleServer().openServer(-1);
        System.in.read();
    }

}

package com.study.demo.rpc.client.netty;

import com.study.demo.rpc.client.service.Service;

/**
 * 模拟 RPC 框架的使用
 * 本地接口调用方法,通过远程返回该方法的结果 等价于 在本地调用
 */
public class ClientSocketNetty {
    public static void main(String[] args) throws InterruptedException {
//        while (true){
//            long start = System.currentTimeMillis();
//            TestService o = (TestService) ClientRpcProxy.create(TestService.class);
//            System.out.println(o.listAll());
//            long end = System.currentTimeMillis();
//            System.out.println(end-start);
//            Thread.sleep(1000);
//        }
        // 获取实例对象
        Service o2 = (Service) ClientRpcProxy.create(Service.class);
        // 调用对象的方法
        System.out.println(o2.listById(0));
    }
}

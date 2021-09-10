package com.study.exercise.netty.rpc.v1;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class RpcBuilder {

    // 装有 接口名-实现类实例 的 map
    Map<String, Object> services = new ConcurrentHashMap<>();
    // 装有 接口名-接口类对象 的 map
    Map<String, Class> interfaces = new ConcurrentHashMap<>();

    public void publishService(Class serviceInterface, Object service) {
        if (services.containsKey(serviceInterface.getName()) || interfaces.containsKey(serviceInterface.getName())) {
            throw new RpcException("serviceInterface has been already registered......");
        }

        if (!(serviceInterface.isInstance(service))) {
            throw new RpcException("service object must implement the service Interface .......");
        }

        services.put(serviceInterface.getName(), service);
        interfaces.put(serviceInterface.getName(), serviceInterface);
    }

    public RpcResponse invokeService(RpcRequest rpcRequest) {
        // 拿到接口名
        String serviceName = rpcRequest.getServiceName();
        // 拿到方法名，add
        String methodName = rpcRequest.getMethodName();
        // 拿到具体的接口，CalculatorService
        Class serviceInterface = interfaces.get(serviceName);
        // 拿到请求的参数
        double p1 = rpcRequest.getParam1();
        double p2 = rpcRequest.getParam2();

        Method method;
        Object result = null;
        try {
            // 通过反射获取远程调用时的方法
            method = serviceInterface.getMethod(methodName, new Class[]{double.class, double.class});
            // 调用方法获取返回对象
            result = method.invoke(services.get(serviceName), new Object[]{p1, p2});
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 封装返回报文实体到RpcResponse对象中
        RpcResponse response = new RpcResponse();
        response.setId(rpcRequest.getId());
        response.setServiceName(serviceName);
        response.setMethodName(methodName);
        response.setResult((double) result);

        return response;
    }

}

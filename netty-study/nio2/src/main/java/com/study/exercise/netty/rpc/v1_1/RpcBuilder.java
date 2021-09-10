package com.study.exercise.netty.rpc.v1_1;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.study.exercise.netty.rpc.v1.RpcException;

public class RpcBuilder {
    Map<String, Object> services = new ConcurrentHashMap<>();
    Map<String, Class<?>> interfaces = new ConcurrentHashMap<>();

    public void publishService(Class<?> serviceInterface, Object service) {
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
        String serviceName = rpcRequest.getServiceName();
        String methodName = rpcRequest.getMethodName();
        Class<?> serviceInterface = interfaces.get(serviceName);
        String[] parameterTypeNames = rpcRequest.getParameterTypeNames();

        Method method;
        Object result = null;

        Class<?>[] parameterClasses = new Class<?>[parameterTypeNames.length];
        try {
            for (int i = 0; i < parameterClasses.length; i++) {
            	// 获取参数的类型名称，并通过该名称获取对应的类对象，将其放入数组中
                parameterClasses[i] = ClassUtils.getClass(parameterTypeNames[i]);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
        	// 通过反射，获取方法对象
            method = serviceInterface.getMethod(methodName, parameterClasses);
            // 调用该方法
            result = method.invoke(services.get(serviceName), rpcRequest.getArgs());

        } catch (Exception e) {
            e.printStackTrace();
        }

        RpcResponse response = new RpcResponse();
        response.setId(rpcRequest.getId());
        response.setMethodName(methodName);
        response.setResult((double) result);

        return response;
    }

}

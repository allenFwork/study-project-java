package com.study.dubbo.server;

import com.alibaba.dubbo.rpc.*;

/**
 * 自定义的过滤器，将其作为 dubbo 的内置过滤器
 */
public class CustomizedFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        System.out.println("执行自定义过滤器 ... ");
        return invoker.invoke(invocation);
    }

}

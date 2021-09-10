package com.study.exercise.netty.rpc.v1;

import java.util.UUID;
import java.util.concurrent.SynchronousQueue;

/**
 * 此处是通过 实现 接口，完成代理类的实现的
 * 本地接口的代理实现，理论上是动态代理实现的，调用时被封装看不到该代理实例的
 * 此处以 CalculatorService接口为准，实现了该接口的实现类
 */
public class CalculatorServiceProxy implements CalculatorService {

    private NettyClient client;

    // 该代理类的作用是调用远程接口，所以需要netty客户端实例
    public CalculatorServiceProxy(String host, int port) {
        client = new NettyClient();
        try {
            // 初始化客户端，即获取连接远程的SocketChannel对象，此处是NioSocketChannel
            client.init(host, port, SerializerFactory.getSerializer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 代理接口的实际实现逻辑
    @Override
    public double add(double op1, double op2) {
        String id = UUID.randomUUID().toString();

        RpcRequest request = new RpcRequest();
        request.setId(id);
        request.setServiceName(CalculatorService.class.getName());
        request.setMethodName("add");
        request.setParam1(op1);
        request.setParam2(op2);

        RpcResponse response = this.sendRPCRequest(request);

        return response.getResult();
    }

    @Override
    public double subtraction(double op1, double op2) {
        String id = UUID.randomUUID().toString();

        RpcRequest request = new RpcRequest();
        request.setServiceName(CalculatorService.class.getName());
        request.setId(id);
        request.setMethodName("sub");
        request.setParam1(op1);
        request.setParam2(op2);

        RpcResponse response = this.sendRPCRequest(request);

        return response.getResult();
    }

    @Override
    public double multiply(double op1, double op2) {
        String id = UUID.randomUUID().toString();

        RpcRequest request = new RpcRequest();
        request.setServiceName(CalculatorService.class.getName());

        request.setId(id);
        request.setMethodName("mul");
        request.setParam1(op1);
        request.setParam2(op2);

        RpcResponse response = this.sendRPCRequest(request);

        return response.getResult();
    }

    // 实现调用远程接口逻辑
    private RpcResponse sendRPCRequest(RpcRequest request) {
    	// 创建一个 SynchronousQueue 队列对象，此时该队列内部是空的
        SynchronousQueue<RpcResponse> queue = new SynchronousQueue();
        // 调用远程接口对应的唯一标识id 和 队列 放入 全局单例变量 map 中
        NettyClient.putSynchronousQueue(request.getId(), queue);
        RpcResponse response = null;
        try {
            client.sendRpcRequest(request);
			/**
			 * SynchronousQueue 的 take()方法作用：
			 *   取出并且remove掉queue里的element（认为是在queue里的。。。），取不到东西他会一直等。
			 * SynchronousQueue对象中如果是空的，那么就会一直阻塞在这里。
			 */
			response = queue.take();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return response;
    }

    public void stop() {
        client.close();
    }

}

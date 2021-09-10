package com.study.exercise.netty.rpc.v1;

/**
 * 启动远程服务端
 */
public class RpcDemoServer {

	public static void main(String[] args) {

		// 在此版本的rpc框架模拟中，RpcBuilder充当了远程注册服务的作用，只有一个RpcBuilder对象
		RpcBuilder builder = new RpcBuilder();
		// 发布服务接口（服务名称） 和 服务实现
		builder.publishService(CalculatorService.class, new CalculatorServiceImpl());
		
		NettyServer server = new NettyServer(8080);
		try {
			// 将 RpcBuilder 对象传到 Netty服务端
			server.start(builder);
		} catch (Exception e) {
 			e.printStackTrace();
		}
		
	}

}

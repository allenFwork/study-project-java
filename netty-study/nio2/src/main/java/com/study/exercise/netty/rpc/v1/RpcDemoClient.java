package com.study.exercise.netty.rpc.v1;

/**
 * 客户端启动：
 * 	 客户端并不知道要调用远程接口，以为是直接调用本地方法就可以了
 */
public class RpcDemoClient {

	public static void main(String[] args) {

		// CalculatorService接口的代理类，通过代理实例调用接口方法
		CalculatorServiceProxy proxy = new CalculatorServiceProxy("127.0.0.1", 8080);
		
		System.out.println(proxy.add(1.0, 2.0));
		
		proxy.stop();
	}

}

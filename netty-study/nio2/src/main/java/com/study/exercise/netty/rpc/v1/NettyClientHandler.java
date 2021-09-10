package com.study.exercise.netty.rpc.v1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.SynchronousQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

	private static final Logger logger = LoggerFactory.getLogger(NettyClientHandler.class);

	@Override
	public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) throws Exception {
		logger.error("远程连接异常：", cause);
		channelHandlerContext.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse rpcResponse) throws Exception {
		String id = rpcResponse.getId();
		SynchronousQueue<RpcResponse> synchronousQueue = NettyClient.getSynchronousQueue(id);
		/**
		 * SynchronousQueue是无界的，是一种无缓冲的等待队列，但是由于该Queue本身的特性，
		 * 在某次添加元素后必须等待其他线程取走后才能继续添加。
		 *
		 *  注意1：它一种阻塞队列，其中每个 put 必须等待一个 take，反之亦然。
		 *         同步队列没有任何内部容量，甚至连一个队列的容量都没有。
		 *  注意2：它是线程安全的，是阻塞的。
		 *  注意3:不允许使用 null 元素。
		 *  注意4：公平排序策略是指调用put的线程之间，或take的线程之间。
		 *  公平排序策略可以查考ArrayBlockingQueue中的公平策略。
		 *
		 *  put() 往queue放进去一个element以后就一直wait直到有其他thread进来把这个element取走。
		 */
		synchronousQueue.put(rpcResponse);
		NettyClient.removeById(id);
	}

}

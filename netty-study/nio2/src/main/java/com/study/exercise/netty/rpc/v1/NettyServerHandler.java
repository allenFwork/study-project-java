package com.study.exercise.netty.rpc.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 服务端业务流程处理器
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

    private RpcBuilder rpcBuilder;

    public NettyServerHandler(RpcBuilder rpcBuilder) {
        this.rpcBuilder = rpcBuilder;
    }

    @Override
    public void channelRead0(final ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) throws Exception {
        // 调用具体的业务方法，调用 CalculatorService.add方法
        RpcResponse rpcResponse = rpcBuilder.invokeService(rpcRequest);
        // 把响应返回给客户端
        channelHandlerContext.writeAndFlush(rpcResponse);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        channelHandlerContext.close();
    }
}

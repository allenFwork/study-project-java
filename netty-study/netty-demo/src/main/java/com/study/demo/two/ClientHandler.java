package com.study.demo.two;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class  ClientHandler extends SimpleChannelInboundHandler<String> {
    // 读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(channelHandlerContext.channel().remoteAddress() + ", client output:" + s);
    }

    // 通道就绪
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        for (int i = 0; i < 10; i++) {
            channelHandlerContext.writeAndFlush("来自客户端的问候");
        }
    }

    // 异常发生
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) throws Exception {
        cause.printStackTrace();
        channelHandlerContext.channel().close();
    }
}

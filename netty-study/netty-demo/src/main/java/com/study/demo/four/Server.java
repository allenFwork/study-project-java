package com.study.demo.four;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 服务端启动类
 */
public class Server {
    public static void main(String[] args) {
        // 接收客户端连接的线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 真正处理读写事件的线程组
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                           .channel(NioServerSocketChannel.class)
                           // 服务器的服务端添加一个日志处理器（netty提供的）
                           .handler(new LoggingHandler(LogLevel.INFO))
                           // 服务器的客户端添加一个处理器（用来初始化处理器的处理器）
                           .childHandler(new ServerInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(8989).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}

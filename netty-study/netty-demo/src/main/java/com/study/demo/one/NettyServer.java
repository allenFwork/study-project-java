package com.study.demo.one;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * netty服务端
 */
public class NettyServer {

    public static void main(String[] args) {
        // 接收客户端连接的线程组 主线程
        EventLoopGroup boosGroup = new NioEventLoopGroup(1);
        // 真正处理读写时间的线程组 从线程-工作线程
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            // 创建服务器启动助手来配置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();
                           // 设置两个线程组
            serverBootstrap.group(boosGroup, workGroup)
                           // 服务端使用什么通道？ 此处设置使用 NioServerSocketChannel 工作为服务器，用来反射创建
                           .channel(NioServerSocketChannel.class)
                           // 对于服务端的pipLine添加处理器
                           //.handler()
                           // ServerInitializer 用来添加已经连接上来的客户端的处理器,netty启动完成后会删除掉该处理器对象
                           .childHandler(new ServerInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(8989).sync();
            // 关闭服务端，通过sync方法阻塞关闭操作，使其进入死循环;如果没有sync方法，netty服务会直接关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

}

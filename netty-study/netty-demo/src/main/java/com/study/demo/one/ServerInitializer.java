package com.study.demo.one;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * ServerInitializer 特殊的handler：
 *   用来添加其余的 处理客户端请求数据的handler,netty初始化时会执行initChannel方法
 */
public class ServerInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // HttpServerCodec 处理http协议的处理器
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        pipeline.addLast("serverHandler", new ServerHandler());
    }
}

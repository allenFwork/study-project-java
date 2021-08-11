package com.study.netty.firstdemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * 自定义的处理器 handler
 *   继承于 SimpleChannelInboundHandler, 表示只处理入栈的请求数据
 */
public class ServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
        if (httpObject instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) httpObject;
            String uri = httpRequest.uri();
            System.out.println(uri);
            ByteBuf byteBuf = Unpooled.copiedBuffer("helloWorld", CharsetUtil.UTF_8);
            FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            fullHttpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());
            channelHandlerContext.writeAndFlush(fullHttpResponse);
        }
    }
}

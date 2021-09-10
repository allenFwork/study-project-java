package com.study.http_demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;

import java.util.List;

/**
 * 1、初始化Bootstrap （连接）
 * 2、初始化pipeline(编解码)
 * 3、业务处理
 */
public class HttpServer {

    public void openServer(int port) {
        EventLoopGroup boot = new NioEventLoopGroup(1);
        EventLoopGroup work = new NioEventLoopGroup(8);

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.group(boot, work)
                       .childHandler(new ChannelInitializer() {
                            @Override
                            protected void initChannel(Channel channel) throws Exception {
                                // 1 解码request，HttpRequestDecoder解码器
                                channel.pipeline().addLast("http-decode", new HttpRequestDecoder());

                                // 添加能够将HttpRequest和HttpContent聚合在一次的处理器，限制RequestBody的最大大小为65536
                                channel.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));

                                // 3 编码response，HttpResponseEncoder编码器
                                channel.pipeline().addLast("http-encode", new HttpResponseEncoder());
                                // 2 业务处理
                                channel.pipeline().addLast("http-server-handler", new HttpServerHandler());
                            }
                       });
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            System.out.println("服务启动成功, 端口号为：" + port);
            // 等待管道关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("优雅的关闭线程池");
            boot.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

    // 创建了一个业务处理器 内部类
    private static class HttpServerHandler extends SimpleChannelInboundHandler {

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {

            /*----------------- 获取经过HttpRequestDecoder解码器处理得到的request对象（开始） -----------------*/
            if (msg instanceof HttpContent) {
                HttpPostRequestDecoder decoder = new HttpPostRequestDecoder((HttpRequest) msg);
                decoder.offer((HttpContent) msg);
                List<InterfaceHttpData> list = decoder.getBodyHttpDatas();
            }
            // 判断是不是请求的最后一个数据包，请求时发送的requestBody数据过大，分包发送
            if (msg instanceof LastHttpContent) {

            }
            /*----------------- 获取经过HttpRequestDecoder解码器处理得到的request对象（结束） -----------------*/

            /*--------------------------------------- 业务处理（开始） ---------------------------------------*/
            /*--------------------------------------- 业务处理（结束） ---------------------------------------*/

            /*-------------------------------- 封装返回的response对象（开始） --------------------------------*/
            // 创建返回的response: FullHttpResponse类型对象
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
            // 请求头
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=utf-8");
            // 请求体
            String src = "<!DOCTYPE html>\n" +
                         "<html lang=\"en\">\n" +
                            "<head>\n" +
                            "    <meta charset=\"UTF-8\">\n" +
                            "    <title>hello word</title>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "    hello word\n" +
                            "</body>\n" +
                         "</html>";
            response.content().writeBytes(src.getBytes("UTF-8"));
            /*-------------------------------- 封装返回的response对象（结束） --------------------------------*/

            // 发送数据给客户端
            ChannelFuture channelFuture = channelHandlerContext.writeAndFlush(response);
//            ChannelFuture channelFuture = channelHandlerContext.channel().writeAndFlush(response);
            // 关闭
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.openServer(8080);
    }
}

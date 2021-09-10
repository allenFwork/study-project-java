package com.study.exercise.netty.rpc.v1;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

    private Channel channel;

    /*
     * 本地接口的代理类调用本地方法时需要获取RpcResponse对象，即远程接口返回的数据信息，
     * 该数据信息通过SocketChannel对象的pipeline对象的handler处理获取,
     * 代理对象与其没有任何联系无法获取，所以只能通过中间类 NettyClient 来获取 RcpResponse对象
     *
     * 在 NettyClient 中创建一个全局的单例对象：ConcurrentHashMap 对象
     * ConcurrentHashMap 该对象，是一个map，代理类调用本地方法时，put一对键值对,
     * key为生成的唯一标识id，value为 SynchronousQueue<RpcResponse>对象。
     * 接受远程方法返回的数据时，会通过返回数据中对应的id，找到 ConcurrentHashMap 对象中的 SynchronousQueue<RpcResponse>对象，
     * 在handler中获取RcpResponse对象将其放到SynchronousQueue队列中
     */
    private static ConcurrentHashMap<String, SynchronousQueue<RpcResponse>> mapInfo = new ConcurrentHashMap<>();

    public static void putSynchronousQueue(String id, SynchronousQueue<RpcResponse> queue) {
        mapInfo.put(id, queue);
    }

    public static SynchronousQueue<RpcResponse> getSynchronousQueue(String id) {
        return mapInfo.get(id);
    }

    public static void removeById(String id) {
        mapInfo.remove(id);
    }

    public void sendRpcRequest(RpcRequest rpcRequest) throws Exception {
        try {
            this.channel.writeAndFlush(rpcRequest).sync();
        } catch (Exception e) {
            throw e;
        }
    }

    public void init(String host, int port, final Serializer serializer) throws Exception {
        EventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline()
                                // 添加自定义的编码器
                                .addLast(new RpcEncode(RpcRequest.class, serializer))
                                // 添加自定义的解码器
                                .addLast(new RpcDecode(RpcResponse.class, serializer))
                                // 添加 进行业务处理的 处理器
                                .addLast(new NettyClientHandler());
                    }
                })
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_KEEPALIVE, true);
        this.channel = bootstrap.connect(host, port).sync().channel();
        System.out.println("接口服务端连接成功......");
    }

    public boolean isValidate() {
        if (this.channel != null) {
            return this.channel.isActive();
        }
        return false;
    }

    public void close() {
        if (this.channel != null) {
            if (this.channel.isOpen()) {
                this.channel.close();
            }
        }
    }
}

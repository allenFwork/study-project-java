package com.study.netty.seconddemo;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class ServerInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        /*
         * 1. LengthFieldOffset 长度字段的偏差
         * 2. LengthFieldLength 长度字段占的字节数
         * 3. lengthAdjustment  添加到长度字段的补偿值
         * 4. initialBytesToStrip 从解码桢中第一次去除的字节数
         */
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
        // 计算当前带发送消息的二进制字节长度，将该长度添加到ByteBuf的缓冲区头中
        pipeline.addLast(new LengthFieldPrepender(4));
        // StringDecoder extends MessageToMessageDecoder
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        // StringEncoder extends MessageToMessageEncoder
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new ServerHandler());
    }
}

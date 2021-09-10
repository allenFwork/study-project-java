package com.study.exercise.netty.rpc.v1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


/**
 * 自定义的编码器：
 * 1. 实现对象的序列化 -》 对象转为二进制
 * 2. 对请求的数据进行加工，即编码
 */
public class RpcEncode extends MessageToByteEncoder<Object> {

    private Class<?> genericClass;
    private Serializer serializer;

    public RpcEncode(Class<?> genericClass, Serializer serializer) {
        this.genericClass = genericClass;
        this.serializer = serializer;
    }

    /**
     * 对请求报文进行编码
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object in, ByteBuf out) throws Exception {
        // 判断 传入的对象in 是不是  genericClass类型的，解码时是反序列化为genericClass类型的
        if (genericClass.isInstance(in)) {
            // 1. 对象序列化
            byte[] data = serializer.serialize(in);
            // 2. 写入 传输报文实体的二进制长度
            out.writeInt(data.length);
            // 3. 写入 报文实体的二进制数据
            out.writeBytes(data);
        }
    }
}
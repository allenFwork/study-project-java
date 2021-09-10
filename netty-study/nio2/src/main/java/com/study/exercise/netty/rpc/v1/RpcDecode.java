package com.study.exercise.netty.rpc.v1;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 自定义解码器：
 * 1. 解码
 * 2. 反序列化
 */
public class RpcDecode extends ByteToMessageDecoder {

	private Class<?> genericClass;
	private Serializer serializer;

	public RpcDecode(Class<?> genericClass, Serializer serializer) {
		this.genericClass = genericClass;
		this.serializer = serializer;
	}

	/**
	 * 解码
	 *
	 */
	@Override
	public final void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {

		/*------------------------------------------ 解码（开始） ------------------------------------------*/
		// 如果传输过来的保重字节长度小于4，那么该包是有问题的，直接返回
		if (in.readableBytes() < 4) {
			return;
		}
		// 记录此时的读指针的位置
		in.markReaderIndex();
		// 读取一个int类型的值，即读取4个字节的数据，根据协议该数据表示请求报文实体的字节长度
		int dataLength = in.readInt();
		// 请求报文实体为空
		if (dataLength < 0) {
			channelHandlerContext.close();
		}
		// 缓存的空间中可读的字节长度 小于 请求报文实体的字节长度
		if (in.readableBytes() < dataLength) {
			// 读指针回到标记位置
			in.resetReaderIndex();
			// 返回，不进行decode编码操作，等待更多的数据进入到缓存区，在进行编码
			return;
		}
		byte[] data = new byte[dataLength];
		// 此时读指针已经指向了请求报文数据开头的位置
		in.readBytes(data);
		/*------------------------------------------ 解码（结束） ------------------------------------------*/

		// 反序列化为对象
		Object obj = serializer.deserialize(data, genericClass);
		// 将对象传给下一个handler
		out.add(obj);
	}
}
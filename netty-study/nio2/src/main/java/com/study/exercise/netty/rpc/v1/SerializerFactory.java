package com.study.exercise.netty.rpc.v1;

import com.study.exercise.netty.rpc.v1.serialize.ProtobufSerializer;

/**
 * 获取序列化策略实例的工厂
 */
public class SerializerFactory {
	
	public static Serializer getSerializer() {
		return new ProtobufSerializer();
	}

}

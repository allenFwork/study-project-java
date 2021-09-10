package com.study.exercise.netty.rpc.v1;

/**
 * 序列化接口
 */
public interface Serializer {

    // 将字节数组（字节流）转化为 clazz 类型的对象
    public <T> Object deserialize(byte[] bytes, Class<T> clazz);

    // 将对象转化为字节数组（字节流）
    public <T> byte[] serialize(T obj);

}

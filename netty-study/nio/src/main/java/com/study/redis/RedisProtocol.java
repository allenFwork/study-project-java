package com.study.redis;

/**
 * redis的协议
 */
public class RedisProtocol {

    public static final String star = "*";
    public static final String crlf = "\r\n";
    public static final String lengthStart = "$";

    // redis的枚举
    public static enum command {
        SET, GET, INCR
    }

}

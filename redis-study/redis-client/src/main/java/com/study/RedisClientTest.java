package com.study;

import redis.clients.jedis.Jedis;

public class RedisClientTest {

    public static void main(String[] args) {

        /*----------------------------- jedis的简单使用（开始） ----------------------------*/
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.set("superman", "can fly");
        String value = jedis.get("superman");
        System.out.println(value);
        /*----------------------------- jedis的简单使用（结束） ----------------------------*/

    }

}

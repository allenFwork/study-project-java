package com.study.redis;

import redis.clients.jedis.Jedis;

public class RedisClient {

    private RedisSocket redisSocket;

    public RedisClient(String ip, int port) {
        this.redisSocket = new RedisSocket(ip, port);
    }

    public String set(String key, String value) {
        redisSocket.send(commandStrUtil(RedisProtocol.command.SET, key.getBytes(), value.getBytes()));
        return redisSocket.read();
    }

    public String get(String key) {
        redisSocket.send(commandStrUtil(RedisProtocol.command.GET, key.getBytes()));
        return redisSocket.read();
    }

    public String incr(String key) {
        redisSocket.send(commandStrUtil(RedisProtocol.command.INCR, key.getBytes()));
        return redisSocket.read();
    }

    public void close() {
        redisSocket.close();
    }

    /**
     * 下面的命令在redis的aop文件中保存的数据是：
     * *3      ：表示接下来有三组命令，$3 SET ; $6 taibai; $6 123456 三组
     * $3      ：$3表示3个字符长度
     * SET
     * $6
     * taibai
     * $6
     * 123456
     * <p>
     * * 表示有几组命令
     * $ 表示命令的字符长度
     */
    public String commandStrUtil(RedisProtocol.command command, byte[]... bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(RedisProtocol.star).append(1 + bytes.length).append(RedisProtocol.crlf);
        stringBuilder.append(RedisProtocol.lengthStart).append(command.toString().getBytes().length).append(RedisProtocol.crlf);
        stringBuilder.append(command.toString()).append(RedisProtocol.crlf);
        for (byte[] aByte : bytes) {
            stringBuilder.append(RedisProtocol.lengthStart).append(aByte.length).append(RedisProtocol.crlf);
            stringBuilder.append(new String(aByte)).append(RedisProtocol.crlf);
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {

        // 使用Jedis客户端
        Jedis jedis = new Jedis("127.0.0.1", 9999);
        System.out.println(jedis.set("taibai", "123456"));
        System.out.println(jedis.get("taibai"));
        System.out.println(jedis.incr("lock"));
        jedis.close();

        // 使用自定义的redis客户端
        RedisClient redisClient = new RedisClient("192.168.204.188", 6379);
        System.out.println(redisClient.set("taibai", "123456"));
        System.out.println(redisClient.get("taibai"));
        System.out.println(redisClient.incr("lock"));
        redisClient.close();

    }

}

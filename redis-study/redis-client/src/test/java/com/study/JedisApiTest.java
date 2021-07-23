package com.study;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * redis客户端 Jedis的相关API操作
 */
public class JedisApiTest {

    private static Jedis jedis;
    private static final String KEY = "20181212";
    private static final String VALUE = "superman";


    @Before
    public void setUp() throws Exception {
        jedis = new Jedis("localhost", 6379);
    }

    @Test
    public void testKey() throws InterruptedException {
        System.out.println("清除数据：" + jedis.flushDB());
        System.out.println("判断某个键是否存在：" + jedis.exists("username"));
        System.out.println("新增<'username','zzh':" + jedis.set("username", "zzh") );
        Set<String> keys = jedis.keys("*");
        System.out.println("系统中所有的建，如下：" + keys);
//        System.out.println("删除键 username：" + jedis.del("username"));
        System.out.println("查看键username所存储的值的类型：" + jedis.type("username"));
    }

    /**
     * 剩余时间：单位秒
     */
    @Test
    public void testTtl() {
        // 给 key 为 KEY 的数据设置过期时间为100秒
        jedis.expire(KEY, 100);
        // 获取过期时间：秒
        long time = jedis.ttl(KEY);
        System.out.println("倒计时（ttl）的时间为：" + time);
        // -1 表示
    }
    /**
     * 剩余时间：毫秒
     * 与上一个的区别：单位不一样
     */
    @Test
    public void testPttl() {
        // 给 key 为 KEY 的数据设置过期时间为100秒
        jedis.expire(KEY, 100);
        // 获取过期时间：毫秒
        long time = jedis.pttl(KEY);
        System.out.println("倒计时（pttl）的时间为：" + time);
    }

    @Test
    public void testApi() {

        // 判断数据是否存在
        boolean flag = jedis.exists(KEY);
        System.out.println("是否存在：" + flag);

        // 删除对应key的键值对数据
        jedis.del(KEY);
        System.out.println("删除数据：");

        // 键值对
        jedis.mset(new String[]{"name", "Li Si", "age", "18", "QQ", "2324324324"});
        System.out.printf("姓名：%s, 年龄：%s, 联系方式：%s,", jedis.get("name"), jedis.get("age"), jedis.get("QQ"));

        // 原子操作
        jedis.incr("age");
        System.out.println(jedis.get("age"));
    }

    /**
     * 测试对String（字符串）的操作
     */
    @Test
    public void testString() {
        jedis.flushDB();
        System.out.println(jedis.set("key1", "value1"));
        System.out.println("在key1后面加入值：" + jedis.append("key1", "End"));
        System.out.println("key1的值：" + jedis.get("key1"));
        System.out.println("增加多个键值对：" + jedis.mset("key01", "value01", "key02", "value02", "key03", "value03"));
        System.out.println("获取多个键值对：" + jedis.mget("key01", "key02", "key03"));
        System.out.println("获取多个键值对：" + jedis.mget("key01", "key02", "key03", "key04"));
        System.out.println("删除多个键值对：" + jedis.del(new String[]{"key01", "key02"}));
        System.out.println("获取多个键值对：" + jedis.mget("key01", "key02", "key03"));
        jedis.flushDB();
        System.out.println("========================新增加键值对防止覆盖原先值=======================");
        System.out.println(jedis.setnx("key1", "value1"));
        System.out.println(jedis.setnx("key2", "value2"));
        System.out.println(jedis.setnx("key2", "value2-new"));
        System.out.println(jedis.get("key1"));
        System.out.println(jedis.get("key2"));
        System.out.println("=========================获取原值，更新为新值==============================");
        System.out.println(jedis.getSet("key2", "key2GetSet"));
        System.out.println(jedis.get("key2"));
    }


    /**
     * 测试对 list（列表）的操作
     */
    @Test
    public void testList() {
        jedis.flushDB();
        System.out.println("=============================添加一个list===================================");
        jedis.lpush("collection", "superman", "batman", "the flash", "the iron man");
        System.out.println("collection的内容为：" + jedis.lrange("collection", 0, -1));
        System.out.println("collection的长度为：" + jedis.llen("collection"));
        jedis.lpush("sortedList排序后：", "3", "6", "2", "0", "7", "4");
        System.out.println(jedis.sort("sortedList排序后"));
        System.out.println("sortedList排序后：" + jedis.lrange("sortedList排序后", 0, -1));

    }


    @Test
    public void testSet() {

    }

}

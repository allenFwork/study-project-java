package com.study.redis.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.redis.client.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;

@SpringBootTest
public class SpringDataRedisApplicationTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void stringTest() {
        // 写入一条string数据
        redisTemplate.opsForValue().set("name", "batman");
        // 获取string数据
        Object name = redisTemplate.opsForValue().get("name");
        System.out.println(name);
    }

    @Test
    void testSaveUser() {
        redisTemplate.opsForValue().set("user", new User("张三", 22));
        User user = (User) redisTemplate.opsForValue().get("user");
        System.out.println(user);
    }


    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    // JSON序列化工具
    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testSaveUser2() throws JsonProcessingException {
        // 创建对象
        User user = new User("虎哥", 21);
        // 手动序列化
        String json = mapper.writeValueAsString(user);
        // 写入数据
        stringRedisTemplate.opsForValue().set("user:200", json);

        // 获取数据
        String jsonUser = stringRedisTemplate.opsForValue().get("user:200");
        // 手动反序列化
        User user1 = mapper.readValue(jsonUser, User.class);
        System.out.println("user = " + user1);
    }

    @Test
    void testHash() {
        stringRedisTemplate.opsForHash().put("user:400", "name", "超人");
        stringRedisTemplate.opsForHash().put("user:400", "age", "20");

        String name = (String) stringRedisTemplate.opsForHash().get("user:400", "name");
        System.out.println(name);
        String age = (String) stringRedisTemplate.opsForHash().get("user:400", "age");
        System.out.println(age);

        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries("user:400");
        System.out.println(entries);
    }

}

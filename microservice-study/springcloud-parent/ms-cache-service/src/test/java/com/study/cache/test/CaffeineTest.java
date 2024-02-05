package com.study.cache.test;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.junit.jupiter.api.Test;

import java.time.Duration;

public class CaffeineTest {

    // Caffeine库的简单使用
    @Test
    void testBasicOps() {
        // 构建cache对象
        Cache<String, String> cache = Caffeine.newBuilder().build();

        // 存数据
        cache.put("gf", "迪丽热巴");

        // 取数据
        String gf = cache.getIfPresent("gf");
        System.out.println("gf = " + gf);

        // 取数据，包含两个参数：
        // 参数一：缓存的key
        // 参数二：Lambda表达式，表达式参数就是缓存的key，方法体是查询数据库的逻辑
        // 优先根据key查询JVM缓存，如果未命中，则执行参数二的Lambda表达式
        String defaultGF = cache.get("defaultGF", key -> {
            // 根据key去数据库查询数据
            return "柳岩";
        });
        System.out.println("defaultGF = " + defaultGF);
    }

    /*
        基于大小策略
     */
    @Test
    void testEvictByNum() throws InterruptedException {
        // 创建缓存对象
        Cache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(1) // 设置缓存大小上限为 1
                .build();

        // 存数据
        cache.put("girlFriend1", "迪丽热巴");
        cache.put("girlFriend2", "范冰冰");
        cache.put("girlFriend3", "柳岩");

        // 延迟10ms，给清理线程一点时间
        Thread.sleep(10L);

        // 获取数据
        System.out.println(cache.getIfPresent("girlFriend1"));
        System.out.println(cache.getIfPresent("girlFriend2"));
        System.out.println(cache.getIfPresent("girlFriend3"));
    }

    /*
        基于时间设置驱逐策略
     */
    @Test
    void testEvictByTime() throws InterruptedException {
        // 创建缓存对象
        Cache<String, String> cache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(1)) // 设置缓存有效期1秒
                .build();

        // 存数据
        cache.put("girlFriend", "柳岩");

        // 获取数据
        System.out.println(cache.getIfPresent("girlFriend"));
        // 休眠1.2秒
        Thread.sleep(1200L);
        System.out.println(cache.getIfPresent("girlFriend"));
    }

}

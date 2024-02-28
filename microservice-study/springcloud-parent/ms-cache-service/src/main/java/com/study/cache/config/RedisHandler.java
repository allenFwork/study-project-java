package com.study.cache.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.cache.pojo.Item;
import com.study.cache.pojo.ItemStock;
import com.study.cache.service.IItemService;
import com.study.cache.service.IItemStockService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

// 编写Redis初始化类，实现InitializingBean接口，必须重写afterPropertiesSet方法
@Component
public class RedisHandler implements InitializingBean {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IItemService itemService;
    @Autowired
    private IItemStockService stockService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 初始化缓存，数据预热：在 RedisHandler 对象创建完成，并完成对象注入后立刻执行（即项目启动后就会执行）
    @Override
    public void afterPropertiesSet() throws Exception {
        // Redis初始化缓存
        // 1.查询商品信息（此处是数据库中数据全部查询出来，实际使用大数据分析选择部分数据）
        List<Item> itemList = itemService.list();
        // 2.放入缓存
        for (Item item : itemList) {
            // 2.1.item序列化为JSON
            String json = MAPPER.writeValueAsString(item);
            // 2.2.存入redis
            stringRedisTemplate.opsForValue().set("item:id:" + item.getId(), json);
        }

        // 3.查询商品库存信息
        List<ItemStock> stockList = stockService.list();
        // 4.放入缓存
        for (ItemStock stock : stockList) {
            // 2.1.item序列化为JSON
            String json = MAPPER.writeValueAsString(stock);
            // 2.2.存入redis
            stringRedisTemplate.opsForValue().set("item:stock:id:" + stock.getId(), json);
        }
    }

    // 封装保存方法
    public void saveItem(Item item) {
        try {
            String json = MAPPER.writeValueAsString(item);
            stringRedisTemplate.opsForValue().set("item:id:" + item.getId(), json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // 封装删除方法
    public void deleteItemById(Long id) {
        stringRedisTemplate.delete("item:id:" + id);
    }
}

package com.study.redis.apply.service;

import com.study.redis.apply.RedisApplyApplicationTest;
import com.study.redis.apply.service.impl.ShopServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ShopServiceImplTest extends RedisApplyApplicationTest {

    @Autowired
    private ShopServiceImpl shopService;

    // 热点数据缓存初始化
    @Test
    void hotKeyShopSaveTest() {
        shopService.saveShop2Redis(1L, 10L);
    }

}

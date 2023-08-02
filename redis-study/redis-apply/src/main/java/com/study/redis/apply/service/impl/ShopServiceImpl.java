package com.study.redis.apply.service.impl;

import com.study.redis.apply.dto.Result;
import com.study.redis.apply.entity.Shop;
import com.study.redis.apply.mapper.ShopMapper;
import com.study.redis.apply.service.IShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 *  商店服务实现类
 *
 * @author allen
 * @since 2022-8-2
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {

    @Override
    public Result queryById() {
        return null;
    }

}

package com.study.redis.apply.service;

import com.study.redis.apply.dto.Result;
import com.study.redis.apply.entity.Shop;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 服务类
 * @author allen
 * @since 2021-12-22
 */
public interface IShopService extends IService<Shop> {

    Result queryById(Long id);

    Result updateShopById(Shop shop);

}

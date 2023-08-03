package com.study.redis.apply.service;

import com.study.redis.apply.dto.Result;
import com.study.redis.apply.entity.ShopType;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author allen
 * @since 2023-08-02
 */
public interface IShopTypeService extends IService<ShopType> {

    Result queryList();

}

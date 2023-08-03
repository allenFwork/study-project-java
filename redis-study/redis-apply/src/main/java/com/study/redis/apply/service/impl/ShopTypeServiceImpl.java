package com.study.redis.apply.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.redis.apply.dto.Result;
import com.study.redis.apply.entity.ShopType;
import com.study.redis.apply.mapper.ShopTypeMapper;
import com.study.redis.apply.service.IShopTypeService;
import com.study.redis.apply.utils.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商店类型服务实现类
 *
 * @author allen
 * @since 2023-8-2
 */
@Service
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements IShopTypeService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result queryList() {
        // 1.查询redis缓存
        String shoTypeListStr = stringRedisTemplate.opsForValue().get(RedisConstants.CACHE_SHOP_TYPE_KEY + "list");
        if (StrUtil.isNotBlank(shoTypeListStr)) {
            // hutool 工具的 toBean方法只能转换一个对象，不能转换集合对象
            return Result.ok(JSONUtil.toList(shoTypeListStr, ShopType.class));
        }
        // 2.数据库查询商店类别
        List<ShopType> shopTypeList = query().orderByAsc("sort").list();
        if (shopTypeList == null) {
            return Result.ok("商品类别为空！");
        }
        // 3.写入redis
        stringRedisTemplate.opsForValue().set(RedisConstants.CACHE_SHOP_TYPE_KEY + "list", JSONUtil.toJsonStr(shopTypeList));
        // 4.返回
        return Result.ok(shopTypeList);
    }
}

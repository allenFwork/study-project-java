package com.study.redis.apply.service.impl;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.redis.apply.dto.Result;
import com.study.redis.apply.entity.Shop;
import com.study.redis.apply.mapper.ShopMapper;
import com.study.redis.apply.service.IShopService;
import com.study.redis.apply.utils.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static com.study.redis.apply.utils.RedisConstants.CACHE_SHOP_TTL;

/**
 * 商店服务实现类
 *
 * @author allen
 * @since 2022-8-2
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result queryById(Long id) {
        // 原始逻辑处理
        // Shop shop = queryWithPassThrough(id);

        // 为了解决缓存穿透问题
        Shop shop = queryWithWithMutex(id);
        if (shop == null)
            return Result.fail("店铺不存在！");

        return Result.ok(shop);
    }

    // 原始逻辑
    public Shop queryWithPassThrough(Long id) {
        // 1.从redis查询商铺缓存
        String shopStr = stringRedisTemplate.opsForValue().get(RedisConstants.CACHE_SHOP_KEY + id);
        // 2.判断是否存在
        if (StrUtil.isNotBlank(shopStr)) { // 只有shopStr有具体的字符串数据时，才会进入下面
            // 3.存在，直接返回
            return JSONUtil.toBean(shopStr, Shop.class);
        }
        // 判断命中的是否是空值，shopStr为 null 或者 ""（空字符串） 或者 "/t/n" 时，才会执行到这里
        if (shopStr != null) { // 不为null，就一定是空字符串了，因为不会向其中存放 “/t/n” 的
            // 返回错误信息
            return null;
        }

        // 4.不存在，根据id查询数据库
        Shop shop = getById(id);
        // 5.不存在，返回错误
        if (shop == null) {
            // 为了解决缓存穿透问题，所以将数据库查询为空的数据也存储到redis中，存储为空值(设置过期时间不宜太长，2分钟比较合适)
            stringRedisTemplate.opsForValue().set(RedisConstants.CACHE_SHOP_KEY + id, "", RedisConstants.CACHE_NULL_TTL, TimeUnit.MINUTES);
            // 返回错误信息
            return null;
        }
        // 6.存在，写入redis(为了保证数据的一致性，添加超时时间)
        stringRedisTemplate.opsForValue().set(RedisConstants.CACHE_SHOP_KEY + id, JSONUtil.toJsonStr(shop), CACHE_SHOP_TTL, TimeUnit.MINUTES);
        // 7.返回
        return shop;
    }

    // 使用互斥锁，解决缓存击穿问题
    public Shop queryWithWithMutex(Long id) {
        // 1.从redis查询商铺缓存
        String shopStr = stringRedisTemplate.opsForValue().get(RedisConstants.CACHE_SHOP_KEY + id);
        // 2.判断是否存在
        if (StrUtil.isNotBlank(shopStr)) { // 只有shopStr有具体的字符串数据时，才会进入下面
            // 3.存在，直接返回
            return JSONUtil.toBean(shopStr, Shop.class);
        }
        // 判断命中的是否是空值，shopStr为 null 或者 ""（空字符串） 或者 "/t/n" 时，才会执行到这里
        if (shopStr != null) { // 不为null，就一定是空字符串了，因为不会向其中存放 “/t/n” 的
            // 返回错误信息
            return null;
        }
        // 4.实现缓存重建
        // 4.1 获取互斥锁
        String lockKey = "lock:shop:" + id;
        Shop shop = null;
        try {
            boolean flag = new ExclusiveLock().tryLock(lockKey);
            // 4.2 判断是否获取成功
            if (!flag) {
                // 4.3 获取失败，则休眠并重试
                Thread.sleep(50);
                return queryWithPassThrough(id);
            }
            // 4.4 获取锁成功，根据id查询数据库
            shop = getById(id);
            // 模拟耗费时间,等待两秒
            Thread.sleep(2);
            // 5.不存在，返回错误
            if (shop == null) {
                // 为了解决缓存穿透问题，所以将数据库查询为空的数据也存储到redis中，存储为空值(设置过期时间不宜太长，2分钟比较合适)
                stringRedisTemplate.opsForValue().set(RedisConstants.CACHE_SHOP_KEY + id, "", RedisConstants.CACHE_NULL_TTL, TimeUnit.MINUTES);
                // 返回错误信息
                return null;
            }
            // 6.存在，写入redis(为了保证数据的一致性，添加超时时间)
            stringRedisTemplate.opsForValue().set(RedisConstants.CACHE_SHOP_KEY + id, JSONUtil.toJsonStr(shop), CACHE_SHOP_TTL, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 7.释放互斥锁
            new ExclusiveLock().releaseLock("shopKey");
        }
        // 8.返回
        return shop;
    }

    /**
     * 更新商铺信息
     */
    @Override
    @Transactional // 下面的操作是一个整体，不可分割，所以放在一个事务中
    public Result updateShopById(Shop shop) {
        Long id = shop.getId();
        if (id == null) {
            return Result.fail("店铺id不能为空");
        }
        // 1.更新商铺信息
        updateById(shop);
        // 2.删除redis的缓存（保证缓存与数据库的数据一致性）
        stringRedisTemplate.delete(RedisConstants.CACHE_SHOP_KEY + shop.getId());

        return Result.ok();
    }

    /**
     * 获取互斥锁和释放互斥锁方案：
     * 可以通过 redis的 “ setnx lock 1” 命令，实现获取互斥锁，返回1表示成功获取了锁，返回0表示获取锁失败
     * 通过 del lock 实现释放锁，实质就是redis内存中删除了 key 为 lock 的键值
     */
    class ExclusiveLock {

        /**
         * 获取锁
         */
        public boolean tryLock(String key) {
            // setIfAbsent方法使用的就是 setnx 命令
            Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", 10, TimeUnit.SECONDS);
            // 理论上 setIfAbsent 返回只有 true 和 false，但有可能出现问题会返回 null，出现空指针问题
            return BooleanUtil.isTrue(flag);
        }

        /**
         * 释放锁
         */
        public void releaseLock(String key) {
            stringRedisTemplate.delete("lock");
        }

    }

}

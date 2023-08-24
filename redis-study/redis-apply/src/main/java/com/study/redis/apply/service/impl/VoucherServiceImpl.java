package com.study.redis.apply.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.redis.apply.dto.Result;
import com.study.redis.apply.entity.SeckillVoucher;
import com.study.redis.apply.entity.Voucher;
import com.study.redis.apply.mapper.VoucherMapper;
import com.study.redis.apply.service.ISecKillVoucherService;
import com.study.redis.apply.service.IVoucherService;
import com.study.redis.apply.utils.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
public class VoucherServiceImpl extends ServiceImpl<VoucherMapper, Voucher> implements IVoucherService {

    @Resource
    private ISecKillVoucherService secKillVoucherService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result queryVoucherOfShop(Long shopId) {
        // 查询优惠券信息
        List<Voucher> vouchers = getBaseMapper().queryVoucherOfShop(shopId);
        // 返回结果
        return Result.ok(vouchers);
    }

    @Override
    @Transactional
    public void addSeckillVoucher(Voucher voucher) {
        // 保存优惠券
        save(voucher);
        // 保存秒杀信息
        SeckillVoucher seckillVoucher = new SeckillVoucher();
        seckillVoucher.setVoucherId(voucher.getId());
        seckillVoucher.setStock(voucher.getStock());
        seckillVoucher.setBeginTime(voucher.getBeginTime());
        seckillVoucher.setEndTime(voucher.getEndTime());
        secKillVoucherService.save(seckillVoucher);
        // 保存秒杀活动对应的优惠券信息到Redis中
        stringRedisTemplate.opsForValue().set(RedisConstants.SECKILL_STOCK_KEY + voucher.getId(), voucher.getStock().toString());
    }
}

package com.study.redis.apply.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.redis.apply.dto.Result;
import com.study.redis.apply.entity.VoucherOrder;

public interface IVoucherOrderService extends IService<VoucherOrder> {

    // 传入参数为优惠券的id
    public Result secKillVoucher(Long voucherId);

    public Result createVoucherOrder(Long voucherId);

    public void createVoucherOrder(VoucherOrder voucherOrder);

}

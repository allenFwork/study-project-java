package com.study.redis.apply.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.redis.apply.dto.Result;
import com.study.redis.apply.entity.SeckillVoucher;
import com.study.redis.apply.entity.VoucherOrder;
import com.study.redis.apply.mapper.VoucherOrderMapper;
import com.study.redis.apply.service.ISecKillVoucherService;
import com.study.redis.apply.service.IVoucherOrderService;
import com.study.redis.apply.utils.RedisIdWorker;
import com.study.redis.apply.utils.SimpleRedisLock;
import com.study.redis.apply.utils.UserHolder;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class VoucherOrderServiceImpl extends ServiceImpl<VoucherOrderMapper, VoucherOrder> implements IVoucherOrderService {

    @Autowired
    private ISecKillVoucherService secKillVoucherService; // 秒杀券对应的服务类

    @Autowired
    private RedisIdWorker redisIdWorker;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 最初的方法(原版本)
    @Transactional
    public Result secKillVoucher0(Long voucherId) {
        // 1.查询优惠券信息
        SeckillVoucher voucher = secKillVoucherService.getById(voucherId);
        // 2.判断优惠券的秒杀活动是否开始
        LocalDateTime beginTime = voucher.getBeginTime();
        if (beginTime.isAfter(LocalDateTime.now())) {
            // 尚未开始活动
            return Result.fail("秒杀尚未开始！");
        }
        // 3.判断优惠券的秒杀活动是否结束
        LocalDateTime endTime = voucher.getEndTime();
        if (endTime.isBefore(LocalDateTime.now())) {
            return Result.fail("秒杀已经结束！");
        }
        // 4.判断库存是否充足
        long stock = voucher.getStock();
        if (stock < 1) {
            // 库存不足
            return Result.fail("库存不足！");
        }

        // 5.一人一单
        long userId = UserHolder.getUser().getId();
        // 5.1 查询订单
        int count = query().eq("user_id", userId).eq("voucher_id", voucherId).count();
        // 5.2 判断是否存在
        if (count > 0) {
            // 用户已经购买过了
            return Result.fail("用户已经购买过了！");
        }

        // 6.扣减库存
//        boolean success = seckillVoucherService.update()
//                .setSql("stock = stock -1")
//                .eq("voucher_id", voucherId)
//                .eq("stock", voucher.getStock()) // 乐观锁思想，解决超卖问题
//                .update();
        boolean success = secKillVoucherService.update()
                .setSql("stock = stock -1")
                .eq("voucher_id", voucherId)
                .gt("stock", 0) // 乐观锁思想，解决超卖问题(方案二)
                .update();
        if (!success) {
            // 扣减失败
            return Result.fail("扣减失败！");
        }

        // 7.创建订单
        VoucherOrder voucherOrder = new VoucherOrder();
        // 7.1订单id
        long orderId = redisIdWorker.nextId("order");
        voucherOrder.setId(orderId);
        // 7.2 用户id
        voucherOrder.setUserId(userId);
        // 7.3 代金券id
        voucherOrder.setVoucherId(voucherId);
        save(voucherOrder);

        // 8.返回订单id
        return Result.ok(orderId);
    }

    // 版本2：解决了一人一单的单机问题（未解决分布式环境下的问题）
    public Result secKillVoucher2(Long voucherId) {
        // 1.查询优惠券信息
        SeckillVoucher voucher = secKillVoucherService.getById(voucherId);
        // 2.判断优惠券的秒杀活动是否开始
        LocalDateTime beginTime = voucher.getBeginTime();
        if (beginTime.isAfter(LocalDateTime.now())) {
            // 尚未开始活动
            return Result.fail("秒杀尚未开始！");
        }
        // 3.判断优惠券的秒杀活动是否结束
        LocalDateTime endTime = voucher.getEndTime();
        if (endTime.isBefore(LocalDateTime.now())) {
            return Result.fail("秒杀已经结束！");
        }
        // 4.判断库存是否充足
        long stock = voucher.getStock();
        if (stock < 1) {
            // 库存不足
            return Result.fail("库存不足！");
        }

        Long userId = UserHolder.getUser().getId();
        /**
         * 1.必须先添加锁，在使用事务，即锁把事务包起来了，否则会出现问题，例如：
         *   当线程1创建完该用户对应的一张优惠券后，此时 synchronized的锁结束了，其他线程也可以执行该逻辑了。
         *   但是此时线程1对应的事务还没有提交，那么数据库中该用户对应的数据就还是没有创建优惠券的情况，这时线程2可能会再次执行创建优惠券的逻辑。
         * 2.此处通过  synchronized (userId.toString().intern()) { 方法体 }，那么事务就被包括在该锁中
         *   事务如果没有提交，那么该用户对应的锁是不会释放掉的
         * 3.userId是Long类型的，它是包装类，即使对应了一个数值，也不能确定是一个对象，
         *   所以通过 userId.toString()转化为String对象，在通过String.intern()方法转化为同一个String对象。
         *   那么此时该用户都会拿到同一个String对象，通过synchronized获取该对象锁，只有一个线程能成功，其余等待
         */
        synchronized (userId.toString().intern()) {
            // 下面这样调用，等价于this.createVoucherOrder(voucherId); spring管理的事务会失效
            // return createVoucherOrder(voucherId);
            /**
             * 通过 org.springframework.aop.framework.AopContext 获取Spring管理的代理对象，其可以使用事务
             * 想要使用该类，必须添加依赖和启用注释
             * 1.        <dependency>
             *             <groupId>org.aspectj</groupId>
             *             <artifactId>aspectjweaver</artifactId>
             *           </dependency>
             * 2. @EnableAspectJAutoProxy(exposeProxy = true) // 暴露AspectJ的代理对象，默认是不暴露
             */
            IVoucherOrderService proxy = (IVoucherOrderService) AopContext.currentProxy();
            return proxy.createVoucherOrder(voucherId);
        }
    }

    // 版本3：分布式锁解决了一人一单的单机问题（自定义的redis分布式锁）
    public Result secKillVoucher3(Long voucherId) {
        // 1.查询优惠券信息
        SeckillVoucher voucher = secKillVoucherService.getById(voucherId);
        // 2.判断优惠券的秒杀活动是否开始
        LocalDateTime beginTime = voucher.getBeginTime();
        if (beginTime.isAfter(LocalDateTime.now())) {
            // 尚未开始活动
            return Result.fail("秒杀尚未开始！");
        }
        // 3.判断优惠券的秒杀活动是否结束
        LocalDateTime endTime = voucher.getEndTime();
        if (endTime.isBefore(LocalDateTime.now())) {
            return Result.fail("秒杀已经结束！");
        }
        // 4.判断库存是否充足
        long stock = voucher.getStock();
        if (stock < 1) {
            // 库存不足
            return Result.fail("库存不足！");
        }

        Long userId = UserHolder.getUser().getId();
        // 创建对象：通过 order:用户id 来设置一个用户一个锁
        SimpleRedisLock lock = new SimpleRedisLock("order:" + userId, stringRedisTemplate);
        // 获取锁
        boolean isLock = lock.tryLock(1200);
        if (!isLock) {
            // 获取锁失败，返回错误或重试
            return Result.fail("不允许重复下单");
        }
        try {
            // 获取代理对象（事务）
            IVoucherOrderService proxy = (IVoucherOrderService) AopContext.currentProxy();
            return proxy.createVoucherOrder(voucherId);
        } finally {
            // 释放锁
            lock.unLock();
        }

    }

    @Autowired
    private RedissonClient redissonClient;

    // 版本4：分布式锁解决了一人一单问题（使用Redisson的分布式锁）
    public Result secKillVoucher4(Long voucherId) {
        // 1.查询优惠券信息
        SeckillVoucher voucher = secKillVoucherService.getById(voucherId);
        // 2.判断优惠券的秒杀活动是否开始
        LocalDateTime beginTime = voucher.getBeginTime();
        if (beginTime.isAfter(LocalDateTime.now())) {
            // 尚未开始活动
            return Result.fail("秒杀尚未开始！");
        }
        // 3.判断优惠券的秒杀活动是否结束
        LocalDateTime endTime = voucher.getEndTime();
        if (endTime.isBefore(LocalDateTime.now())) {
            return Result.fail("秒杀已经结束！");
        }
        // 4.判断库存是否充足
        long stock = voucher.getStock();
        if (stock < 1) {
            // 库存不足
            return Result.fail("库存不足！");
        }

        Long userId = UserHolder.getUser().getId();
        // 创建锁对象：通过 order:用户id 来设置一个用户一个锁
        RLock rLock = redissonClient.getLock("order:" + userId);
        // 获取锁（肯能成功，可能失败）
        boolean isLock = rLock.tryLock();

        if (!isLock) {
            // 获取锁失败，返回错误或重试
            return Result.fail("不允许重复下单");
        }
        try {
            // 获取代理对象（事务）
            IVoucherOrderService proxy = (IVoucherOrderService) AopContext.currentProxy();
            return proxy.createVoucherOrder(voucherId);
        } finally {
            // 释放锁
            rLock.unlock();
        }

    }

    /**
     * 为了解决一人一单的问题，多线程查询时，一个人同时下了多单
     * 通过synchronized，悲观锁解决这种查询问题
     * 但是该关键字如果加在方法上面，串行执行，太影响性能了。不管是什么用户，走到这里都得串行执行。
     */
    @Transactional // 事务控制原子性
    public Result createVoucherOrder(Long voucherId) {
        // 5.一人一单
        Long userId = UserHolder.getUser().getId();

        // 5.1 查询订单
        int count = query().eq("user_id", userId).eq("voucher_id", voucherId).count();
        // 5.2 判断是否存在
        if (count > 0) {
            // 用户已经购买过了
            return Result.fail("用户已经购买过了！");
        }

        // 6.扣减库存
        boolean success = secKillVoucherService.update()
                .setSql("stock = stock -1")
                .eq("voucher_id", voucherId)
                .gt("stock", 0) // 乐观锁思想，解决超卖问题(方案二)
                .update();
        if (!success) {
            // 扣减失败
            return Result.fail("扣减失败！");
        }

        // 7.创建订单
        VoucherOrder voucherOrder = new VoucherOrder();
        // 7.1订单id
        long orderId = redisIdWorker.nextId("order");
        voucherOrder.setId(orderId);
        // 7.2 用户id
        voucherOrder.setUserId(userId);
        // 7.3 代金券id
        voucherOrder.setVoucherId(voucherId);
        save(voucherOrder);

        // 8.返回订单id
        return Result.ok(orderId);

    }

    /*--------------------------------------------------- 异步处理秒杀活动下单功能，提高性能 ---------------------------------------------------*/
    // 版本5：使用异步处理优化秒杀功能，提高性能
    @Override
    public Result secKillVoucher(Long voucherId) {
        Long userId = UserHolder.getUser().getId();
        Long orderId = redisIdWorker.nextId("order");
        // 1.执行Lua的脚本
        Long result = stringRedisTemplate.execute(SECKILL_SCRIPT,
                Collections.emptyList(),
                voucherId.toString(),
                userId.toString(),
                orderId.toString()
        );
        // 2.判断是否为0（lua脚本执行的结果为0时，表示可以下单）
        int r = result.intValue();
        if (r != 0) {
            // 2.1 不为0，代表没有购买资格
            return Result.fail(r == 1 ? "库存不足" : "不能重复下单");
        }
        // 2.2 为0有购买资格，把下单信息保存到阻塞队列
        // 2.2.1 创建订单
        VoucherOrder voucherOrder = new VoucherOrder();
        // 2.2.2 订单ids
        voucherOrder.setId(orderId);
        // 2.2.3 用户id
        voucherOrder.setUserId(userId);
        // 2.2.4 代金券id
        voucherOrder.setVoucherId(voucherId);
        // 2.2.5 放入阻塞队列中
        orderTasks.add(voucherOrder);

        // 3.获取代理对象（为了子任务执行时使用事务）
        proxy = (IVoucherOrderService) AopContext.currentProxy();

        // 4.返回订单
        return Result.ok(orderId);
    }

    // Lua的脚本文件
    private static final DefaultRedisScript<Long> SECKILL_SCRIPT;

    static {
        SECKILL_SCRIPT = new DefaultRedisScript<>();
        SECKILL_SCRIPT.setLocation(new ClassPathResource("secKill.lua"));
        SECKILL_SCRIPT.setResultType(Long.class);
    }

    // 需要消耗内存空间，所以设置了限定的大小
    private BlockingQueue<VoucherOrder> orderTasks = new ArrayBlockingQueue<>(1024 * 1024);
    // 创建一个单线程的线程池
    private static final ExecutorService SECKILL_EXECUTOR = Executors.newSingleThreadExecutor();

    @PostConstruct // 在类初始化的时候，开始执行线程池中的任务
    private void init() {
        SECKILL_EXECUTOR.submit(new VoucherOrderHandler());
    }

    // 用于线程池处理的任务
    // 当初始化完毕后，就会去从对列中去拿信息
    private class VoucherOrderHandler implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    // 1.获取队列中的订单信息
                    VoucherOrder voucherOrder = orderTasks.take();
                    // 2.处理订单
                    handleVoucherOrder(voucherOrder);
                } catch (Exception e) {
                    log.error("处理订单异常：", e);
                }
            }
        }
    }

    private IVoucherOrderService proxy;

    // 处理创建订单
    private void handleVoucherOrder(VoucherOrder voucherOrder) {
        // 1.获取用户id (不能通过UserHolder获取了，因为这是多线程任务，对应的ThreadLocal中没有用户信息)
        Long userId = voucherOrder.getUserId();
        // 2.创建锁对象
        RLock rLock = redissonClient.getLock("order:" + userId);
        // 3.获取锁（肯能成功，可能失败）
        boolean isLock = rLock.tryLock();
        // 4.判断是否获取锁
        if (!isLock) {
            // 获取锁失败，返回错误或重试
            log.error("不允许重复下单");
            return;
        }
        try {
            // 不能通过 AopContext.currentProxy() 获取代理对象，因为该方法底层是通过 ThreadLocal 获取，此处是多线程任务不成功
            // IVoucherOrderService proxy = (IVoucherOrderService) AopContext.currentProxy();
            proxy.createVoucherOrder(voucherOrder);
        } finally {
            // 释放锁
            rLock.unlock();
        }
    }

    @Transactional // 数据库中保存订单，事务控制原子性
    public void createVoucherOrder(VoucherOrder voucherOrder) {
        // 1.获取用户id
        Long userId = voucherOrder.getUserId();

        // 2.查询订单
        int count = query().eq("user_id", userId).eq("voucher_id", voucherOrder.getVoucherId()).count();
        if (count > 0) {
            // 用户已经购买过了
            log.error("用户已经购买过了！");
        }
        boolean success = secKillVoucherService.update()
                .setSql("stock = stock -1")
                .eq("voucher_id", voucherOrder.getVoucherId())
                .gt("stock", 0) // 乐观锁思想，解决超卖问题(方案二)
                .update();
        if (!success) {
            // 扣减失败
            log.error("扣减失败！");
        }

        save(voucherOrder);
    }

}

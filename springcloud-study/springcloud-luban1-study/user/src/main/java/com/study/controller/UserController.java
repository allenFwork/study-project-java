package com.study.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.study.service.OrderFeignClient;
import com.study.service.PowerFeignClient;
import com.study.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    /**
     * spring开发的 org.springframework.web.client.RestTemplate 类，专门又来调用其他微服务使用的
     */
    @Autowired
    RestTemplate restTemplate;

    private static final String POWER_URL = "http://SERVER-POWER";
    private static final String ORDER_URL = "http://SERVER-ORDER";

    @Autowired
    private PowerFeignClient powerFeignClient;

    // 使用 @Autowired注解, idea编译器会认为找不到该bean对象实例,添加红色线在下方,执行不会出现问题
    // 使用 @Resource注解, 不会出现红色的报错警告线
    @Resource
    private OrderFeignClient orderFeignClient;

    @RequestMapping("/getUser.do")
    public Result getUser(){
        Map<String, Object> map = new HashMap<>();
        map.put("key", "user");
        return Result.success("返回成功");
    }

    @RequestMapping("/getPower.do")
    @HystrixCommand(fallbackMethod = "getPowerFallBack",
                    threadPoolKey = "power", // 配置线程池的唯一ID
                    threadPoolProperties = {@HystrixProperty(name = "coreSize", value = "5") // 配置线程池属性,配置5个线程,如果5个线程调用该方法都报错,直接限制调用该方法（限流）,改为调用该方法的备份方法
                  })
    public Result getPower(){

        // 熔断全开
        // 熔断打开后,每隔5秒会进行熔断变为半开,会调用一下微服务,如果失败了,熔断直接全开
        System.out.println("测试熔断功能是否开启,进入此方法,熔断功能未开启。。。");

//        Map<String, Object> map = new HashMap<>();
//        map.put("key1", "value1");
//        map.put("key2", "value1");
//        Result.success().set("key1", "value1").set("key2", "value2");

        // 通过 http协议 完成服务之间的调用
//        return Result.success("操作成功", restTemplate.getForObject("http://localhost:6000/getPower.do", Object.class));
        // 通过 nginx 进行代理调用,实现负载均衡
//        return Result.success("操作成功",restTemplate.getForObject("http://localhost:80/getPower.do", Object.class));
        // 通过 微服务名字调用
        return Result.success("操作成功", restTemplate.getForObject(POWER_URL + "/getPower.do", Object.class));

    }
    @RequestMapping("/getPowerByFeign.do")
    // hystrix使用方法降级的方法,调用备份方法: getPowerForBack()
    @HystrixCommand(fallbackMethod = "getPowerFallBack")
    public Result getPowerByFeign() {
        return Result.success("操作成功", powerFeignClient.getPower());
    }
    
    @RequestMapping("/getOrder.do")
    public Result getOrder(){
        // 通过 微服务名字调用
        return Result.success("操作成功", restTemplate.getForObject(ORDER_URL + "/getOrder.do", Object.class));
    }
    @RequestMapping("/getOrderByFeign.do")
    public Result getOrderByFeign() {
        return Result.success("操作成功", orderFeignClient.getOrder());
    }

    /**
     * 作为方法降级的备份方法
     */
    public Result getPowerFallBack() {
        return Result.error("系统繁忙，请稍后再试");
    }

}

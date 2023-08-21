package com.study.redis.apply.utils;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RedisData {
    // 逻辑过期时间
    private LocalDateTime expireTime;
    // 存储的正真数据
    private Object data;
}

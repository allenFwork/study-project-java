package com.study.jvm.reference.soft;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * 软引用案例1 - Caffeine中的软引用
 * Caffeine框架，专门用于进行缓存
 */
public class SoftReferenceDemo1 {
    public static void main(String[] args) {
        Cache<Object, Object> build = Caffeine.newBuilder().softValues().build();
    }
}

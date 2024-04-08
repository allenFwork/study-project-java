package com.study.jvm.actual_combat.memory_leak.demo7;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 内存泄露问题：如果大量的数据在静态变量中被长期引用，数据就不会被释放，如果这些数据不再使用，就成为了内存泄漏。
 */
@Lazy // 懒加载
@Component
public class TestLazy {
    private byte[] bytes = new byte[1024 * 1024 * 1024];
}

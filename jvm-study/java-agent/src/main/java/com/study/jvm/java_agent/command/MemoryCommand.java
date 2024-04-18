package com.study.jvm.java_agent.command;

import com.sun.management.HotSpotDiagnosticMXBean;

import java.io.IOException;
import java.lang.management.BufferPoolMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MemoryCommand {

    // 打印所有内存信息
    public static void printMemory() {
        List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();

        // 堆内存
        System.out.println("堆内存：");
        getMemoryInfo(memoryPoolMXBeans, MemoryType.HEAP);
        // 非堆内存
        System.out.println("非堆内存：");
        getMemoryInfo(memoryPoolMXBeans, MemoryType.NON_HEAP);

        // 打印nio相关内容

        try {
            Class clazz = Class.forName("java.lang.management.BufferPoolMXBean");
            List<BufferPoolMXBean> bufferPoolMXBeans = ManagementFactory.getPlatformMXBeans(clazz);

            // 打印内容
            for (BufferPoolMXBean bufferPoolMXBean : bufferPoolMXBeans) {
                StringBuilder strBuilder = new StringBuilder();
                strBuilder.append("name:")
                        .append(bufferPoolMXBean.getName())
                        .append(" used:")
                        .append(bufferPoolMXBean.getMemoryUsed() / 1024 / 1024) // 转换成M做单位
                        .append("M")

                        .append(" committed:")
                        .append(bufferPoolMXBean.getTotalCapacity() / 1024 / 1024) // 转换成M做单位
                        .append("M");
                System.out.println(strBuilder);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private static void getMemoryInfo(List<MemoryPoolMXBean> memoryPoolMXBeans, MemoryType memoryType) {
        memoryPoolMXBeans.stream().filter(x -> x.getType().equals(memoryType)).forEach(x -> {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append("name:")
                    .append(x.getName())
                    .append(" used:")
                    .append(x.getUsage().getUsed() / 1024 / 1024) // 转换成M做单位
                    .append("M")

                    .append(" committed:")
                    .append(x.getUsage().getCommitted() / 1024 / 1024) // 转换成M做单位
                    .append("M")

                    .append(" max:")
                    .append(x.getUsage().getMax() / 1024 / 1024) // 转换成M做单位
                    .append("M");
            System.out.println(strBuilder);
        });
    }

    // 生成内存快照
    public static void heapDump() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        HotSpotDiagnosticMXBean hotSpotDiagnosticMXBean = ManagementFactory.getPlatformMXBean(HotSpotDiagnosticMXBean.class);

        try {
            hotSpotDiagnosticMXBean.dumpHeap(simpleDateFormat.format(new Date()) + ".hprof", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

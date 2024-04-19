package com.study.jvm.java_agent.command;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class ThreadCommand {

    // 打印线程信息
    public static void printThreadInfo() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(threadMXBean.isObjectMonitorUsageSupported(), threadMXBean.isSynchronizerUsageSupported());

        // 打印线程信息
        for (ThreadInfo threadInfo : threadInfos) {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append("name: ")
                    .append(threadInfo.getThreadName())
                    .append(" threadId: ")
                    .append(threadInfo.getThreadId())
                    .append(" threadState: ")
                    .append(threadInfo.getThreadState());
            System.out.println(strBuilder);

            // 打印线程对应的栈信息
            StackTraceElement[] stackTrace = threadInfo.getStackTrace();
            for (StackTraceElement stackTraceElement : stackTrace) {
                System.out.println(stackTraceElement);
            }
        }
    }
}
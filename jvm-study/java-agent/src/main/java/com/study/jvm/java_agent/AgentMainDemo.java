package com.study.jvm.java_agent;

import com.study.jvm.java_agent.command.ClassCommand;

import java.lang.instrument.Instrumentation;

// 本地学习使用的代码
public class AgentMainDemo {

    // 编写静态加载模式下的premain方法
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("com.study.jvm.java_agent.AgentMain.premain执行了 ... ");
    }

    // 编写动态加载模式下的agentmain方法
    public static void agentmain(String agentArgs, Instrumentation inst) {
        // System.out.println("com.study.jvm.java_agent.AgentMain.agentmain执行了 ... ");
        // MemoryCommand.printMemory();
        // MemoryCommand.heapDump();
        // ClassCommand.printAllClassLoader(inst);
        // ClassCommand.printClassSourceCode(inst);
        ClassCommand.enhanceClass(inst);
    }

}
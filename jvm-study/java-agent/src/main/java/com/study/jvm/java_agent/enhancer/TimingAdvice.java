package com.study.jvm.java_agent.enhancer;

import net.bytebuddy.asm.Advice;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

// 统计耗时，打印方法名、类名
public class TimingAdvice {

    // 方法进入时，返回开始时间
    @Advice.OnMethodEnter
    static long enter() {
        return System.nanoTime();
    }

    /**
     * 方法退出时候，统计方法执行耗时
     *
     * @Advice.Origin("#t") 表示该参数是此时的类名
     * @Advice.Origin("#m") 表示该参数是此时的方法名
     * @AgentParam("agent.log") 获取用户启动时传入的参数
     */
    @Advice.OnMethodExit
    static void exit(@Advice.Enter long value,
                     @Advice.Origin("#t") String className,
                     @Advice.Origin("#m") String methodName,
                     @AgentParam("agent.log") String fileName) {
        String str = methodName + "@" + className + "耗时为: " + (System.nanoTime() - value) + "纳秒\n";
        try {
            // 将耗时的字符串信息以追加的形式写入文件中
            FileUtils.writeStringToFile(new File(fileName), str, StandardCharsets.UTF_8, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

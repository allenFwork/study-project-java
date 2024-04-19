package com.study.jvm.java_agent;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class AttachMain {
    public static void main(String[] args) throws AttachNotSupportedException, AgentLoadException, IOException, AgentInitializationException {

        // 获取进程列表，让用户手动进行输入
        // 1.执行jps命令，打印所有的进程列表
        Process jps = Runtime.getRuntime().exec("jps");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(jps.getInputStream()));
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }

        // 2.输入进程ID
        Scanner scanner = new Scanner(System.in);
        String processId = scanner.next();

        // 获取进程虚拟机对象
        VirtualMachine vm = VirtualMachine.attach(processId);
        // 执行java agent里面的agentmain方法(本地测试)
        // vm.loadAgent("D:\\programme\\idea\\idea_workspace\\study-project-java-2023\\jvm-study\\java-agent\\target\\jvm-java-agent-jar-with-dependencies.jar");
        // 服务器上将两个jar包放在同级目录下，即可直接写jar名字
        vm.loadAgent("jvm-java-agent-jar-with-dependencies.jar");
    }

}

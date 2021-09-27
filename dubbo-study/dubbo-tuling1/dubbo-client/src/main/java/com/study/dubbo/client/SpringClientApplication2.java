package com.study.dubbo.client;

import com.alibaba.dubbo.rpc.RpcContext;
import com.study.dubbo.server.Bean.UserVo;
import com.study.dubbo.server.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


/**
 * 用来模拟测试 dubbo异步调用多个方法
 */
public class SpringClientApplication2 {
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-consumer.xml");
        UserService userService = context.getBean(UserService.class);
        while (!read().equals("exit")) {
            long begin = System.currentTimeMillis();
            UserVo userVo1 = userService.getUser(1111);
            /**
             * 1.使用Rpc框架获取异步调用的结果
             * 2.必须给上述方法设置为async=true,否则 RpcContext.getContext().getFuture(); 返回null
             */
            Future<UserVo> future1 = RpcContext.getContext().getFuture();
            UserVo userVo2 = userService.getUser(2221);
            Future<UserVo> future2 = RpcContext.getContext().getFuture();
            UserVo userVo3 = userService.getUser(3333);
            Future<UserVo> future3 = RpcContext.getContext().getFuture();
            try {
                // wait 直到拿到结果 获超时
                userVo1 = future1.get();
                // wait 直到拿到结果 获超时
                userVo2 = future2.get();
                // wait 直到拿到结果 获超时
                userVo3 = future3.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            long end = System.currentTimeMillis();
            /*
             * 结果：
             * 1.第一次调用应该花费远超过50毫秒，因为涉及到初始化
             * 2.第二次以后，每次调用的时间应该在50毫秒往上一点点
             */
            System.out.println("花费了：" + (end - begin) + "毫秒时间");
            System.out.println(userVo1);
            System.out.println(userVo2);
            System.out.println(userVo3);
        }
    }

    private static String read() throws IOException {
        byte[] b = new byte[1024];
        int size = System.in.read(b);
        return new String(b, 0, size).trim();
    }

}


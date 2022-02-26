package com.study.util;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.Random;

public class CustomerRule extends AbstractLoadBalancerRule {

    Random random;

    // 当前下标,设置默认值为-1,否则为0
    private int nowIndex = -1;
    // 上一次下标
    private int lastIndex = -1;
    // 跳过的下标
    private int skipIndex = -1;

    public CustomerRule() {
        random = new Random();
    }

    /**
     * 第一种选择策略：
     * 伪随机，当一个下标(微服务) 连续被调用两次，第三次如果还是它，那么就再随即一次
     */
    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        }
        Server server = null;
        while (server == null) {
            /**
             * 判断当前线程是否处于中断状态
             */
            if (Thread.interrupted()) {
                return null;
            }

            // 返回所有 没出问题的 server组成的List
            List<Server> upList = lb.getReachableServers();

            // 返回所有的 server组成的List（包括出问题的）
            List<Server> allList = lb.getAllServers();

            int serverCount = allList.size();
            if (serverCount == 0) {
                /**
                 * 一个微服务也没有，直接返回 null
                 */
                return null;
            }

            int index = random.nextInt(serverCount);
            /*-----------------------------------逻辑（开始）------------------------------------*/
            System.out.println("当前下标：nowIndex=" + index);
            // 随机选择完服务后，当前下表就变为了上一次的下标
            lastIndex = nowIndex;
            if (index == skipIndex) {
                System.out.println("跳过");
                System.out.println("跳过的下标：" + index);
                // 重新选择下标
                index = random.nextInt(serverCount);
            }
            // 1 1 0 是成立的
            skipIndex = -1;
            nowIndex = index;
            if (nowIndex == lastIndex) {
                skipIndex = nowIndex;
            }
            /*-----------------------------------逻辑（结束）------------------------------------*/
            /**
             * 从 allList 中获取 server,可能获取有问题的server
             * 不能从upList中拿server,因为某些有问题的server可能又能够使用了，不能直接就再也不用它了
             */
            server = upList.get(index);

            if (server == null) {
                /**
                 * 当前线程让出 CPU
                 */
                Thread.yield();
                continue;
            }

            /**
             * 如果server 是个正常的 server
             */
            if (server.isAlive()) {
                return (server);
            }

            /**
             * 不应该执行下面的代码
             */
            server = null;
            Thread.yield();
        }

        return server;
    }

    @Override
    public Server choose(Object o) {
        return null;
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

}

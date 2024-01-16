package com.study.load_balance;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 自定义的负载均衡策略
 *
 * 补充：
 * 1. 自定义的负载均衡策略不能写在@SpringbootApplication注解的@CompentScan扫描得到的地方
 * 2. 引用规则,使用 @RibbonClient(name = "MS-PROVIDER-USER", configuration = CustomizedLoadBalanceRule.class) 指定负载均衡策略
 */
public class CustomizedLoadBalanceRule extends AbstractLoadBalancerRule {

    Random rand;
    private int currentIndex = 0;
    private List<Server> currentChooseList = new ArrayList<Server>();

    public CustomizedLoadBalanceRule() {
        rand = new Random();
    }

    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
        // TODO Auto-generated method stub
    }

    /**
     * Randomly choose from all living servers
     */
    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        }
        Server server = null;

        while (server == null) {
            if (Thread.interrupted()) {
                return null;
            }
            List<Server> upList = lb.getReachableServers();
            List<Server> allList = lb.getAllServers();

            int serverCount = allList.size();
            if (serverCount == 0) {
                return null;
            }

            // 第一次进来 随机选取一个下标
            int index = rand.nextInt(serverCount);

            // 当前轮询的次数小于等于5
            if (currentIndex < 5) {
                // 保存当前选择的服务列表ip
                if (currentChooseList.isEmpty()) {
                    currentChooseList.add(upList.get(index));
                }
                // 当前的++
                currentIndex++;
                // 返回保存的
                return currentChooseList.get(0);
            } else {
                currentChooseList.clear();
                currentChooseList.add(0, upList.get(index));
                currentIndex = 0;
            }


            if (server == null) {
                Thread.yield();
                continue;
            }

            if (server.isAlive()) {
                return (server);
            }

            server = null;
            Thread.yield();
        }

        return server;
    }

}

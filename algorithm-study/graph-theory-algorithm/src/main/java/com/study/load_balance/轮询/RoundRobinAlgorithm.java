package com.study.load_balance.轮询;

import com.study.load_balance.ServerIps;

import java.util.HashMap;
import java.util.Map;

/**
 * 轮询算法策略
 */
public class RoundRobinAlgorithm {

    // 读取的 ip地址 时对应的位置
    private static Integer index = 0;

    /**
     * 算法1：
     */
    public static String getServer() {
        String ip = "";
        synchronized (index) {
            if (index >= ServerIps.LIST.size()) {
                index = 0;
            }
            ip = ServerIps.LIST.get(index);
            index ++;
        }
        return ip;
    }

    private static Integer sequenceNumber = 1;

    /**
     * 算法2：
     * 思想：涉及到权重
     * 缺点：如果第一台服务器的权重值很大，那么第一台服务器的压力很大
     */
    public static String getServerByWeight() {

        int totalWeight = 1;
        for (Integer weight : ServerIps.WEIGHT_LIST.values()) {
            // 算出权重值的总和，用来取模时使用
            totalWeight += weight;
        }
        int index = 0;
        synchronized (sequenceNumber) {
            index = sequenceNumber % totalWeight;
            sequenceNumber++;
        }
        for (String ip : ServerIps.WEIGHT_LIST.keySet()) {
            int weight = ServerIps.WEIGHT_LIST.get(ip);
            if (index < weight) {
                return ip;
            } else {
                index = index - weight;
            }
        }
        return "执行到这里出现错误";
    }


    private static Map<String, Weight> weightMap = new HashMap<>();

    /**
     * 算法3：
     * 思想：平滑加权轮询
     *       动态权重
     * 实例： A:5 B:1 C:1
     *        每台服务器都设有两个属性值: weight(权重值) currentWeight(动态权重值,初始值都为0)
     *          A,B,C               A,B,C
     *          currentWeight       weight
     *          0,0,0               5,1,1
     *                                          max(currentWeight)     选出调用的服务   max(currentWeight)-sum(weight) 最大的currentWeight-所有服务的权重值之和
     * 第一步： 5,1,1               5,1,1              5                     A                     -2,1,1
     * 第二步： 3,2,2               5,1,1              3                     A                     -4,2,2
     * 第三步： 1,3,3               5,1,1              3                     B                     1,-4,3
     * 第四步： 6,-3,4              5,1,1              6                     A                    -1,-3,4
     * 第五步： 4,-2,5              5,1,1              5                     C                     4,-2,-2
     * 第六步： 9,-1,-1             5,1,1              9                     A                     2,-1,-1
     * 第七步： 7,0,0               5,1,1              7                     A                      0,0,0             回到了最初设置的 0,0,0
     */
    public static String getServerByWeight2() {
        // 初始化 weightMap
        if (weightMap.isEmpty()) {
            for (String ip : ServerIps.WEIGHT_LIST.keySet()) {
                Integer weight = ServerIps.WEIGHT_LIST.get(ip);
                weightMap.put(ip, new Weight(weight, weight, ip));
            }
        }

        // 获取所有权重植最大的值
        Weight maxCurrentWeight = null;
        for (Weight weight : weightMap.values()) {
            if(maxCurrentWeight == null || weight.getCurrentWeight() > maxCurrentWeight.getCurrentWeight()) {
                maxCurrentWeight = weight;
            }
        }

        // java8中API,累加,计算出所有服务器的权重值之和
        int totalWeight = ServerIps.WEIGHT_LIST.values().stream().reduce(0, (w1, w2) -> w1+w2);

        maxCurrentWeight.setCurrentWeight(maxCurrentWeight.getCurrentWeight() - totalWeight);

        for (Weight weight : weightMap.values()) {
            weight.setWeight(weight.getCurrentWeight() + weight.getWeight());
        }

        // 返回当前权重值最大的 ip 地址
        return maxCurrentWeight.getIp();
    }

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
//            System.out.println(getServer());
//            System.out.println(getServerByWeight());
            System.out.println(getServerByWeight2());
        }

    }
}

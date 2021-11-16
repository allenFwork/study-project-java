package com.study.load_balance.随机;

import com.study.loadBanance.ServerIps;

import java.util.ArrayList;
import java.util.List;

/**
 * 随机策略算法
 */
public class RandomAlgorithm {

    /**
     * 算法1
     * 缺点：实际情况下，每台服务器的性能是不一样的，此算法不能够较多概率调用性能好的服务器
     */
    public static String getServer() {
        java.util.Random random = new java.util.Random();
        int index = random.nextInt(ServerIps.LIST.size());
        return ServerIps.LIST.get(index);
    }

    /**
     * 算法2:
     * 思想：通过权重来增加选择性能好的服务器
     *       通过复制思想实现权重
     * 缺点：权重值很大时，权重相加总数越大，复制出来的集合也就越大，越消耗性能
     */
    public static String getServerByWeight() {

        // ip 地址的集合
        List<String> ips = new ArrayList<>();

        // 遍历拿出所有的 ip 地址
        for (String ip : ServerIps.WEIGHT_LIST.keySet()) {
            // 获取 ip 对应的权重值
            Integer weight = ServerIps.WEIGHT_LIST.get(ip);
            // 权重值为多少添加多少次该ip到数组中
            for (int i = 0; i < weight; i++) {
                ips.add(ip);
            }
        }

        // 通过 算法1 随机获取
        java.util.Random random = new java.util.Random();
        int index = random.nextInt(ips.size());
        return ips.get(index);
    }

    /**
     * 算法3:
     * 思想：算法2的改良版
     *       将权重值看作坐标轴上数值，通过范围来获取ip地址
     * 实例：权重 A:5 B:3 C:2
     *       范围 0 - 5 - 8 - 10 , 分为三个范围
     *       off = 7
     *       off > 5         不是A
     *       (off-5=2) < 3   是B
     * 缺点：当只有两台服务器时，它们的性能也是一样时，通过此算法，过程复杂了
     */
    public static String getServerByWeight2() {

        // 权重值总和
        int totalWeight = 0;

        // 获取权重值总和作为范围
        for (Integer weight : ServerIps.WEIGHT_LIST.values()){
            totalWeight += weight;
        }

        // 获取随机数
        java.util.Random random = new java.util.Random();
        int index = random.nextInt(totalWeight);

        for (String ip : ServerIps.WEIGHT_LIST.keySet()){
            Integer weight = ServerIps.WEIGHT_LIST.get(ip);
            if (index < weight) {
                return ip;
            } else {
                index -= weight;
            }
        }
        return "执行过程出现错误";
    }

    /**
     * 算法4:
     * 思想：算法3的改良版
     *       增加所有服务器的权重是否一样的判断
     */
    public static String getServerByWeight3() {

        // 默认所有服务器的权重是一样的
        boolean sameWeight = true;

        // 权重值总和
        int totalWeight = 0;

        // 将权重值放到数组中去
        Object[] weights = ServerIps.WEIGHT_LIST.values().toArray();
        for (int i=0; i<weights.length; i++) {
            Integer weight = (Integer) weights[i];
            // 获取权重值总和作为范围
            totalWeight += weight;
            if (sameWeight && i>0 && weight!=weights[i-1]) {
                sameWeight = false;
            }
        }

        java.util.Random random = new java.util.Random();
        int index;

        if (sameWeight) {  // 权重都一样
            index = random.nextInt(ServerIps.WEIGHT_LIST.size());
            return (String) ServerIps.WEIGHT_LIST.keySet().toArray()[index];
        } else {           // 权重不一样
            // 获取随机数
            index = random.nextInt(totalWeight);
            for (String ip : ServerIps.WEIGHT_LIST.keySet()){
                Integer weight = ServerIps.WEIGHT_LIST.get(ip);
                if (index < weight) {
                    return ip;
                } else {
                    index -= weight;
                }
            }
        }
        return "执行过程出现错误";
    }

    public static void main(String[] args) {
        for (int i=0; i<100; i++) {
            // 算法1：
//            System.out.println(getServer());
            // 算法2：
//            System.out.println(getServerByWeight());
            // 算法3：
//            System.out.println(getServerByWeight2());
            System.out.println(getServerByWeight3());
        }

        // 知识点小测试
        int a = 1;
        for (int i = 0; i < 10; i++) {
            a += 2;
            System.out.println(a);
        }
        for (int i = 0; i < 10; i++) {
            a =+ 2;
            System.out.println(a);
        }
    }

}

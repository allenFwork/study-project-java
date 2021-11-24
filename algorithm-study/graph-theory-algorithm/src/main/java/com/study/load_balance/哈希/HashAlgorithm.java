package com.study.load_balance.哈希;

import com.study.load_balance.ServerIps;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 哈希策略
 */
public class HashAlgorithm {

    // 虚拟节点
    private static final int V_NODES = 160;

    // 虚拟节点Map
    private static SortedMap<Integer, String> virtualMap = new TreeMap<>();

    static {
        for (String ip : ServerIps.LIST) {
            for (int i=0; i<V_NODES; i++) {

            }
        }
    }

    /**
     * 哈希函数
     */
    private static int getHash(String str) {
        final int p =16777619;
        int hash = (int) 216613621L;
        for (int i = 0; i < str.length(); i++)
            hash = (hash ^ str.charAt(i)) * p;
        hash += hash << 13;
        hash += hash << 7;
        hash += hash << 3;
        hash += hash << 17;
        hash += hash << 5;

        // 如果计算出来的值为负数,则取其绝对值
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }

    public static void main(String[] args) {

    }

}

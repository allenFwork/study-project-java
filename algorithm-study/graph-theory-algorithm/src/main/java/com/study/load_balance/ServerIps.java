package com.study.load_balance;

import java.util.*;

public class ServerIps {

    // 模拟服务器的地址
    public static final List<String> LIST = new ArrayList<>();

    static {
        LIST.add("192.168.1.1");
        LIST.add("192.168.1.2");
        LIST.add("192.168.1.3");
        LIST.add("192.168.1.4");
        LIST.add("192.168.1.5");
        LIST.add("192.168.1.6");
        LIST.add("192.168.1.7");
        LIST.add("192.168.1.8");
        LIST.add("192.168.1.9");
        LIST.add("192.168.1.10");
    }

    public static final Map<String, Integer> WEIGHT_LIST = new HashMap<>();

    static {
        // 权重加起来是 55
        WEIGHT_LIST.put("192.168.1.1", 1);
        WEIGHT_LIST.put("192.168.1.2", 2);
        WEIGHT_LIST.put("192.168.1.3", 3);
        WEIGHT_LIST.put("192.168.1.4", 4);
        WEIGHT_LIST.put("192.168.1.5", 5);
        WEIGHT_LIST.put("192.168.1.6", 6);
        WEIGHT_LIST.put("192.168.1.7", 7);
        WEIGHT_LIST.put("192.168.1.8", 8);
        WEIGHT_LIST.put("192.168.1.9", 9);
        WEIGHT_LIST.put("192.168.1.10", 10);
    }

    public static final Map<String, Integer> ACTIVITY_LIST = new LinkedHashMap<>();

    static {
        ACTIVITY_LIST.put("192.168.1.1", 2);
        ACTIVITY_LIST.put("192.168.1.2", 0);
        ACTIVITY_LIST.put("192.168.1.3", 1);
        ACTIVITY_LIST.put("192.168.1.4", 3);
        ACTIVITY_LIST.put("192.168.1.5", 0);
        ACTIVITY_LIST.put("192.168.1.6", 1);
        ACTIVITY_LIST.put("192.168.1.7", 4);
        ACTIVITY_LIST.put("192.168.1.8", 2);
        ACTIVITY_LIST.put("192.168.1.9", 7);
        ACTIVITY_LIST.put("192.168.1.10", 3);
    }

}

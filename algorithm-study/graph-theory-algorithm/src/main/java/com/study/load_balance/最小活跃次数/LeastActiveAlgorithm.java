package com.study.load_balance.最小活跃次数;

import com.study.load_balance.ServerIps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 服务器最小活跃次数策略
 */
public class LeastActiveAlgorithm {

    private static String getServer() {
        // 最小活跃次数的服务
        Integer minActive = null;
        for (Integer number : ServerIps.ACTIVITY_LIST.values()) {
            if (minActive == null || number < minActive) {
                minActive = number;
            }
        }

        List<String> minIps = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : ServerIps.ACTIVITY_LIST.entrySet()) {
            minIps.add(entry.getKey());
        }

        if (minIps.size() > 1) {
            // 。。。
            return null;
        } else {
            return minIps.get(0);
        }
    }

}

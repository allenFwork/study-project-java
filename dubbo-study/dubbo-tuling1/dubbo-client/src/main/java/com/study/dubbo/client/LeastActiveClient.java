package com.study.dubbo.client;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.study.dubbo.server.Bean.UserVo;
import com.study.dubbo.server.UserService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tommy
 * Created by Tommy on 2018/11/20
 **/
public class LeastActiveClient {
    UserService service;

    // URL 远程服务的调用地址
    public UserService buildService(String url) {
        ApplicationConfig config = new ApplicationConfig("young-app");
        // 构建一个引用对象
        ReferenceConfig<UserService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(config);
        referenceConfig.setInterface(UserService.class);
//        referenceConfig.setUrl(url);
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://192.168.0.147:2181"));
        referenceConfig.setLoadbalance("leastactive");
        referenceConfig.setTimeout(5000);
        // 透明化
        this.service = referenceConfig.get();
        return service;
    }

    static int i = 0;

    public static void main(String[] args) throws IOException {
        LeastActiveClient client1 = new LeastActiveClient();
        client1.buildService("");
        Map<Integer, Integer> counts = new HashMap<>();
        String cmd;
        while (!(cmd = read()).equals("exit")) {
            if (cmd.equals("report")) {
                for (Map.Entry<Integer, Integer> e : counts.entrySet()) {
                    System.out.println(e.getKey() + "=" + e.getValue());
                }
            } else {
                for (int j = 0; j < 100; j++) {
                    run(client1.service, counts);
                }
            }
        }
    }

    public static void run(final UserService service, final Map<Integer, Integer> counts) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserVo u = service.getUser(111);
                if (!counts.containsKey(u.getPort())) {
                    counts.put(u.getPort(), 0);
                }
                counts.put(u.getPort(), counts.get(u.getPort()) + 1);
            }
        }).start();
    }

    private static String read() throws IOException {
        byte[] b = new byte[1024];
        int size = System.in.read(b);
        return new String(b, 0, size).trim();
    }
}

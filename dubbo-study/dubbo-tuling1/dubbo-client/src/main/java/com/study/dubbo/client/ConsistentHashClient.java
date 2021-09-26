package com.study.dubbo.client;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.study.dubbo.server.Bean.UserVo;
import com.study.dubbo.server.UserService;

import java.io.IOException;

/**
 * @author Tommy
 * Created by Tommy on 2018/11/20
 **/
public class ConsistentHashClient {
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
        referenceConfig.setLoadbalance("consistenthash");
        referenceConfig.setTimeout(5000);
        // 透明化
        this.service = referenceConfig.get();
        return service;
    }

    static int i = 0;

    public static void main(String[] args) throws IOException {
        ConsistentHashClient client1 = new ConsistentHashClient();
        client1.buildService("");
        String cmd;
        while (!(cmd = read()).equals("exit")) {
            UserVo u = client1.service.getUser(Integer.parseInt(cmd));
            System.out.println(u);
        }
    }


    private static String read() throws IOException {
        byte[] b = new byte[1024];
        int size = System.in.read(b);
        return new String(b, 0, size).trim();
    }
}

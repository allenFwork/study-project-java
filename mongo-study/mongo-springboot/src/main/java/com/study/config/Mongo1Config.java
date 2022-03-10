//package com.study.config;
//
//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientOptions;
//import com.mongodb.ServerAddress;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.MongoDbFactory;
//import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
////@EnableMongoRepositories(basePackages = "com.study", mongoTemplateRef = "mongoTemplate")
//public class Mongo1Config {
//
//    @Bean
//    // 通过 @ConfigurationProperties注解,读取springboot的application.yml配置文件,进行赋值
//    @ConfigurationProperties(prefix = "mongodb.mongo1")
//    public MongoProperties mongoProperties() {
//        return new MongoProperties();
//    }
//
//    /**
//     * 创建MongoDbFactory工厂，用来覆盖默认的MongoDbFactory
//     * @param properties
//     * @return
//     */
//    @Bean(name = "mongoDbFactory1")
//    public MongoDbFactory mongoDbFactory(MongoProperties properties) {
//        // 客户端配置（连接数，副本集群验证）
//        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
//        builder.connectionsPerHost(properties.getMaxConnectionsPerHost());
//        builder.minConnectionsPerHost(properties.getMinConnectionsPerHost());
////        if (!StringUtils.isEmpty(properties.getReplicaSet())) {
////            builder.requiredReplicaSetName(properties.getReplicaSet());
////        }
//
//        builder.threadsAllowedToBlockForConnectionMultiplier(
//                properties.getThreadsAllowedToBlockForConnectionMultiplier());
//        builder.serverSelectionTimeout(properties.getServerSelectionTimeout());
//        builder.maxWaitTime(properties.getMaxWaitTime());
//        builder.maxConnectionIdleTime(properties.getMaxConnectionIdleTime());
//        builder.maxConnectionLifeTime(properties.getMaxConnectionLifeTime());
//        builder.connectTimeout(properties.getConnectTimeout());
//        builder.socketTimeout(properties.getSocketTimeout());
////        builder.socketKeepAlive(properties.getSocketKeepAlive());
//        builder.sslEnabled(properties.getSslEnabled());
//        builder.sslInvalidHostNameAllowed(properties.getSslInvalidHostNameAllowed());
//        builder.alwaysUseMBeans(properties.getAlwaysUseMBeans());
//        builder.heartbeatFrequency(properties.getHeartbeatFrequency());
//        builder.minHeartbeatFrequency(properties.getMinHeartbeatFrequency());
//        builder.heartbeatConnectTimeout(properties.getHeartbeatConnectTimeout());
//        builder.heartbeatSocketTimeout(properties.getHeartbeatSocketTimeout());
//        builder.localThreshold(properties.getLocalThreshold());
//        MongoClientOptions mongoClientOptions = builder.build();
//        // MongoDB地址列表
//        List<ServerAddress> serverAddresses = new ArrayList<>();
//        for (String address : properties.getAddress()) {
//            String[] hostAndPort = address.split(":");
//            String host = hostAndPort[0];
//            int port = Integer.parseInt(hostAndPort[1]);
//            ServerAddress serverAddress = new ServerAddress(host, port);
//            serverAddresses.add(serverAddress);
//        }
//        //System.out.println("serverAddresses:" + serverAddresses.toString());​
//        // 连接认证
////        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(properties.getUsername(),
////                properties.getAuthenticationDatabase() != null ? properties.getAuthenticationDatabase() : properties.getDatabase(),
////                properties.getPassword().toCharArray());
//        //创建客户端和Factory
//        MongoClient mongoClient = new MongoClient(serverAddresses, mongoClientOptions);
//        SimpleMongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, properties.getDatabase());
//        return mongoDbFactory;
//    }
//
//}

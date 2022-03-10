package com.study.config;//package com.study.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.ArrayList;
import java.util.List;

/**
 * mongo单个数据源配置类
 */
//@Configuration
public class MongoConfig {

    @Bean
    // 通过 @ConfigurationProperties注解,读取springboot的application.yml配置文件,进行赋值
    @ConfigurationProperties(prefix = "mongodb.mongo")
    public MongoProperties mongoProperties() {
        return new MongoProperties();
    }

    /**
     * 创建MongoDbFactory工厂，用来覆盖默认的MongoDbFactory
     *
     * @param properties
     * @return
     */
    @Bean
    public MongoDbFactory mongoDbFactory(MongoProperties properties) {
        // 客户端配置（连接数，副本集群验证）
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.connectionsPerHost(properties.getMaxConnectionsPerHost());
        builder.minConnectionsPerHost(properties.getMinConnectionsPerHost());
//        if (!StringUtils.isEmpty(properties.getReplicaSet())) {
//            builder.requiredReplicaSetName(properties.getReplicaSet());
//        }

        builder.threadsAllowedToBlockForConnectionMultiplier(
                properties.getThreadsAllowedToBlockForConnectionMultiplier());
        builder.serverSelectionTimeout(properties.getServerSelectionTimeout());
        builder.maxWaitTime(properties.getMaxWaitTime());
        builder.maxConnectionIdleTime(properties.getMaxConnectionIdleTime());
        builder.maxConnectionLifeTime(properties.getMaxConnectionLifeTime());
        builder.connectTimeout(properties.getConnectTimeout());
        builder.socketTimeout(properties.getSocketTimeout());
//        builder.socketKeepAlive(properties.getSocketKeepAlive());
        builder.sslEnabled(properties.getSslEnabled());
        builder.sslInvalidHostNameAllowed(properties.getSslInvalidHostNameAllowed());
        builder.alwaysUseMBeans(properties.getAlwaysUseMBeans());
        builder.heartbeatFrequency(properties.getHeartbeatFrequency());
        builder.minHeartbeatFrequency(properties.getMinHeartbeatFrequency());
        builder.heartbeatConnectTimeout(properties.getHeartbeatConnectTimeout());
        builder.heartbeatSocketTimeout(properties.getHeartbeatSocketTimeout());
        builder.localThreshold(properties.getLocalThreshold());
        MongoClientOptions mongoClientOptions = builder.build();
        // MongoDB地址列表
        List<ServerAddress> serverAddresses = new ArrayList<>();
        for (String address : properties.getAddress()) {
            String[] hostAndPort = address.split(":");
            String host = hostAndPort[0];
            int port = Integer.parseInt(hostAndPort[1]);
            ServerAddress serverAddress = new ServerAddress(host, port);
            serverAddresses.add(serverAddress);
        }
        //System.out.println("serverAddresses:" + serverAddresses.toString());​
        // 连接认证
//        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(properties.getUsername(),
//                properties.getAuthenticationDatabase() != null ? properties.getAuthenticationDatabase() : properties.getDatabase(),
//                properties.getPassword().toCharArray());
        // 创建客户端和Factory
        MongoClient mongoClient = new MongoClient(serverAddresses, mongoClientOptions);
        SimpleMongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, properties.getDatabase());
        return mongoDbFactory;
    }

//    /**
//     * 版本一: spring封装对 mongo数据库进行操作的对象实质就是 MongoTemplate
//     *
//     * @return
//     * @throws Exception
//     */
//    @Bean
//    public MongoTemplate mongoTemplate() throws Exception {
//        return new MongoTemplate(mongoDbFactory(mongoProperties()));
//    }

    /**
     * @param factory
     * @param context
     * @param conversions
     * @return
     */
    @Bean(name = "mappingMongoConverter")
    public MappingMongoConverter mappingMongoConverter(MongoDbFactory factory,
                                                       MongoMappingContext context,
                                                       @Qualifier("mongoCustomConversions") CustomConversions conversions) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
        // remove _class field: 去除插入到mongo数据库时,每条记录会添加 _class 的属性
        mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
        mappingConverter.setCustomConversions(conversions);
        return mappingConverter;
    }

    /**
     * 版本二: spring封装对 mongo数据库进行操作的对象实质就是 MongoTemplate
     *
     * @return
     * @throws Exception
     */
    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory,
                                       MappingMongoConverter mappingMongoConverter) throws Exception {
        // 配置的 MongoTemplate 进行插入数据时,处理掉了会添加 _class 这个字段
        return new MongoTemplate(mongoDbFactory, mappingMongoConverter);
    }

}

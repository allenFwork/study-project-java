package com.study.config;

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
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableMongoRepositories(basePackages = {"com.study.mongo2"}, mongoTemplateRef = "mongoTemplate2")
public class Mongo2Config {

    @Bean(name = "mongoProperties2")
    @ConfigurationProperties(prefix = "mongodb.mongo2")
    public MongoProperties mongoProperties2() {
        return new MongoProperties();
    }

    @Bean(name = "mongoDbFactory2")
    public MongoDbFactory mongoDbFactory2(@Qualifier("mongoProperties2") MongoProperties properties) {
        // 客户端配置（连接数，副本集群验证）
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.connectionsPerHost(properties.getMaxConnectionsPerHost());
        builder.minConnectionsPerHost(properties.getMinConnectionsPerHost());
        builder.threadsAllowedToBlockForConnectionMultiplier(
                properties.getThreadsAllowedToBlockForConnectionMultiplier());
        builder.serverSelectionTimeout(properties.getServerSelectionTimeout());
        builder.maxWaitTime(properties.getMaxWaitTime());
        builder.maxConnectionIdleTime(properties.getMaxConnectionIdleTime());
        builder.maxConnectionLifeTime(properties.getMaxConnectionLifeTime());
        builder.connectTimeout(properties.getConnectTimeout());
        builder.socketTimeout(properties.getSocketTimeout());
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
        MongoClient mongoClient = new MongoClient(serverAddresses, mongoClientOptions);
        SimpleMongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, properties.getDatabase());
        return mongoDbFactory;
    }

    @Bean(name = "mappingMongoConverter2")
    public MappingMongoConverter mappingMongoConverter2(@Qualifier("mongoDbFactory2") MongoDbFactory factory,
                                                        MongoMappingContext context,
                                                        @Qualifier("mongoCustomConversions") CustomConversions conversions) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
        // remove _class field: 去除插入到mongo数据库时,每条记录会添加 _class 的属性
        mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
        mappingConverter.setCustomConversions(conversions);
        return mappingConverter;
    }

    @Bean(name = "mongoTemplate2")
    public MongoTemplate mongoTemplate2(@Qualifier("mongoDbFactory2") MongoDbFactory mongoDbFactory,
                                        @Qualifier("mappingMongoConverter2") MappingMongoConverter mappingMongoConverter) throws Exception {
        return new MongoTemplate(mongoDbFactory, mappingMongoConverter);
    }

}

package com.study.solrj.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
public class BeanConfig {

    @Bean
    public RestTemplate restTemplate() {
        // 底层封装 URLConnection
        return new RestTemplate();
    }

    @Value("${url}")
    private String url;

    @Bean
    public HttpSolrClient createHttpSolrClient() {
        HttpSolrClient.Builder builder = new HttpSolrClient.Builder(url);
        return builder.build();
    }

    @Value("${zk01}")
    private String zk01Url;
    @Value("${zk02}")
    private String zk02Url;
    @Value("${zk03}")
    private String zk03Url;

    @Bean("cloudSolrClient")
    public CloudSolrClient createCloudSolrClient() {
        // zookeeper集群的连接地址
        List<String> zkHosts = new ArrayList();
        zkHosts.add(zk01Url);
        zkHosts.add(zk02Url);
        zkHosts.add(zk03Url);
        CloudSolrClient.Builder builder = new CloudSolrClient.Builder(zkHosts, Optional.empty());
        CloudSolrClient cloudSolrClient = builder.build();

        // 设置连接超时时间
        cloudSolrClient.setZkConnectTimeout(300000);
        cloudSolrClient.setZkClientTimeout(300000);

        // 设置collection
        cloudSolrClient.setDefaultCollection("testcore");

        return cloudSolrClient;
    }

}

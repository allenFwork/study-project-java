package com.study.solrj;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;


// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest(classes = SolrjApplication.class)
// 让 JUnit 运行 Spring 的测试环境， 获得 Spring 环境的上下文的支持
@RunWith(SpringRunner.class)
public class SolrTest {

    @Test
    public void hello() {
        Assert.assertTrue(true);
    }

    @Autowired
    private RestTemplate restTemplate;

    @org.junit.Test
    public void solrRestApiTest() {
        // 定义请求的url
        String url = "http://localhost:9080/solr/CollectionTest/select?q=item_title:手机";
        // 发送请求，指定响应结果类型，由于响应的结果是JSON，指定Pojo/Map
        // getForEntity 方法对应的是 GET请求
        ResponseEntity<Map> resp = restTemplate.getForEntity(url, Map.class);
        // 获取响应体结果
        Map<String, Object> body = resp.getBody();
        // 获取Map中--->response
        Map<String, Object> response = (Map<String, Object>) body.get("response");
        // 获取总记录数
        System.out.println(response.get("numFound"));
        // 获取起始下标
        System.out.println(response.get("start"));
        // 获取文档
        List<Map<String, Object>> docs = (List<Map<String, Object>>) response.get("docs");
        for (Map<String, Object> doc : docs) {
            System.out.println(doc.get("id"));
            System.out.println(doc.get("item_title"));
            System.out.println(doc.get("item_price"));
            System.out.println(doc.get("item_image"));
            System.out.println("=====================");
        }
    }

}

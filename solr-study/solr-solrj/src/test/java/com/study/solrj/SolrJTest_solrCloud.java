package com.study.solrj;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;

/**
 * SolrJ对集群的操作
 */
public class SolrJTest_solrCloud extends SolrJTest {

    @Autowired
    @Qualifier("cloudSolrClient")
    private SolrClient solrClient;

    /**
     * 添加文档
     */
    @Test
    public void addDocument() throws IOException, SolrServerException {
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id", 2);
        document.setField("name", "java");
        // 此处的testcore就是solr集群中对应的 Collection “testcore”
//        solrClient.add("testcore", document);
        // 配置CloudSolrClient时，设置了默认的Collection为testcore
        solrClient.add(document);
        // 提交
        solrClient.commit();
    }

    /**
     * 查询文档
     */
    @Test
    public void queryDocument() throws IOException, SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("*:*");
        QueryResponse queryResponse = solrClient.query(solrQuery);

        SolrDocumentList results = queryResponse.getResults();
        for (SolrDocument document : results) {
            System.out.println(document);
        }

        System.out.println("满足条件的文档数量：" + results.getNumFound());
    }

}



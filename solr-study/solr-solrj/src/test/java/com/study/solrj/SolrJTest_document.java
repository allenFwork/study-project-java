package com.study.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Date;

public class SolrJTest_document extends SolrJTest {

    @Autowired
    // SolrJ提供的Solr的客户端
    private HttpSolrClient httpSolrClient;

    /**
     * SolrJ 添加文档
     *
     * @throws IOException
     * @throws SolrServerException
     */
    @Test
    public void addDocumentTest() throws IOException, SolrServerException {
        // 创建文档
        SolrInputDocument document = new SolrInputDocument();
        // 指定文档中的域
        document.setField("id", "889922");
        document.setField("item_title", "华为 Meta30 高清手机");
        document.setField("item_price", 20);
        document.setField("item_images", "21312312.jpg");
        document.setField("item_createtime", new Date());
        document.setField("item_updatetime", new Date());
        document.setField("item_category", "手机");
        document.setField("item_brand", "华为");
        // 添加文档
        httpSolrClient.add(document);
        httpSolrClient.commit();
    }

    /**
     * SolrJ 修改文档
     *
     * @throws IOException
     * @throws SolrServerException
     */
    @Test
    public void updateDocumentTest() throws IOException, SolrServerException {
        // 创建文档
        SolrInputDocument document = new SolrInputDocument();
        // 指定文档中的域
        document.setField("id", "889922");
        document.setField("book_name", "SolrJ是Solr提供的操作Solr的javaAPI,挺好用");
        document.setField("book_num", 20);
        document.setField("book_pic", "21312312.jpg");
        document.setField("book_price", 20.0);
        // 添加文档
        httpSolrClient.add(document);
        httpSolrClient.commit();
    }

    /**
     * SolrJ 删除文档
     *
     * @throws IOException
     * @throws SolrServerException
     */
    @Test
    public void deleteDocumentTest() throws IOException, SolrServerException {
        httpSolrClient.deleteById("889922");
        httpSolrClient.commit();
    }

    /**
     * SolrJ 删除文档: 通过查询结果删除
     *
     * @throws IOException
     * @throws SolrServerException
     */
    @Test
    public void deleteQueryTest() throws IOException, SolrServerException {
        // 条件为 *:* , 表示删除所有
        httpSolrClient.deleteByQuery("book_name:java");
        httpSolrClient.commit();
    }

}

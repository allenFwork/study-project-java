package com.study.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * SolrJ的基本查询：简单查询，过滤查询，分页查询，组合查询
 */
public class SolrJTest_query extends SolrJTest {

    @Autowired
    // SolrJ提供的Solr的客户端
    private HttpSolrClient httpSolrClient;

    /**
     * 基础查询测试
     *
     * @throws IOException
     * @throws SolrServerException
     */
    @Test
    public void baseQueryTest() throws IOException, SolrServerException {
        // 封装查询条件
        SolrQuery params = new SolrQuery();
        // 设置查询条件, 参数1:查询参数, q, fq...
        params.setQuery("item_title:手机"); // 等价于 params.set("q", "item_title:手机");
        // 执行查询,获取结果
        QueryResponse resp = httpSolrClient.query(params);
        // 满足条件的文档
        SolrDocumentList results = resp.getResults();
        // 迭代results
        for (SolrDocument result : results) {
            System.out.println(result.get("id") + "--" + result.get("item_title"));
        }
        // 获取总记录
        long numFound = results.getNumFound();
        System.out.println(numFound);
    }

    /**
     * 基础查询+过滤
     * 过滤条件：品牌是华为，价格在[1000-2000]之间
     *
     * @throws IOException
     * @throws SolrServerException
     */
    @Test
    public void baseFilterQueryTest() throws IOException, SolrServerException {
        // 封装查询条件
        SolrQuery params = new SolrQuery();
        // 设置查询条件, 参数1:查询参数, q, fq...
        params.setQuery("item_title:手机");
        params.addFilterQuery("item_brand:华为");
        params.addFilterQuery("item_price:[1000 TO 2000]");
        // 执行查询,获取结果
        QueryResponse resp = httpSolrClient.query(params);
        // 满足条件的文档
        SolrDocumentList results = resp.getResults();
        // 迭代results
        for (SolrDocument result : results) {
            System.out.println(result.get("id") + "--" + result.get("item_title") + "---" + result.get("item_brand") + "---" + result.get("item_price"));
        }
        // 获取总记录
        long numFound = results.getNumFound();
        System.out.println(numFound);
    }

    /**
     * 分页 + 排序 + 取别名
     *
     * @throws IOException
     * @throws SolrServerException
     */
    @Test
    public void queryByPageAndSortTest() throws IOException, SolrServerException {
        // 封装查询条件
        SolrQuery solrQuery = new SolrQuery();
        // 设置查询条件, 参数1:查询参数, q, fq...
        solrQuery.setQuery("item_title:手机");
        solrQuery.addFilterQuery("item_brand:华为");
        solrQuery.addFilterQuery("item_price:[1000 TO 2000]");

        // 第一页
        solrQuery.setStart(0);
        // 20条
        solrQuery.setRows(20);

        // 排序: SolrQuery.ORDER.desc降序; SolrQuery.ORDER.asc升序
        solrQuery.addSort("item_price", SolrQuery.ORDER.desc);
        solrQuery.addSort("id", SolrQuery.ORDER.asc);

        // 取别名：
//        solrQuery.setFields("price:item_price"); // 如果Fields只设置了price,那么结果中也只有price一个域
        solrQuery.setFields("id,price:item_price,title:item_title,brand:item_brand,category:item_category,image:item_image");

        // 执行查询,获取结果
        QueryResponse resp = httpSolrClient.query(solrQuery);
        // 满足条件的文档
        SolrDocumentList results = resp.getResults();
        // 迭代results
        for (SolrDocument result : results) {
            System.out.println(result.get("id") + "--" + result.get("item_title") + "---" + result.get("item_brand") + "---" + result.get("item_price"));
            System.out.println(result);
            System.out.println(result.get("id") + "--" + result.get("title") + "---" + result.get("brand") + "---" + result.get("category"));
        }
        // 获取总记录
        long numFound = results.getNumFound();
        System.out.println(numFound);
    }

    /**
     * SolrJ 组合查询
     *
     * @throws IOException
     * @throws SolrServerException
     */
    @Test
    public void combinationQueryTest() throws IOException, SolrServerException {
        // 封装查询条件
        SolrQuery solrQuery = new SolrQuery();
//        solrQuery.setQuery("item_title:手机 OR item_title:平板电脑");
//        solrQuery.setQuery("item_title:手机 AND item_title:三星");
        solrQuery.setQuery("+item_title:手机 +item_title:三星");
        // 执行查询,获取结果
        QueryResponse resp = httpSolrClient.query(solrQuery);
        // 满足条件的文档
        SolrDocumentList results = resp.getResults();
        // 迭代results
        for (SolrDocument result : results) {
            System.out.println(result);
        }
        // 获取总记录
        long numFound = results.getNumFound();
        System.out.println(numFound);
    }

}

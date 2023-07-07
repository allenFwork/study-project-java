package com.study.solrj;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupCommand;
import org.apache.solr.client.solrj.response.GroupResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.GroupParams;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class SolrJTest_query_hightLight extends SolrJTest {

    @Autowired
    private SolrClient solrClient;

    @Test
    public void highlightingQueryTest() throws IOException, SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("item_title:三星手机");
        // 开启高亮
        solrQuery.setHighlight(true);
        // 设置高亮域
        solrQuery.addHighlightField("item_title");
        // 高亮的前后缀
        solrQuery.setHighlightSimplePre("<font>");
        solrQuery.setHighlightSimplePost("</font>");

        // 设置修改高梁器
//        solrQuery.set("hl.method", "fastVector");

        QueryResponse response = solrClient.query(solrQuery);

        SolrDocumentList results = response.getResults();
        for (SolrDocument result : results) {
            System.out.println(result);
        }

        // 解析高亮结果
        // 获取高亮结果
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        // map的key是文档id, map的value包含高亮的数据
        for (String id : highlighting.keySet()) {
            System.out.println("文档id: " + id);
            Map<String, List<String>> highLightData = highlighting.get(id);

            /**
             * item_title: [
             *      "飞利浦 老人<em>手机</em> (X2560) 深情蓝 移动联通2G<em>手机</em> 双卡双待"
             * ]
             */
            if (highLightData != null && highLightData.size() > 0) {
                List<String> stringList = highLightData.get("item_title");
                if (stringList != null && stringList.size() > 0) {
                    String title = stringList.get(0);
                    System.out.println(title);
                }
            }
        }
    }

}



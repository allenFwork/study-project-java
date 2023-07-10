package com.study.solrj.controller;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/solrCloud/docker/test")
public class TestController {

    @Autowired
    private CloudSolrClient cloudSolrClient;

    @RequestMapping("/addDocument")
    public Map<String, Object> addDocument(String id, String bookName) throws IOException, SolrServerException {
        SolrInputDocument doc = new SolrInputDocument();
        doc.setField("id", id);
        doc.setField("book_name", bookName);
        cloudSolrClient.add(doc);
        cloudSolrClient.commit();
        Map<String, Object> result = new HashMap<>();
        result.put("flag", true);
        result.put("msg", "添加文档成功");
        return result;
    }

}

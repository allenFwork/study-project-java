package com.study.solrj;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.SuggesterResponse;
import org.apache.solr.client.solrj.response.Suggestion;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * SolrJ对SolrCore的维护操作
 */
public class SolrJTest_solrCore extends SolrJTest {

    @Autowired
    private SolrClient solrClient;

    /**
     * 创建SolrCore
     */
    @Test
    public void createSolrCore() throws IOException, SolrServerException {
        CoreAdminRequest.createCore("collection2", "D:\\programme\\solr\\solr_home\\Collection2", solrClient);
    }

    @Test
    public void reloadSolrCore() throws IOException, SolrServerException {
        // 先将 solrCore中的 collection2删除掉，然后又重新添加进去
        CoreAdminRequest.reloadCore("collection2", solrClient);
    }

    @Test
    public void renameSolrCore() throws IOException, SolrServerException {
        CoreAdminRequest.renameCore("collection2", "collection2_newName", solrClient);
    }

    @Test
    public void unloadSolrCore() throws IOException, SolrServerException {
        // 卸载SolrCore, 但是该SolrCore的文件还是存在于硬盘上(没有删除)
        CoreAdminRequest.unloadCore("collection2_newName", solrClient);
    }

    @Test
    public void swapSolrCore() throws IOException, SolrServerException {
        // 交换两个SolrCore中的数据
        CoreAdminRequest.swapCore("collection1", "collection2", solrClient);
    }


}



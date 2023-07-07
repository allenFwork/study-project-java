package com.study.solrj;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.SuggesterResponse;
import org.apache.solr.client.solrj.response.Suggestion;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class SolrJTest_query_suggest extends SolrJTest {

    @Autowired
    private SolrClient solrClient;

    @Test
    public void suggestQueryTest() throws IOException, SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("item_title:iphonxx");
        solrQuery.set("spellcheck", true);
        QueryResponse response = solrClient.query(solrQuery);
        /**
         * suggestions: [
         *      "iphonxx", {
         *          numFound: 1,
         *          startOffset: 11,
         *          endOffset: 18,
         *          suggestion: [
         *              "iphone6"
         *          ]
         *      }
         * ]
         */
        SpellCheckResponse spellCheckResponse = response.getSpellCheckResponse();
        Map<String, SpellCheckResponse.Suggestion> suggestionMap = spellCheckResponse.getSuggestionMap();
        for (String s : suggestionMap.keySet()) {
            // 错误的词
            System.out.println(s);
            // 建议的词
            SpellCheckResponse.Suggestion suggestion = suggestionMap.get(s);

            List<String> alternatives = suggestion.getAlternatives();
            System.out.println(alternatives);
        }
    }

    @Test
    public void suggestQueryTest2() throws IOException, SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        // 设置参数
        solrQuery.setQuery("java");
        // 开启自动建议
        solrQuery.set("suggest", true);
        // 指定自动建议的组件
        solrQuery.set("suggest.dictionary", "mySuggester");
        // 设置自动建议数量
        solrQuery.set("suggest.count", "5");
        QueryResponse response = solrClient.query(solrQuery);

        // 解析结果
        SuggesterResponse suggesterResponse = response.getSuggesterResponse();
        // 获取自动建议的数据
        Map<String, List<Suggestion>> suggestions = suggesterResponse.getSuggestions();
        for (String key : suggestions.keySet()) {
            System.out.println(key);
            List<Suggestion> suggestionList = suggestions.get(key);
            for (Suggestion suggestion : suggestionList) {
                String term = suggestion.getTerm();
                System.out.println(term);
            }
        }
    }
}



package com.study.solrj;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.*;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.GroupParams;
import org.apache.solr.common.util.NamedList;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class SolrJTest_query_group extends SolrJTest {

    @Autowired
    private SolrClient solrClient;

    /**
     * 需求：查询Item_title中包含手机的文档，按照品牌对文档进行分组；同组中的文档放在一起。
     */
    @Test
    public void groupQueryTest() throws IOException, SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        /**
         * q=item_title:手机
         * &group=true
         * &group.field=item_brand
         */
        solrQuery.setQuery("item_title:手机");
        // 注意solrJ中每没有提供分组特有API。需要使用set方法完成
        solrQuery.setGetFieldStatistics(true);
        solrQuery.set(GroupParams.GROUP, true);
        solrQuery.set(GroupParams.GROUP_FIELD, "item_brand");

        // 设置组的分页参数
        solrQuery.setStart(0);
        solrQuery.setRows(3);
        // 设置组内文档的分页参数
        solrQuery.set(GroupParams.GROUP_OFFSET, 0);
        solrQuery.set(GroupParams.GROUP_LIMIT, 5);

        // 按照组内价格排序降序
        solrQuery.set(GroupParams.GROUP_SORT, "item_price desc");

        QueryResponse response = solrClient.query(solrQuery);
        // GroupResponse封装了整个group分组结果
        GroupResponse groupResponse = response.getGroupResponse();
        // 由于分组的字段可以是多个。所以返回数组
        List<GroupCommand> values = groupResponse.getValues();
        // 获取品牌分组结果
        GroupCommand groupCommand = values.get(0);
        // 匹配到的文档数量
        int matches = groupCommand.getMatches();
        System.out.println(matches);
        // 每个组合每个组中的文档信息
        List<Group> groups = groupCommand.getValues();
        for (Group group : groups) {
            // 分组名称
            System.out.println(group.getGroupValue());
            // 组内文档
            SolrDocumentList result = group.getResult();
            // 组内文档的数量 result.getNumFound()
            System.out.println(group.getGroupValue() + ": 文档个数" + result.getNumFound());
            for (SolrDocument entries : result) {
                System.out.println(entries);
            }
        }
    }


}

package com.study.solrj;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.*;
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

/**
 * SolrJ的基本查询：简单查询，过滤查询，分页查询，组合查询
 */
public class SolrJTest_query_facet extends SolrJTest {

    // SolrJ提供的Solr的客户端
    @Autowired
    private SolrClient solrClient;

    /**
     * Facet 设置FacetField域名查询
     * 需求：对 item_title 中包含手机的文档，按照品牌域进行分组，并且统计数量
     *
     * @throws IOException
     * @throws SolrServerException
     */
    @Test
    public void facetFieldQueryTest() throws IOException, SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        // 查询条件
        solrQuery.setQuery("item_title:手机");
        // Facet相关参数
        // 等价于facet=on, 开启Facet查询功能
        solrQuery.setFacet(true);
        // 设置 facetField, 通过 item_brand 进行聚合统计
        solrQuery.addFacetField("item_brand");
        // 限制统计数量，通过item_brand聚合后，对应的数量小于1的就不用返回了(过滤掉了)
        solrQuery.setFacetMinCount(1);
        QueryResponse response = solrClient.query(solrQuery);

        // 对于Fact查询来说，我们主要获取Facet相关的数据
        // 根据域名获取指定分组数据
        FacetField facetField = response.getFacetField("item_brand");
        List<FacetField.Count> values = facetField.getValues();
        for (FacetField.Count value : values) {
            System.out.println(value.getName() + "--" + value.getCount());
        }
    }

    /**
     * 需求：查询分类是平板电视的商品数量，品牌是华为的商品数量 ，品牌是三星的商品数量，价格在1000-2000的商品数量；
     *
     * @throws IOException
     * @throws SolrServerException
     */
    @Test
    public void facetFieldQueryTest2() throws IOException, SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        // 查询条件
        solrQuery.setQuery("*:*");
        // Facet相关参数
        /**
         * facet.query=item_category:平板电视&
         * facet.query=item_brand:华为&
         * facet.query=item_brand:三星&
         * facet.query=item_price:[1000 TO 2000]
         *
         * {!key=平板电视}表示起别名，别名的名字是“平板电视”
         */
        solrQuery.setFacet(true);
        solrQuery.addFacetQuery("{!key=平板电视}item_category:平板电视");
        solrQuery.addFacetQuery("{!key=华为品牌}item_brand:华为");
        solrQuery.addFacetQuery("{!key=三星品牌}item_brand:三星");
        solrQuery.addFacetQuery("{!key=1000到2000}item_price:[1000 TO 2000]");
        QueryResponse response = solrClient.query(solrQuery);

        // 对于Fact查询来说，我们主要获取Facet相关的数据: 根据域名获取指定分组数据
        /**
         * item_category:平板电视: 207,
         * item_brand:华为: 67,
         * item_brand:三星: 154,
         * item_price:[1000 TO 2000]: 217
         */
        Map<String, Integer> facetQuery = response.getFacetQuery();
        for (String key : facetQuery.keySet()) {
            System.out.println(key + "--" + facetQuery.get(key));
        }
    }

    /**
     * 需求：分组查询价格0-2000 ，2000-4000，4000-6000....18000-20000每个区间商品数量
     */
    @Test
    public void facetRangeTest() throws IOException, SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        // 查询条件
        solrQuery.setQuery("*:*");
        // Facet相关参数
        /**
         * facet=on&
         * facet.range=item_price&
         * facet.range.start=0&
         * facet.range.end=20000&
         * facet.range.gap=2000
         */
        solrQuery.setFacet(true);
        solrQuery.addNumericRangeFacet("item_price", 0, 20000, 2000);
        QueryResponse response = solrClient.query(solrQuery);

        List<RangeFacet> facetRanges = response.getFacetRanges();
        for (RangeFacet facetRange : facetRanges) {
            System.out.println(facetRange.getName());
            List<RangeFacet.Count> counts = facetRange.getCounts();
            for (RangeFacet.Count count : counts) {
                System.out.println(count.getValue() + "---" + count.getCount());
            }
        }
    }

    /**
     * 需求：统计2015年每个季度添加的商品数量
     */
    @Test
    public void facetRangeTest2() throws IOException, SolrServerException, ParseException {
        SolrQuery solrQuery = new SolrQuery();
        // 查询条件
        solrQuery.setQuery("*:*");
        // Facet相关参数
        /**
         * facet=on&
         * facet.range=item_createtime&
         * facet.range.start=2015-01-01T00:00:00Z&
         * facet.range.end=2016-01-01T00:00:00Z&
         * facet.range.gap=%2B3MONTH
         */
        solrQuery.setFacet(true);

        // 设置时区：不设置时区，可能会相差8小时，查询出 2024-12-31 00:00:00 的数据
        TimeZone timeZone = TimeZone.getTimeZone("GTM-8");
        TimeZone.setDefault(timeZone);

        Date start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2015-01-01 00:00:00");
        Date end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-01-01 00:00:00");
        solrQuery.addDateRangeFacet("item_createtime", start, end, "+3MONTH");
        QueryResponse response = solrClient.query(solrQuery);

        List<RangeFacet> facetRanges = response.getFacetRanges();
        for (RangeFacet facetRange : facetRanges) {
            System.out.println(facetRange.getName());
            List<RangeFacet.Count> counts = facetRange.getCounts();
            for (RangeFacet.Count count : counts) {
                System.out.println(count.getValue() + "---" + count.getCount());
            }
        }
    }

    /**
     * 需求：统计item_price在0-1000和0-100商品数量和item_createtime是2019年~现在添加的商品数量
     */
    @Test
    public void testIntervalRange() throws IOException, SolrServerException, ParseException {
        SolrQuery solrQuery = new SolrQuery();
        // 查询条件
        solrQuery.setQuery("*:*");
        // Facet相关参数
        /**
         * &facet=on
         * &facet.interval=item_price
         * &f.item_price.facet.interval.set=[0,1000]
         * &f.item_price.facet.interval.set=[0,100]
         * &facet.interval=item_createtime
         * &f.item_createtime.facet.interval.set=[2019-01-01T0:0:0Z,NOW]
         */
        solrQuery.setFacet(true);
        solrQuery.addIntervalFacets("item_price", new String[]{"[0,1000]", "[0,100]"});
        solrQuery.addIntervalFacets("item_createtime", new String[]{"[2019-01-01T0:0:0Z,NOW]"});
        QueryResponse response = solrClient.query(solrQuery);

        /**
         *  facet_intervals: {
         *       item_price: {
         *          [0,1000]: 285,
         *          [0,100]: 20
         *      },
         *      item_createtime: {
         *          [2019-01-01T0:0:0Z,NOW]: 22
         *      }
         *  }
         */
        List<IntervalFacet> intervalFacets = response.getIntervalFacets();
        for (IntervalFacet intervalFacet : intervalFacets) {
            String field = intervalFacet.getField();
            System.out.println(field);
            List<IntervalFacet.Count> intervals = intervalFacet.getIntervals();
            for (IntervalFacet.Count interval : intervals) {
                System.out.println(interval.getKey());
                System.out.println(interval.getCount());
            }

        }
    }

    /**
     * 需求：统计每一个品牌和其不同分类商品对应的数量；
     *    联想 手机 10
     *    联想  电脑 2
     *    华为 手机 10
     *    ...
     */
    @Test
    public void pivotFacetTest() throws IOException, SolrServerException, ParseException {
        SolrQuery solrQuery = new SolrQuery();
        // 查询条件
        solrQuery.setQuery("*:*");

        /**
         *  &facet=on
         *  &facet.pivot=item_brand,item_category
         *
         *  等价于先按照 item_brand 分组，在其基础上进行 item_category分组
         */
        solrQuery.addFacetPivotField("item_brand,item_category");
        // 执行查询
        QueryResponse response = solrClient.query(solrQuery);

        // 解析结果：
        // response.getFacetPivot() 返回对整个pivot查询结果的封装
        NamedList<List<PivotField>> facetPivot = response.getFacetPivot();
        // 基于 item_brand,item_category 维度查询的结果
        for (Map.Entry<String, List<PivotField>> stringListEntry : facetPivot) {
            List<PivotField> value = stringListEntry.getValue();
            /**
             * PivotField类型实例：
             * {
             *     "field": "item_brand",
             *     "value": "三星",
             *     "count": 154,
             *     "pivot": [
             *         {
             *             "field": "item_category",
             *             "value": "手机",
             *             "count": 134
             *         }
             *     ]
             * }
             */
            for (PivotField pivotField : value) {
                System.out.println(pivotField.getField());
                System.out.println(pivotField.getValue());
                System.out.println(pivotField.getCount());
                List<PivotField> pivot = pivotField.getPivot();
                for (PivotField field : pivot) {
                    System.out.println(field.getField());
                    System.out.println(field.getValue());
                    System.out.println(field.getCount());
                }
                System.out.println("--------------------");
            }
        }
    }

}

package cn.com.search.core;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.net.InetAddress;

/**
 * 实现 spring 的 org.springframework.beans.factory.InitializingBean 接口，
 * 重写 afterPropertiesSet()方法，在初始化bean的时候会执行该方法
 */
@Service
public class ElasticSearchManger implements InitializingBean {

    public TransportClient client;
    public Settings settings;

    /**
     * 初始化连接elasticsearch集群
     */
    public void init() {
        settings = Settings.settingsBuilder().put("cluster.name", "my.elk_cluster")
                .put("client.transport.sniff", true)
                .build();
        try {
            /**
             * 客户端是数据传输接口连接,所以端口号是9300
             */
            client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.33.4"), 9300))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.33.4"), 9300));
        } catch (Exception e) {

        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    public static void main(String[] args) {
        ElasticSearchManger elasticSearchManger = new ElasticSearchManger();
        elasticSearchManger.init();
        SearchResponse response = elasticSearchManger.client.prepareSearch("bbg_goods").setTypes("item_loc")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(QueryBuilders.queryStringQuery("item_desc:格力"))
                .setFrom(0).setSize(10).setExplain(false)
                .get();
        System.out.println(response);
    }

}
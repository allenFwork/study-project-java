package com.study.util.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * 通过连接池管理连接对象，不会频繁创建连接对象
 * 使用情景：多线程频繁调用第三方接口
 */
public class HttpConnectionPoolUtil2 {

    private static final Integer MAX_TOTAL = 200;             // 连接池最大连接数
    private static final Integer MAX_PER_ROUTE = 40;          // 单个路由默认最大连接数
    private static final Integer CONN_TIMEOUT = 5 * 1000;     // 连接超时时间ms
    private static final Integer SOCK_TIMEOUT = 5 * 1000;     // 读取超时时间ms
    private static final Integer CONN_REQ_TIMEOUT = 5 * 1000; // 获取连接实例的超时时间ms

    // 发送请求的客户端单例
    private CloseableHttpClient httpClient;
    //连接池管理类
    private PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;

    // 监控连接池现状的线程
    private IdleConnectionMonitorThread idleConnectionMonitor = null;


    // 在类的初始化阶段的执行
    public HttpConnectionPoolUtil2() {
        // 创建连接池管理器
        poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        // 设置最大的连接数
        poolingHttpClientConnectionManager.setMaxTotal(MAX_TOTAL);
        // 设置每个主机的最大连接数，访问每一个网站指定的连接数，不会影响其他网站的访问
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);

        // 此处采用统一配置，也可以单独配置到相应的 HttpGet 或 HttpPost 对象上
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(CONN_TIMEOUT)                // 设置连接超时
                .setSocketTimeout(SOCK_TIMEOUT)                 // 设置读取超时
                .setConnectionRequestTimeout(CONN_REQ_TIMEOUT)  // 设置从连接池获取连接实例的超时
                .build();

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClient = httpClientBuilder
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setConnectionManagerShared(true)
                .setDefaultRequestConfig(requestConfig)
                .build();

        // 如果不采用连接池就是这种方式获取连接
        // CloseableHttpClient httpClient = HttpClients.createDefault();

        idleConnectionMonitor = new IdleConnectionMonitorThread(poolingHttpClientConnectionManager);
    }

    /**
     * get请求
     *
     * @param requestUrl
     * @return
     */
    public String doGetByPool(String requestUrl) {
        CloseableHttpResponse response = null;
        String resultContent = "";
        HttpGet httpGet = new HttpGet(requestUrl);
        try {
            // 执行get请求得到返回对象
            response = httpClient.execute(httpGet);
            // 通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            // 通过EntityUtils中的toString方法将结果转换为字符串
            resultContent = EntityUtils.toString(entity);
            // 关闭HttpEntity是的流，如果手动关闭了InputStream instream = entity.getContent();这个流，也可以不调用这个方法
            EntityUtils.consume(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 主动释放连接，其他请求才可以从连接池中获取该连接
            httpGet.releaseConnection();

            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return resultContent;
    }

    //用于监控空闲的连接池连接
    private final class IdleConnectionMonitorThread extends Thread {
        private final HttpClientConnectionManager connMgr;
        private volatile boolean shutdown;

        private static final int MONITOR_INTERVAL_MS = 2000;
        private static final int IDLE_ALIVE_MS = 5000;

        public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
            super();
            this.connMgr = connMgr;
            this.shutdown = false;
        }

        @Override
        public void run() {
            try {
                while (!shutdown) {
                    synchronized (this) {
                        wait(MONITOR_INTERVAL_MS);
                        // 关闭无效的连接
                        connMgr.closeExpiredConnections();
                        // 关闭空闲时间超过IDLE_ALIVE_MS的连接
                        connMgr.closeIdleConnections(IDLE_ALIVE_MS, TimeUnit.MILLISECONDS);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 关闭后台连接
        public void shutdown() {
            shutdown = true;
            synchronized (this) {
                notifyAll();
            }
        }
    }

    public void clean() throws Throwable {
        this.finalize();
    }

}


# 1. 使用

## 1.1 使用http连接池的优点:

1. 复用http连接,省去了tcp的3次握手和4次挥手的时间,极大降低请求响应的时间
2. 自动管理 `tcp` 连接,不用人为地释放/创建连接

## 1.2 使用http连接池的大致流程 

1. 创建 PoolingHttpClientConnectionManager 实例
2. 给manager设置参数
3. 给manager设置重试策略
4. 给manager设置连接管理策略
5. 开启监控线程，及时关闭被服务器单向断开的连接
6. 构建 HttpClient 实例
7. 创建 HttpPost/HttpGet 实例，并设置参数
8. 获取响应，做适当的处理
9. 将用完的连接放回连接池

## 1.3 示例代码

```java

```





## 1.4 关键点

- httpclient 实例必须是单例，且该实例必须使用 `HttpClients.custom().setConnectionManager()` 来绑定一个 PollingHttpClientConnectionManager，这样该client每次发送请求都会通过manager来获取连接，如果连接池中没有可用连接的话，则该会阻塞线程，直到有可用的连接
- httpclients4.5.x 版本直接调用 ClosableHttpResponse.close() 就能直接把连接放回连接池，而不是关闭连接，以前的版本貌似要调用其他方法才能把连接放回连接池
- 由于服务器一般不会允许无限期的长连接,所以需要开启监控线程,每隔一段时间就检测一下连接池中连接的情况,及时关闭异常连接和长时间空闲的连接,避免占用服务器资源.
  

https://www.cnblogs.com/wusanga/p/17392445.html



## HttpClient及其连接池配置

- 整个线程池中最大连接数 MAX_CONNECTION_TOTAL = 800
- 路由到某台主机最大并发数，是MAX_CONNECTION_TOTAL（整个线程池中最大连接数）的一个细分 ROUTE_MAX_COUNT = 500
- 重试次数，防止失败情况 RETRY_COUNT = 3
- 客户端和服务器建立连接的超时时间 CONNECTION_TIME_OUT = 5000 
- 客户端从服务器读取数据的超时时间 READ_TIME_OUT = 7000 
- 从连接池中获取连接的超时时间 CONNECTION_REQUEST_TIME_OUT = 5000 
- 连接空闲超时，清楚闲置的连接 CONNECTION_IDLE_TIME_OUT = 5000 
- 连接保持存活时间 DEFAULT_KEEP_ALIVE_TIME_MILLIS = 20 * 1000



## MaxtTotal 和 DefaultMaxPerRoute 的区别

- MaxtTotal是整个池子的大小；
- DefaultMaxPerRoute是根据连接到的主机对MaxTotal的一个细分；

比如： MaxtTotal=400，DefaultMaxPerRoute=200，而我只连接到 `http://hjzgg.com` 时，到这个主机的并发最多只有200；而不是400；
而我连接到`http://qyxjj.com` 和 `http://httls.com` 时，到每个主机的并发最多只有200；即加起来是400（但不能超过400）。
所以起作用的设置是DefaultMaxPerRoute。



## HttpClient连接池模型

<img src="images\image-20240112122635989.png" alt="image-20240112122635989" style="zoom: 33%;" />





## HttpClient从连接池中获取连接源码分析

1. org.apache.http.pool.AbstractConnPool

   ```java
   private E getPoolEntryBlocking(
           final T route, final Object state,
           final long timeout, final TimeUnit tunit,
           final PoolEntryFuture<E> future)
               throws IOException, InterruptedException, TimeoutException {
   
   
       Date deadline = null;
       if (timeout > 0) {
           deadline = new Date
               (System.currentTimeMillis() + tunit.toMillis(timeout));
       }
   
   
       this.lock.lock();
       try {
           final RouteSpecificPool<T, C, E> pool = getPool(route);// 这是每一个路由细分出来的连接池
           E entry = null;
           while (entry == null) {
               Asserts.check(!this.isShutDown, "Connection pool shut down");
               //从池子中获取一个可用连接并返回
               for (;;) {
                   entry = pool.getFree(state);
                   if (entry == null) {
                       break;
                   }
                   if (entry.isExpired(System.currentTimeMillis())) {
                       entry.close();
                   } else if (this.validateAfterInactivity > 0) {
                       if (entry.getUpdated() + this.validateAfterInactivity <= System.currentTimeMillis()) {
                           if (!validate(entry)) {
                               entry.close();
                           }
                       }
                   }
                   if (entry.isClosed()) {
                       this.available.remove(entry);
                       pool.free(entry, false);
                   } else {
                       break;
                   }
               }
               if (entry != null) {
                   this.available.remove(entry);
                   this.leased.add(entry);
                   onReuse(entry);
                   return entry;
               }
   
               // 创建新的连接
               // New connection is needed
               final int maxPerRoute = getMax(route);//获取当前路由最大并发数
               // Shrink the pool prior to allocating a new connection
               final int excess = Math.max(0, pool.getAllocatedCount() + 1 - maxPerRoute);
               if (excess > 0) {// 如果当前路由对应的连接池的连接超过最大路由并发数，获取到最后使用的一次连接，释放掉
                   for (int i = 0; i < excess; i++) {
                       final E lastUsed = pool.getLastUsed();
                       if (lastUsed == null) {
                           break;
                       }
                       lastUsed.close();
                       this.available.remove(lastUsed);
                       pool.remove(lastUsed);
                   }
               }
   
               // 尝试创建新的连接 
               if (pool.getAllocatedCount() < maxPerRoute) {//当前路由对应的连接池可用空闲连接数+当前路由对应的连接池已用连接数 < 当前路由对应的连接池最大并发数
                   final int totalUsed = this.leased.size();
                   final int freeCapacity = Math.max(this.maxTotal - totalUsed, 0);
                   if (freeCapacity > 0) {
                       final int totalAvailable = this.available.size();
                       if (totalAvailable > freeCapacity - 1) {//线程池中可用空闲连接数 > (线程池中最大连接数 - 线程池中已用连接数 - 1)
                           if (!this.available.isEmpty()) {
                               final E lastUsed = this.available.removeLast();
                               lastUsed.close();
                               final RouteSpecificPool<T, C, E> otherpool = getPool(lastUsed.getRoute());
                               otherpool.remove(lastUsed);
                           }
                       }
                       final C conn = this.connFactory.create(route);
                       entry = pool.add(conn);
                       this.leased.add(entry);
                       return entry;
                   }
               }
   
   
               boolean success = false;
               try {
                   pool.queue(future);
                   this.pending.add(future);
                   success = future.await(deadline);
               } finally {
                   // In case of 'success', we were woken up by the
                   // connection pool and should now have a connection
                   // waiting for us, or else we're shutting down.
                   // Just continue in the loop, both cases are checked.
                   pool.unqueue(future);
                   this.pending.remove(future);
               }
               // check for spurious wakeup vs. timeout
               if (!success && (deadline != null) &&
                   (deadline.getTime() <= System.currentTimeMillis())) {
                   break;
               }
           }
           throw new TimeoutException("Timeout waiting for connection");
       } finally {
           this.lock.unlock();
       }
   }
   ```

   

## HttpClient从连接池中获取连接流程图

![image-20231215101936554](images\image-20231215101936554.jpg)


package com.study.watcher;/* ━━━━━━如来保佑━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　┻　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━永无BUG━━━━━━
 * 图灵学院-悟空老师
 * 以往视频加小乔老师QQ：895900009
 * 悟空老师QQ：245553999
 */

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

public class ZookeeperWatcher2 implements Watcher {
   private String connectString="192.168.0.31:2181,192.168.0.32:2181,192.168.0.33:2181";

   private ZooKeeper zookeeper;

   public ZookeeperWatcher2() {
      try {
         this.zookeeper = new ZooKeeper(connectString, 5000,this);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }


   /***
    * 创建持久节点
    * @param path
    * @param data
    * @return
    */
   public String createPersistent(String path,String data){
      try {
         return  zookeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
      } catch (KeeperException e) {
         e.printStackTrace();
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
      return  null;
   }


   /***
    * 创建临时节点
    * @param path
    * @param data
    * @return
    */
   public String createEphemeral(String path,String data){
      try {
         return  zookeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
      } catch (KeeperException e) {
         e.printStackTrace();
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
      return  null;
   }

   /***
    * 更新信息
    * @param path
    * @return
    * @throws KeeperException
    * @throws InterruptedException
    */
   public String getData(String path,boolean watcher) throws KeeperException, InterruptedException {
      byte data[] = zookeeper.getData(path,watcher,null);
      data = (data == null)? "null".getBytes() : data;
      return new String(data);
   }


   /***
    * 更新信息
    * @param path
    * @param data
    * @return
    * @throws KeeperException
    * @throws InterruptedException
    */
   public Stat setData(String path, String data) throws KeeperException, InterruptedException {
      return zookeeper.setData(path, data.getBytes(), -1);
   }

   /***
    * 是否存在
    * @param path
    * @return
    * @throws KeeperException
    * @throws InterruptedException
    */
   public Stat exists(String path,boolean watcher) throws KeeperException, InterruptedException {
      return zookeeper.exists(path,watcher);

   }


   /***
    * 删除
    * @param path
    * @return
    * @throws KeeperException
    * @throws InterruptedException
    */
   public void delete(String path) throws KeeperException, InterruptedException {
      zookeeper.delete(path,-1);
   }
   /***
    * 删除
    * @param path
    * @return
    * @throws KeeperException
    * @throws InterruptedException
    */
   public void deleteRecursive(String path) throws KeeperException, InterruptedException {
      ZKUtil.deleteRecursive(zookeeper, path);
   }

   public void close() throws InterruptedException {
      zookeeper.close();
   }

   @Override
   public void process(WatchedEvent event) {
      // 连接状态
      Event.KeeperState keeperState = event.getState();
      // 事件类型
      Event.EventType eventType = event.getType();
      // 受影响的path
      String path = event.getPath();
      //step 1:
      //  System.out.println("连接状态:"+keeperState+",事件类型："+eventType+",受影响的path:"+path);

      //step:2
      try {
         if(null!=this.exists("/wukong",true)) {
            System.out.println("内容:"+ this.getData("/wukong", true));
         }
         System.out.println("连接状态:"+keeperState+",事件类型："+eventType+",受影响的path:"+path);
      } catch (Exception e) {
         e.printStackTrace();
      }
      System.out.println("--------------------");
   }
}

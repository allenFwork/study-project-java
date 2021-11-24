package com.study.znode;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

public class ZookeeperCrud {

    private ZooKeeper zooKeeper;

    public ZookeeperCrud(String url) {
        try {
            zooKeeper = new ZooKeeper(url, 5000, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 创建持久节点
    public String createPersistent(String path, String data) {
        try {
            return zooKeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 创建临时节点
    public String createEphemeral(String path, String data) {
        try {
            return zooKeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 查询节点的数据信息
    public String getData(String path) {
       byte data[] = new byte[0];
       try {
          data = zooKeeper.getData(path, false, null);
       } catch (KeeperException e) {
          e.printStackTrace();
       } catch (InterruptedException e) {
          e.printStackTrace();
       }
       data = (data == null) ? "null".getBytes() : data;
        return new String(data);
    }

    // 更新节点数据信息
    public Stat setData(String path, String data) {
       try {
          return zooKeeper.setData(path, data.getBytes(), -1);
       } catch (KeeperException e) {
          e.printStackTrace();
       } catch (InterruptedException e) {
          e.printStackTrace();
       }
       return null;
    }

    // 查询节点是否存在
    public Stat exists(String path) {
       try {
          return zooKeeper.exists(path, false);
       } catch (KeeperException e) {
          e.printStackTrace();
       } catch (InterruptedException e) {
          e.printStackTrace();
       }
       return null;
    }

    // 删除节点
    public void deleteNode(String path) {
       try {
          zooKeeper.delete(path, -1);
       } catch (InterruptedException e) {
          e.printStackTrace();
       } catch (KeeperException e) {
          e.printStackTrace();
       }
    }

    // 递归删除节点
    public void deleteRecursive(String path) {
       try {
          ZKUtil.deleteRecursive(zooKeeper, path);
       } catch (InterruptedException e) {
          e.printStackTrace();
       } catch (KeeperException e) {
          e.printStackTrace();
       }
    }

    // 关闭会话连接
    public void close() throws InterruptedException {
        zooKeeper.close();
    }
}

package com.study.zookeeper.client.znode;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * 对 zookeeper服务端 进行创建 修改 节点
 */
public class ZookeeperCrud {
    private ZooKeeper zooKeeper;

    public ZookeeperCrud(String zookeeperServerUrl) throws IOException {
        this.zooKeeper = new ZooKeeper(zookeeperServerUrl, 100000, null);
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
        try {
            byte[] bytes = zooKeeper.getData(path, false, null);
            bytes = (bytes == null) ? "null".getBytes() : bytes;
            return new String(bytes);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "null";
    }

    // 设置节点数据信息
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

    // 查看节点是否存在
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

    // 删除一个节点
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

    public void close() throws InterruptedException {
        zooKeeper.close();
    }

}

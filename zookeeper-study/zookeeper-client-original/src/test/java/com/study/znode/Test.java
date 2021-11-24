package com.study.znode;

import org.apache.zookeeper.KeeperException;

public class Test {

    public static void main(String[] args) throws KeeperException, InterruptedException {
        ZookeeperCrud zookeeperCrud = new ZookeeperCrud("192.168.33.4:2181,192.168.33.5:2181,192.168.33.6:2181");
        if (null != zookeeperCrud.exists("/superman"))
            zookeeperCrud.deleteNode("/superman");
        zookeeperCrud.createEphemeral("/superman", "abc");
        System.out.println(zookeeperCrud.getData("/superman"));
    }

}

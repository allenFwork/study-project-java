package com.study.mongo1.dao;


import java.util.List;

/**
 * mongodb 查询 dao 基类
 */
public interface Mongo1BasicDao<T> {

    /**
     * 插入一条 (如果id相同，插入失败)
     *
     * @param e
     */
    void insertOne(T e);

    /**
     * 批量插入
     *
     * @param list
     */
    void insertBatch(List<T> list);

    /**
     * 刪除
     *
     * @param e
     */
    long delete(T e);

}

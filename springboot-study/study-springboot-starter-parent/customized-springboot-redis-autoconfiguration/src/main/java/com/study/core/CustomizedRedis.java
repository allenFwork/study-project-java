package com.study.core;

/**
 * 接口类
 */
public interface CustomizedRedis {

    /**
     * set方法
     * @param key
     * @param value
     * @return
     */
    String set(String key, String value);

    /**
     * get操作
     * @param key
     * @return
     */
    String get(String key);

}

package com.study.lucene.service;

import com.study.lucene.pojo.ResultModel;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {

    @Override
    public ResultModel query(String queryString, String price, Integer page) {
        // 1.需要使用的对象封装
        // 2.根据查询关键字封装查询对象
        return null;
    }

}

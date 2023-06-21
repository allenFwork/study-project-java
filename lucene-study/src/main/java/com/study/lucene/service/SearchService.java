package com.study.lucene.service;

import com.study.lucene.pojo.ResultModel;

public interface SearchService {

    public ResultModel query(String queryString, String price, Integer page);

}

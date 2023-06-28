package com.study.solr.tokenizer;

import org.apache.lucene.analysis.util.CharTokenizer;

/**
 * 加号分词器，通过“+”进行分词
 */
public class PlusTokenizer extends CharTokenizer {

    // isTokenChar哪些字符是词；
    public boolean isTokenChar(int i) {
        // 将什么字符作为分隔符进行分词
        return i != '+';
    }
}

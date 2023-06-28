package com.study.solr.filter;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

import java.util.Map;

public class KeyWordsFilterFactory extends TokenFilterFactory {

    private String keyWord = "love";

    /**
     * @param args 过滤器参数
     */
    // 调用父类无参构造
    public KeyWordsFilterFactory(Map<String, String> args) throws IllegalAccessException {
        super(args);
        if (args == null) {
            throw new IllegalAccessException("必须传递keywords参数");
        }
        if (args.containsKey("keyWords")) {
            this.keyWord = args.get("keyWords");
        }
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return new KeyWordsFilter(tokenStream, keyWord);
    }

}

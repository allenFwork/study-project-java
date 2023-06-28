package com.study.solr.filter;

import org.apache.lucene.analysis.FilteringTokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;

public class KeyWordsFilter extends FilteringTokenFilter {

    // 接受过滤的此
    private String keyWord;
    // 获取当前词
    private final CharTermAttribute termAtt = (CharTermAttribute)this.addAttribute(CharTermAttribute.class);

    public KeyWordsFilter(TokenStream in, String keyWord) {
        super(in);
        this.keyWord = keyWord;
    }

    /**
     * true:  保留
     * false: 过滤
     *
     * @return
     * @throws IOException
     */
    @Override
    protected boolean accept() throws IOException {
        if (termAtt.toString().equals(keyWord)){
            return false;
        }
        return true;
    }

}

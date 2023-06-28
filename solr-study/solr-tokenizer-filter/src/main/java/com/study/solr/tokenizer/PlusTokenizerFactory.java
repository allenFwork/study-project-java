package com.study.solr.tokenizer;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;

import java.util.Map;

public class PlusTokenizerFactory extends TokenizerFactory {

    /**
     * 在schem配置文件中配置分词器的时候，指定参数args配置分词器时候的指定的参数
     * <analyzer>
     *   <tokenizer class="cn.itcast.tokenizer.PlausSignTokenizerFactory" mode="complex"/>
     * </analyzer>
     */
    public PlusTokenizerFactory(Map<String, String> args) {
        super(args);
    }

    @Override
    public Tokenizer create(AttributeFactory attributeFactory) {
        return new PlusTokenizer();
    }

}
package com.study.solr.function;

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.queries.function.FunctionValues;
import org.apache.lucene.queries.function.ValueSource;
import org.apache.lucene.queries.function.docvalues.StrDocValues;

import java.io.IOException;
import java.util.Map;

public class ConcatValueSource extends ValueSource {

    // 使用构造方法接收ConcatValueSourceParser解析获取的参数
    private ValueSource valueSource1;
    private ValueSource valueSource2;

    public ConcatValueSource(ValueSource valueSource1, ValueSource valueSource2) {
        this.valueSource1 = valueSource1;
        this.valueSource2 = valueSource2;
    }

    // 返回arg1和arg2计算结果
    public FunctionValues getValues(Map map, LeafReaderContext leafReaderContext) throws IOException {
        // 获取形参的值
        final FunctionValues arg1 = valueSource1.getValues(map, leafReaderContext); // 域名
        final FunctionValues arg2 = valueSource1.getValues(map, leafReaderContext); // 域名
        // 返回一个字符串的结果
        return new StrDocValues(this) {
            @Override
            public String strVal(int doc) throws IOException {
                // 根据参数从文档中获取值，拼接两个域名对应的值
                return arg1.strVal(doc) + arg2.strVal(doc);
            }
        };
    }

    public boolean equals(Object o) {
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String description() {
        return null;
    }

}
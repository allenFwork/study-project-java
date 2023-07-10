package com.study.solr.function;

import org.apache.lucene.queries.function.ValueSource;
import org.apache.solr.search.FunctionQParser;
import org.apache.solr.search.SyntaxError;
import org.apache.solr.search.ValueSourceParser;

/**
 * 用来解析函数调用语法，并且将解析的参数返回给第一个类处理
 */
public class ConcatValueSourceParser extends ValueSourceParser {

    // 对函数调用语法进行解析，获取参数，将参数封装到ValueSource，返回函数执行结果
    @Override
    public ValueSource parse(FunctionQParser functionQParser) throws SyntaxError {
        // 1.获取函数调用参数,由于参数类型可能是字符串，int类型，域名，所以封装成ValueSource
        // 1.1获取第一个参数封装对象
        ValueSource valueSource1 = functionQParser.parseValueSource();
        // 1.2获取第二个参数封装对象
        ValueSource valueSource2 = functionQParser.parseValueSource();

        // 返回函数的执行结果
        return new ConcatValueSource(valueSource1, valueSource2);
    }

}
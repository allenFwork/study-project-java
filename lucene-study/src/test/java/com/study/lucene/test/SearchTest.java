package com.study.lucene.test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * 测试搜索过程
 */
public class SearchTest {

    @Test
    public void testIndexSearch() throws ParseException, IOException {

        // 1.创建分词器：对搜索的关键词进行分词使用（注：此处的分词器和创建索引的时候使用的分词器必须一摸一样）
        Analyzer analyzer = new StandardAnalyzer();

        // 2.创建查询对象
        // 第一个参数：默认查询域；第二个参数：分词器
        QueryParser queryParser = new QueryParser("name", analyzer);

        // 3.设置搜索关键词
        /**
         * 查询的关键字中：
         *      如果没有域名（"华为手机"），那么就是使用上面设置的默认域名（搜索域中的域名），即 name域名；
         *      如果设置了域名（"brandName:华为手机"），那么就会查询 brandName域名中有 “华为手机” 的数据
         */
        Query query = queryParser.parse("华为");

        // 4.创建Directory目录对象，指定索引库的位置
        Directory directory = FSDirectory.open(Paths.get("D:\\LuceneDir"));

        // 5.创建输入对象
        IndexReader indexReader = DirectoryReader.open(directory);

        // 6.创建搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        // 7.搜索，并返回结果
        TopDocs topDocs = indexSearcher.search(query, 10); // 第二个参数：返回条数用于展示，分页使用
        System.out.println("结果集的数量：" + topDocs.totalHits); // 获取查询到的结果集的总数，并打印出来

        // 8.获取结果集
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        // 9.遍历结果集
        if (scoreDocs != null) {
            for (ScoreDoc scoreDoc : scoreDocs) {
                // 获取查询到的文档唯一标识: 文档id，这个id是lucene在创建文档的时候自动分配的
                int docID = scoreDoc.doc;
                // 通过文档id，读取文档
                Document document = indexSearcher.doc(docID);
                System.out.println("===============================================");
                // 通过域名，从文档中读取域值
                System.out.println("id = " + document.get("id"));
                System.out.println("name = " + document.get("name"));
                System.out.println("price = " + document.get("price"));
                System.out.println("brandName = " + document.get("brandName"));
                System.out.println("image = " + document.get("image"));
                System.out.println("categoryName = " + document.get("categoryName"));
            }
        }
        // 10.释放资源

    }

    /**
     * 文本内容搜索查询
     *
     * @throws Exception
     */
    @Test
    public void textSearch() throws Exception {

        // 创建分词器
        Analyzer analyzer = new IKAnalyzer();
        // 创建搜索解析器，第一个参数：默认Field域，第二个参数：分词器
        QueryParser queryParser = new QueryParser("name", analyzer);

        // 1.创建Query搜索对象
        Query query = queryParser.parse("华为手机");
//        Query query = queryParser.parse("华为 AND 手机"); // 包含 “华为”、“手机” 取交集
//        Query query = queryParser.parse("华为 OR 手机");  // 包含 “华为”、“手机” 取并集

        // 2.创建Directory流对象,声明索引库位置
        Directory directory = FSDirectory.open(Paths.get("D:\\LuceneDir"));
        // 3.创建索引读取对象IndexReader
        IndexReader reader = DirectoryReader.open(directory);
        // 4.创建索引搜索对象
        IndexSearcher searcher = new IndexSearcher(reader);
        // 5.使用索引搜索对象，执行搜索，返回结果集TopDocs
        // 第一个参数：搜索对象，第二个参数：返回的数据条数，指定查询结果最顶部的n条数据返回
        TopDocs topDocs = searcher.search(query, 50);
        System.out.println("查询到的数据总条数是：" + topDocs.totalHits);
        // 获取查询结果集
        ScoreDoc[] docs = topDocs.scoreDocs;
        // 6.解析结果集
        for (ScoreDoc scoreDoc : docs) {
            // 获取文档
            int docID = scoreDoc.doc;
            Document doc = searcher.doc(docID);
            System.out.println("=============================");
            System.out.println("docID:" + docID);
            System.out.println("id:" + doc.get("id"));
            System.out.println("name:" + doc.get("name"));
            System.out.println("price:" + doc.get("price"));
            System.out.println("brandName:" + doc.get("brandName"));
            System.out.println("image:" + doc.get("image"));
        }
        // 7.释放资源
        reader.close();

    }

    /**
     * 数值范围搜索查询
     *
     * @throws Exception
     */
    @Test
    public void numberSearch() throws Exception {

        // 创建分词器
        Analyzer analyzer = new IKAnalyzer();
        // 创建搜索解析器，第一个参数：默认Field域，第二个参数：分词器
        QueryParser queryParser = new QueryParser("name", analyzer);

        // 1.创建Query搜索对象, 查询价格在 100 到 1000 地数据
        Query query = IntPoint.newRangeQuery("price", 100, 1000);

        // 2.创建Directory流对象,声明索引库位置
        Directory directory = FSDirectory.open(Paths.get("D:\\LuceneDir"));
        // 3.创建索引读取对象IndexReader
        IndexReader reader = DirectoryReader.open(directory);
        // 4.创建索引搜索对象
        IndexSearcher searcher = new IndexSearcher(reader);
        // 5.使用索引搜索对象，执行搜索，返回结果集TopDocs
        // 第一个参数：搜索对象，第二个参数：返回的数据条数，指定查询结果最顶部的n条数据返回
        TopDocs topDocs = searcher.search(query, 50);
        System.out.println("查询到的数据总条数是：" + topDocs.totalHits);
        // 获取查询结果集
        ScoreDoc[] docs = topDocs.scoreDocs;
        // 6.解析结果集
        for (ScoreDoc scoreDoc : docs) {
            // 获取文档
            int docID = scoreDoc.doc;
            Document doc = searcher.doc(docID);
            System.out.println("=============================");
            System.out.println("docID:" + docID);
            System.out.println("id:" + doc.get("id"));
            System.out.println("name:" + doc.get("name"));
            System.out.println("price:" + doc.get("price"));
            System.out.println("brandName:" + doc.get("brandName"));
            System.out.println("image:" + doc.get("image"));
        }
        // 7.释放资源
        reader.close();

    }

    /**
     * 组合查询
     *
     * @throws Exception
     */
    @Test
    public void combinationSearch() throws Exception {

        // 创建分词器
        Analyzer analyzer = new IKAnalyzer();

        // 1.创建Query搜索对象, 查询价格在 100 到 1000 地数据
        Query query1 = IntPoint.newRangeQuery("price", 100, 1000);

        // 创建搜索解析器，第一个参数：默认Field域，第二个参数：分词器
        QueryParser queryParser = new QueryParser("name", analyzer);
        Query query2 = queryParser.parse("华为手机");

        // 创建布尔查询对象（组合查询对象）
        BooleanQuery.Builder queryBuiler = new BooleanQuery.Builder();
        /**
         * BooleanClause.Occur.MUST    ：相当于AND, 并且的关系
         * BooleanClause.Occur.SHOULD  ：相当于OR,  并且的关系
         * BooleanClause.Occur.MUST_NOT：相当于非
         * 注意：如果查询条件都是MUST_NOT，或者只有一个查询条件，然后这一个查询条件是MUST_NOT，则查询不出任何数据。
         */
        queryBuiler.add(query1, BooleanClause.Occur.MUST);
        queryBuiler.add(query2, BooleanClause.Occur.MUST);
        Query query = queryBuiler.build();

        // 2.创建Directory流对象,声明索引库位置
        Directory directory = FSDirectory.open(Paths.get("D:\\LuceneDir"));
        // 3.创建索引读取对象IndexReader
        IndexReader reader = DirectoryReader.open(directory);
        // 4.创建索引搜索对象
        IndexSearcher searcher = new IndexSearcher(reader);
        // 5.使用索引搜索对象，执行搜索，返回结果集TopDocs
        // 第一个参数：搜索对象，第二个参数：返回的数据条数，指定查询结果最顶部的n条数据返回
        TopDocs topDocs = searcher.search(query, 50);
        System.out.println("查询到的数据总条数是：" + topDocs.totalHits);
        // 获取查询结果集
        ScoreDoc[] docs = topDocs.scoreDocs;
        // 6.解析结果集
        for (ScoreDoc scoreDoc : docs) {
            // 获取文档
            int docID = scoreDoc.doc;
            Document doc = searcher.doc(docID);
            System.out.println("=============================");
            System.out.println("docID:" + docID);
            System.out.println("id:" + doc.get("id"));
            System.out.println("name:" + doc.get("name"));
            System.out.println("price:" + doc.get("price"));
            System.out.println("brandName:" + doc.get("brandName"));
            System.out.println("image:" + doc.get("image"));
        }
        // 7.释放资源
        reader.close();

    }

}

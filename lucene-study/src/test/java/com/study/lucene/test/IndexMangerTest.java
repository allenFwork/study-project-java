package com.study.lucene.test;

import com.study.lucene.dao.SkuDao;
import com.study.lucene.dao.SkuDaoImpl;
import com.study.lucene.pojo.Sku;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 索引库维护
 */
public class IndexMangerTest {

    /**
     * 创建索引库
     */
    @Test
    public void createIndexTest() throws IOException {

        // 1. 采集数据
        SkuDao skuDao = new SkuDaoImpl();
        List<Sku> skuList = skuDao.querySkuList();

        // 文档集合
        List<Document> documentList = new ArrayList<>();
        for (Sku sku : skuList) {
            // 2.创建文档对象
            Document document = new Document();
            // 创建域对象，并放入到文档对象中
            // new TextField(String, String, Store) 第一个参数：域名；第二个参数：域值；第三个参数：是否存储
            /**
             * 是否分词：否，因为主键分此后没有意义
             * 是否索引：是，如果根据id主键查询，就必须索引
             * 是否存储：是，因为主键id比较特殊，可以确定唯一的一条数据，在业务上一般有重要作用，所以存储
             */
            document.add(new StringField("id", sku.getId(), Field.Store.YES));

            /**
             * 是否分词：是，因为名称字段需要查询，并且分词后有意义，所以需要分词
             * 是否索引：是，因为需要根据名称字段查询
             * 是否存储：是，因为页面需要展示商品名称，所以需要存储
             */
            document.add(new TextField("name", sku.getName(), Field.Store.YES));

            /**
             * 是否分词：是，因为Lucene底层算法规定，如果根据价格范围查询，必须分词
             * 是否索引：是，需要根据价格进行范围查询，所以需要索引
             * 是否存储：是，因为页面需要展示价格，所以需要存储
             */
            document.add(new IntPoint("price", sku.getPrice()));    // YYN：分词、索引、不存储
            document.add(new StoredField("price", sku.getPrice())); // NNY：不分词、不索引、存储

            /**
             * 是否分词：否，因为不查询，所以不索引，因为不索引所以不分词
             * 是否索引：否，因为不需要根据图片地址查询
             * 是否存储：是，因为页面需要展示商品的图片
             */
            document.add(new StoredField("image", sku.getImage()));

            /**
             * 是否分词：否，因为分类是一个专有名词，是一个整体，所以不分词
             * 是否索引：是，因为需要根据分类查询
             * 是否存储：是，因为页面需要展示商品的分类
             */
            document.add(new StringField("categoryName", sku.getCategoryName(), Field.Store.YES));

            /**
             * 是否分词：否，因为品牌是一个专有名词，是一个整体，所以不分词
             * 是否索引：是，因为需要根据品牌查询
             * 是否存储：是，因为页面需要展示品牌
             */
            document.add(new StringField("brandName", sku.getBrandName(), Field.Store.YES));
            // 将文档对象放入到文档集合中
            documentList.add(document);
        }

        // 3.创建分词器, StandardAnalyzer标准分词器,对英文分词效果好,对中文是单字分词,也就是一个字就认为是一个词
        Analyzer analyzer = new StandardAnalyzer();
//        Analyzer analyzer = new IKAnalyzer();

        // 4.创建Directory目录对象，目录对象表示索引库的位置
        Directory directory = FSDirectory.open(Paths.get("D:\\LuceneDir"));

        // 5. 创建IndexWriterConfig对象
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);

        // 6. 创建IndexWriter输出流对象，指定输出位置和使用的config初始化对象
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

        // 7. 写入文档到索引库
        for (Document document : documentList) {
            indexWriter.addDocument(document);
        }

        // 8.释放资源
        indexWriter.close();
    }

    /**
     * 更新索引库
     *
     * @throws IOException
     */
    @Test
    public void updateIndexTest() throws IOException {

        // 创建文档对象
        Document document = new Document();

        document.add(new StringField("id", "100000003145", Field.Store.YES));
        document.add(new TextField("name", "修改测试", Field.Store.YES));
        document.add(new IntPoint("price", 123));    // YYN：分词、索引、不存储
        document.add(new StoredField("price", 123)); // NNY：不分词、不索引、存储
        document.add(new StoredField("image", "XXX.jpg"));
        document.add(new StringField("categoryName", "电话", Field.Store.YES));
        document.add(new StringField("brandName", "allen品牌", Field.Store.YES));


        // 3.创建分词器, StandardAnalyzer标准分词器,对英文分词效果好,对中文是单字分词,也就是一个字就认为是一个词
        Analyzer analyzer = new StandardAnalyzer();

        // 4.创建Directory目录对象，目录对象表示索引库的位置
        Directory directory = FSDirectory.open(Paths.get("D:\\LuceneDir"));

        // 5. 创建IndexWriterConfig对象
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);

        // 6. 创建IndexWriter输出流对象，指定输出位置和使用的config初始化对象
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

        // 7. 修改，第一个参数：修改条件；第二个参数：修改成的内容
        indexWriter.updateDocument(new Term("id", "100000003145"), document);

        // 8.释放资源
        indexWriter.close();
    }

    /**
     * 删除索引库
     * @throws IOException
     */
    @Test
    public void deleteIndexTest() throws IOException {

        // 3.创建分词器, StandardAnalyzer标准分词器,对英文分词效果好,对中文是单字分词,也就是一个字就认为是一个词
        Analyzer analyzer = new StandardAnalyzer();

        // 4.创建Directory目录对象，目录对象表示索引库的位置
        Directory directory = FSDirectory.open(Paths.get("D:\\LuceneDir"));

        // 5. 创建IndexWriterConfig对象
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);

        // 6. 创建IndexWriter输出流对象，指定输出位置和使用的config初始化对象
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

        // 7. 删除，第一个参数：修改条件
//        indexWriter.deleteDocuments(new Term("id", "100000003145"));
        // 删除所有内容
        indexWriter.deleteAll();

        // 8.释放资源
        indexWriter.close();
    }

}

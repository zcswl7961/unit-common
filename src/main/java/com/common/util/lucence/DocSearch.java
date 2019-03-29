//package com.common.util.lucence;
//
//import org.apache.lucene.document.Document;
//import org.apache.lucene.index.DirectoryReader;
//import org.apache.lucene.index.IndexReader;
//import org.apache.lucene.index.Term;
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.ScoreDoc;
//import org.apache.lucene.search.TermQuery;
//import org.apache.lucene.search.TopDocs;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//
//import java.io.File;
//import java.io.IOException;
//
///**
// *
// * @author zhoucg
// * @date 2018-12-05
// * 搜索引擎查询
// */
//public class DocSearch {
//
//    public static void main(String[] args) throws IOException{
//        // 打开索引库
//        // 找到索引库的位置
//        Directory directory = FSDirectory.open(new File("G:\\lucence"));
//        IndexReader indexReader = DirectoryReader.open(directory);
//        // 创建一个IndexSearcher对象
//        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
//        // 创建一个查询对象
//        TermQuery query = new TermQuery(new Term("name","新建文本文档.txt"));
//        // 执行查询
//        // 返回的最大值，在分页的时候使用
//        TopDocs topDocs = indexSearcher.search(query, 5);
//        // 取查询结果总数量
//        System.out.println("总共的查询结果：" + topDocs.totalHits);
//        // 查询结果，就是documentID列表
//        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
//
//        for (ScoreDoc scoreDoc : scoreDocs) {
//            // 取对象document的对象id
//            int docID = scoreDoc.doc;
//
//            // 相关度得分
//            float score = scoreDoc.score;
//
//            // 根据ID去document对象
//            Document document = indexSearcher.doc(docID);
//
//            System.out.println("相关度得分：" + score);
//            System.out.println("");
//            System.out.println(document.get("name"));
//            System.out.println("");
//            // 另外的一种使用方法
//            System.out.println(document.getField("content").stringValue());
//            System.out.println(document.get("path"));
//            System.out.println();
//            System.out.println("=======================");
//        }
//
//        indexReader.close();
//    }
//}

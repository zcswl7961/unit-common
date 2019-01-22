package com.common.util.lucence;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author zhoucg
 * @date 2018-12-05
 * apache Lucence 搜索引擎使用
 *
 * apache Lucence搜索引擎的基本流程：
 *   1）创建文档对象：为每个文件对应的创建一个Document对象。把文件的属性都保存到document对象中。
 *      需要为每个属性创建一个field（在lucene中叫做域），把field添加到文档对象中。每个document都有一个唯一的编号。
 *   2）分析文档：针对document中的域进行分析，例如分析文件名、文件内容两个域。先把文件内容域中的字符串根据空格进行分词，
 *      把单词进行统一转换成小写。把没有意义的单词叫做停用词。把停用词从词汇列表中去掉。去掉标点符号。最终得到一个关键词列表。
 *      每个关键词叫做一个Term。Term中包含关键词及其所在的域，不同的域中相当的单词是不同的term。
 *   3）创建索引：索引：为了提高查询速度的一个数据结构。在关键词列表上创建一个索引；
 *      把索引和文档对象写入索引库，并记录关键词和文档对象的对应关系。
 *
 *
 *   lucence-analyzers-common-4.0.0.jar    这里包含了各种语言的词法分析其，用于对文件内容进行关键字切分，提取
 *   Lucene-highlighter-4.0.0.jar，这个jar包主要用于搜索出的内容高亮显示。
 *   Lucene-queryparser-4.0.0.jar，提供了搜索相关的代码，用于各种搜索，比如模糊搜索，范围搜索，等等。
 *
 *   索引创建
 */
public class IndexRepository {


    public static void main(String[] args) throws IOException{
        Directory directory = FSDirectory.open(new File("G:\\lucence"));

        File files = new File("G:\\listFile");

        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);

        IndexWriter indexWriter = new IndexWriter(directory,config);

        for (File f : files.listFiles()) {
            // 文件名
            String fileName = f.getName();
            // 文件内容
            @SuppressWarnings("deprecation")
            String fileContent = FileUtils.readFileToString(f);
            // 文件路径
            String filePath = f.getPath();
            // 文件大小
            long fileSize = FileUtils.sizeOf(f);

            // 创建一个Document对象
            Document document = new Document();
            // 向Document对象中添加域信息
            // 参数：1、域的名称；2、域的值；3、是否存储；
            Field nameField = new TextField("name", fileName, Field.Store.YES);
            Field contentField = new TextField("content", fileContent , Field.Store.YES);
            // storedFiled默认存储
            Field pathField = new StoredField("path", filePath);
            Field sizeField = new LongField("size", fileSize, Field.Store.YES);
            // 将域添加到document对象中
            document.add(nameField);
            document.add(contentField);
            document.add(pathField);
            document.add(sizeField);
            // 将信息写入到索引库中
            indexWriter.addDocument(document);
        }

        indexWriter.close();

    }
}

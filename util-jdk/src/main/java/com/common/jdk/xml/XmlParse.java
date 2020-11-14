package com.common.jdk.xml;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * jdk中对于xml文件的解析，已经提供了org.w3c.dom、org.xml.sax 和javax.xml.parsers 原生的支持
 * https://blog.csdn.net/wzyzzu/article/details/51880578
 *
 * （1）org.w3c.dom　W3C推荐的用于XML标准规划文档对象模型的接口。
 * （2）org.xml.sax　用于对XML进行语法分析的事件驱动的XML简单API（SAX）
 * （3）javax.xml.parsers解析器工厂工具，程序员获得并配置特殊的特殊语法分析器。
 * @author zhoucg
 * @date 2020-11-09 14:30
 */
public class XmlParse {


    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbd = builderFactory.newDocumentBuilder();

        Document doc = dbd.parse(new FileInputStream("D:\\IDEA_WorkProject\\unit-common\\util-jdk\\src\\main\\resources\\book.xml"));

        // 通过XML获取book的authors的author子节点列表
        XPathFactory f = XPathFactory.newInstance();
        XPath path = f.newXPath();
        NodeList authors= (NodeList) path.evaluate("book/authors/author", doc, XPathConstants.NODESET);
        System.out.println(authors.getLength());
        //遍历取到的元素
        if(authors!=null){
            for(int i=0;i<authors.getLength();i++){
                Node author      = authors.item(i);
                int n = i + 1;
                System.out.print(n+".   名字:"+ author.getNodeName() );
                System.out.println();
            }
        }

        //获得book的authors的第一个子节点,注意NODESET和NODE的区别
        Node author= (Node) path.evaluate("book/authors/author", doc,XPathConstants.NODE);
        System.out.println("   名称："+author.getNodeName());
        System.out.println("   内容："+author.getTextContent());//如果存在内容则返回内容，不存在则返回空
        //获取节点的属性
        NamedNodeMap attr =  author.getAttributes();
        System.out.println("   该节点的属性个数"+attr.getLength());
        //遍历元素的属性
        if(attr!=null){
            for(int i=0;i<attr.getLength();i++){
                int n = i + 1;
                System.out.print("   属性"+n+"   名称:"+attr.item(i).getNodeName());
                System.out.print("   值:"+attr.item(i).getNodeValue());
                System.out.print("   类型:"+attr.item(i).getNodeType());
                System.out.println();
            }
        }

    }
}

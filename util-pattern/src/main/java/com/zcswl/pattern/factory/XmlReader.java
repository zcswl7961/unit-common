package com.zcswl.pattern.factory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * @author zhoucg
 * @date 2019-12-10 15:53
 */
public class XmlReader {

    public static Object getObject() {
        try{

            DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=factory.newDocumentBuilder();

            //Document doc = builder.parse(new File("config1.xml"));
            Document doc = builder.parse(XmlReader.class.getClassLoader().getResource("config1.xml").getFile());

            //获取包含类名的文本节点
            NodeList n1 = doc.getElementsByTagName("className");
            Node classNode = n1.item(0).getFirstChild();
            String cName="com.zcswl.pattern.factory."+classNode.getNodeValue();

            Class clazz = Class.forName(cName);
            return clazz.newInstance();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

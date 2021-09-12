package com.zcswl.mybatis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author xingyi
 * @date 2021/8/15 9:43 上午
 */
public class Connector {

    /**
     * haracterEncoding -> utf8
     *         "useInformationSchema" -> "true"
     *         "user" -> "root"
     *         "maxReconnects" -> "3"
     *         "tinyInt1isBit" -> "false"
     *         "useUnicode" -> "true"
     *         "nullCatalogMeansCurrent" -> "false"
     *         "password" -> "123456"
     *         "connectTimeout" -> "10000"
     *         "autoReconnect" -> "true"
     * @param args
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("数据库驱动加载成功");
        // useUnicode=true&characterEncoding=utf-8&useSSL=false
        String url="jdbc:mysql://192.168.1.196:3306";
        //如果不加useSSL=false就会有警告，由于jdbc和mysql版本不同，有一个连接安全问题

        String user="root";
        String passWord="123456";
        //Connection对象引的是java.sql.Connection包
        com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("useSSL","false");
        properties.setProperty("password", passWord);
        properties.setProperty("haracterEncoding","utf-8");
        properties.setProperty("useInformationSchema","true");
        properties.setProperty("maxReconnects","3");
        properties.setProperty("tinyInt1isBit","false");
        properties.setProperty("useUnicode","true");
        properties.setProperty("nullCatalogMeansCurrent","false");
        properties.setProperty("connectTimeout","10000");
        properties.setProperty("autoReconnect","true");




        Connection connect = driver.connect(url, properties);
        System.out.println("已成功的与数据库MySQL建立连接！！" + connect);
    }
}

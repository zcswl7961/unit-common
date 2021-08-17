package com.zcswl.presto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author xingyi
 * @date 2021/8/17 2:02 下午
 */
public class PrestoDriverManager {

    /**
     * presto 客户端jdbc配置
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.facebook.presto.jdbc.PrestoDriver");

        Connection connection = DriverManager.getConnection("jdbc:presto://127.0.0.1:8080/mysqllocal/test","12",null);  ;
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("show tables");
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
        rs.close();
        connection.close();
    }
}

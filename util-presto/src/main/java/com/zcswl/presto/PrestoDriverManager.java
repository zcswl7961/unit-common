package com.zcswl.presto;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
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

        Connection connection = DriverManager.getConnection("jdbc:trino://127.0.0.1:8081","root",null);  ;
        DatabaseMetaData metaData = connection.getMetaData();
        System.out.println(metaData);
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("show catalogs");
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
        ResultSet resultSet = stmt.executeQuery("show schemas from hive");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }

        ResultSet resultSet2 = stmt.executeQuery("show tables from hive.yunchuan");
        while (resultSet2.next()) {
            System.out.println(resultSet2.getString(1));

        }

        ResultSet resultSet1 = stmt.executeQuery("desc hive.tagtest0104.\"tagtest0104_donggroup052520_grouptagsupdate_dynamic$partitions\"");
        while (resultSet1.next()) {
            System.out.println(resultSet1.getString(1));
        }



    }
}

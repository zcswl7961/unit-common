import com.mysql.cj.jdbc.MysqlSavepoint;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
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

        test();
        Connection conn = null;
        System.out.println("数据库驱动加载成功");
        // useUnicode=true&characterEncoding=utf-8&useSSL=false
        String url="jdbc:mysql://127.0.0.1:3306";
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
        int majorVersion = driver.getMajorVersion();
        int majorVersion1 = driver.getMajorVersion();
        System.out.println("已成功的与数据库MySQL建立连接！！" + connect);

        // 本地事务
        // SavePoint
        // https://vimsky.com/examples/detail/java-class-com.mysql.jdbc.ConnectionImpl.ExceptionInterceptorChain.html
        // mysql的事务是通过redo.log实现的
        connect.setAutoCommit(false);
        try {
            // 执行本地事物方法
            connect.commit();
        } catch (Exception e) {
            connect.rollback();
        } finally {
            connect.close();
        }

    }


    public static void test() throws SQLException {
        //String jdbcUrl = "jdbc:mysql://23c497u939.51vip.biz:55860/idatatesta?useUnicode=true&queryTimeout=2400&characterEncoding=utf-8&tinyInt1isBit=false&useSSL=false&serverTimezone=Asia/Shanghai&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true";
        String jdbcUrl = "jdbc:mysql://23c497u939.51vip.biz:55860/idatatesta?useSSL=false&useUnicode=true&characterEncoding=utf-8&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai";
        String username = "acc";
        String password = "!@#QWEasdzxc";

        String processSql = "CREATE PROCEDURE xxx() BEGIN\n" +
                "  DROP TABLE\n" +
                "  IF\n" +
                "    EXISTS idatatesta.temp_data_24c92b71;\n" +
                "  set @val_0 =(\n" +
                "    SELECT\n" +
                "      count( 1 ) AS null_count \n" +
                "    FROM\n" +
                "      `idatatesta`.`user` \n" +
                "    WHERE\n" +
                "    NAME IS NULL \n" +
                "    );\n" +
                "  CREATE TABLE\n" +
                "  IF\n" +
                "    NOT EXISTS idatatesta.temp_data_24c92b71 (\n" +
                "       `tenant_id` INT ( 11 ) NOT NULL DEFAULT '0',\n" +
                "     `monitor_id` INT ( 11 ) NOT NULL,\n" +
                "       `rule_id` INT ( 11 ) NOT NULL,\n" +
                "       `record_id` INT ( 11 ) NOT NULL,\n" +
                "       `val` VARCHAR ( 255 )  \n" +
                "    ) ENGINE = INNODB DEFAULT CHARSET = utf8;\n" +
                "  insert INTO idatatesta.temp_data_24c92b71 ( tenant_id, monitor_id, rule_id, record_id, val )\n" +
                "  VALUES\n" +
                "   ( 57, 507, 1623, 13819, @val_0 );\n" +
                "  drop TABLE\n" +
                "  IF\n" +
                "    EXISTS `idatatesta`.`dq_monitor_24c92b71`;\n" +
                "  CREATE TABLE `idatatesta`.`dq_monitor_24c92b71` (\n" +
                "    `id` INT ( 11 ) UNSIGNED NOT NULL COMMENT '编号',\n" +
                "    `name` VARCHAR ( 255 ) DEFAULT NULL COMMENT '姓名',\n" +
                "     `create_date` datetime DEFAULT NULL COMMENT '创建时间',\n" +
                "     `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改>时间',\n" +
                "     `sex` VARCHAR ( 4 ) DEFAULT NULL COMMENT '0:女，1:男' \n" +
                "  ) ENGINE = INNODB DEFAULT CHARSET = utf8 COMMENT = '用户表';\n" +
                "  ALTER TABLE `idatatesta`.`dq_monitor_24c92b71` ADD `dq_job_id` VARCHAR ( 100 );\n" +
                "  ALTER TABLE `idatatesta`.`dq_monitor_24c92b71` ADD `dq_rule_id` INT;\n" +
                "  ALTER TABLE `idatatesta`.`dq_monitor_24c92b71` ADD cyc_time datetime;\n" +
                "  ALTER TABLE `idatatesta`.`dq_monitor_24c92b71` ADD check_column VARCHAR ( 100 );\n" +
                "  INSERT INTO `idatatesta`.`dq_monitor_24c92b71` SELECT\n" +
                "  *,\n" +
                "  '24c92b71',\n" +
                "  1623,\n" +
                "  '2021-12-24 13:52:26',\n" +
                "  'name' \n" +
                "  FROM\n" +
                "    `idatatesta`.`user` \n" +
                "  WHERE\n" +
                "  NAME IS NULL;\n" +
                "end;";

        Connection connection  = DriverManager.getConnection(jdbcUrl, username, password);
        Statement statement = connection.createStatement();

        statement.execute(processSql);


        //调用存储过程
        String procCall = "call xxx()";
        CallableStatement callableStatement = connection.prepareCall(procCall);
        callableStatement.execute();
    }
}

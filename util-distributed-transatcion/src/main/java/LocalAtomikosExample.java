
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.jdbc.AtomikosDataSourceBean;

import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * JDBC2.0规范定义支持分布式事务的jdbc driver需要实现：javax.sql.XAConnection、javax.sql.XADataSource接口  RM
 *
 * TM 需要支持实现UserTransaction、TransactionManager、Transaction、TransactionSynchronizationRegistry、
 * Synchronization、Xid接口，通过与XAResource接口交互来实现分布式事务。此外，TM厂商如果要支持跨应用的分布式事务，那么还要实现JTS规范定义的接口。
 *
 * 常见的TM提供者包括我们前面提到的application server，包括:jboss、ejb server、weblogic等，以及一些以第三方类库形式提供事务管理器功能的jotm、Atomikos。
 *
 * DTP分布式事务模型定义了对应的核心节点：RM(资源管理器),  AP(应用程序),  TM(事务管理器)
 *
 * @author zhoucg
 * @date 2021-01-14 15:19
 */
public class LocalAtomikosExample {


    public static void main(String[] args) {
        AtomikosDataSourceBean dataSourceBean1 = createAtomikosDataSourceBean("db1");
        AtomikosDataSourceBean dataSourceBean2 = createAtomikosDataSourceBean("db2");

        Connection conn1 = null;
        Connection conn2 = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        // TM
        UserTransaction userTransaction = new UserTransactionImp();

        try {
            // 开启事务
            userTransaction.begin();

            // 执行db1上的sqla
            conn1 = dataSourceBean1.getConnection();
            ps1 = conn1.prepareStatement("INSERT into t_order(user_id, status) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps1.setInt(1, 1000);
            ps1.setString(2,"INIT_TWO");
            ps1.executeUpdate();
            ResultSet generatedKeys = ps1.getGeneratedKeys();
            int userId = -1;
            while (generatedKeys.next()) {
                // 获取自定生成的userId
                userId = generatedKeys.getInt(1);
            }

            // 模拟异常 ，直接进入catch代码块，2个都不会提交

            // 执行db2上的sql
            conn2 = dataSourceBean2
                    .getConnection();
            ps2 = conn2.prepareStatement("INSERT into t_order(user_id, status) VALUES (?, ?)");
            ps2.setInt(1, userId);
            ps2.setString(2, "LOCAL");
            ps2.executeUpdate();

            // 两阶段提交
            userTransaction.commit();
        } catch (Exception e) {
            try {
                e.printStackTrace();
                userTransaction.rollback();
            } catch (SystemException systemException) {
                systemException.printStackTrace();
            }
        } finally {
            try {
                ps1.close();
                ps2.close();
                conn1.close();
                conn2.close();
                dataSourceBean1.close();
                dataSourceBean2.close();
            } catch (Exception ignore) {
            }
        }


    }


    private static AtomikosDataSourceBean createAtomikosDataSourceBean(String dbName) {
        // 连接池基本属性
        Properties p = new Properties();
        p.setProperty("url", "jdbc:mysql://localhost:3306/" + dbName);
        p.setProperty("user", "root");
        p.setProperty("password", "123456");

        // 使用AtomikosDataSourceBean封装com.mysql.jdbc.jdbc2.optional.MysqlXADataSource
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        //atomikos要求为每个AtomikosDataSourceBean名称，为了方便记忆，这里设置为和dbName相同
        ds.setUniqueResourceName(dbName);
        ds.setXaDataSourceClassName("com.mysql.cj.jdbc.MysqlXADataSource");
        // ds.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
        ds.setXaProperties(p);
        return ds;
    }

}

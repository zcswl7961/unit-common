package com.zcswl.mybatis;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/**
 * @author zhoucg
 * @date 2020-11-05 10:31
 */
public class SqlParse {

//    public static void main(String[] args) {
//        SQL sql = new SQL();
////        sql.SELECT("P.ID, P.USERNAME, P.PASSWORD, P.FULL_NAME");
////        sql.SELECT("P.LAST_NAME, P.CREATED_ON, P.UPDATED_ON");
////        sql.FROM("PERSON P");
////        sql.FROM("ACCOUNT A");
////        sql.INNER_JOIN("DEPARTMENT D on D.ID = P.DEPARTMENT_ID");
////        sql.INNER_JOIN("COMPANY C on D.COMPANY_ID = C.ID");
////        sql.WHERE("P.ID = A.ID");
////        sql.WHERE("P.FIRST_NAME like ?");
////        sql.OR();
////        sql.WHERE("P.LAST_NAME like ?");
////        sql.GROUP_BY("P.ID");
////        sql.HAVING("P.LAST_NAME like ?");
////        sql.OR();
////        sql.HAVING("P.FIRST_NAME like ?");
////        sql.ORDER_BY("P.ID");
////        sql.ORDER_BY("P.FULL_NAME");
////        sql.LIMIT(100);
////
////        String s = sql.toString();
////        System.out.println(s);
//
//        sql.FROM("hello");
//        sql.SELECT("a");
//        sql.FETCH_FIRST_ROWS_ONLY(100);
//        String s1 = sql.toString();
//        System.out.println(s1);
//    }


    /**
     * 拼接分页查询sql<br/>
     * ORACLE,SQLSERVER,DB2,DM通用
     *
     * @param field
     * @param from
     * @param page
     * @param size
     * @param orderBy
     * @return
     */
    public static String joinPageQuerySql(final String field, final String from, final int page, final int size,
                                          final String orderBy) {
        int pageNum = page;// 页数从1开始计数
        int sizeNum = size;
        StringBuilder buff = new StringBuilder("SELECT ");
        buff.append(field);
        buff.append(" FROM (SELECT ROW_NUMBER() OVER (ORDER BY ");
        buff.append(!StringUtils.isEmpty(orderBy) ? orderBy : field);
        buff.append(") AS RN,PAGE1.* FROM(");
        buff.append(from);
        buff.append(") PAGE1 ) PAGE2 WHERE RN <= ");
        buff.append(pageNum * sizeNum);
        buff.append(" AND RN > ");
        buff.append((pageNum - 1) * sizeNum);
        return buff.toString();
    }

    /**
     * 示例
     * @param args
     */
    public static void main(String[] args) {
        String field = "id,resource_type,deploy_status";
        String from = "asset_resource";
        String orderBy = "id";
        String sql = joinPageQuerySql(field, from.toString(), 1, 15, orderBy);
        System.out.println(sql);
    }
}

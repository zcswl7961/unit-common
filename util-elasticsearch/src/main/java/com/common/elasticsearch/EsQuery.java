package com.common.elasticsearch;
import org.elasticsearch.client.transport.TransportClient;

import java.util.List;

/**
 * elasticsearch数据获取，通过elasticsearch-sql获取对应的es数据，映射字段并返回指定的映射集
 * @author: zhoucg
 * @date: 2018-09-13
 */
public interface EsQuery<T> {

    /**
     * 调用es连接，根据es-sql获取对应的数据信息
     *          无参的es语句
     * @param sql
     * @param client
     * @return 单个对象获取
     */
    T findMessage(String sql, TransportClient client);

    /**
     * 调用es连接，根据es-sql获取对应的数据信息
     *          有参的es语句
     * @param sql
     * @param client
     * @param params  参数
     * @return 单个对象获取
     */
    T findMessage(String sql,TransportClient client,String ...params);

    /**
     * 调用es连接，根据es-sql获取对应的数据信息
     *          有参的es语句
     * @param sql
     * @param client
     * @param params
     * @return 集合
     */
    List<T> findMessageList(String sql, TransportClient client, String ...params);

    /**
     * 调用es连接，根据es-sql获取对应的数据信息
     *          无参的es语句
     * @param sql
     * @param client
     * @return  集合
     */
    List<T> findMessageList(String sql,TransportClient client);
}

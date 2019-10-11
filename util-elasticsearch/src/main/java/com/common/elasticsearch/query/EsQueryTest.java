package com.common.elasticsearch.query;

import com.common.elasticsearch.ElasticSearchConnection;
import com.common.elasticsearch.support.AbstractEsQuery;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;

import java.util.List;
import java.util.Map;


/**
 * @author: zhoucg
 * @date: 2019-10-11
 */
public class EsQueryTest extends AbstractEsQuery{


    /**
     * 1，获取对应TransportClient
     * 2，封装sql
     * 3，重写parseSourceTotalHits方法，将对应的结果重新映射字段,或者重写parseAggregationTotalHits，映射聚合函数信息
     * 4，返回结果
     * @return
     */
    private void test() {
        ElasticSearchConnection elasticSearchConnection = new ElasticSearchConnection("elasticsearch","hosts");
        elasticSearchConnection.connect();
        TransportClient transportClient = elasticSearchConnection.getClient();

        String sql = "select * from demo where id = '%s'";
        String[] params = {"alantic_log_20191011"};


        EsQueryTest esQueryTest = new EsQueryTest();
        List result = esQueryTest.findMessageList(sql,transportClient,params);
    }

    @Override
    public List<?> parseSourceTotalHits(List list, SearchHit[] hits) {
        for(SearchHit hit :  hits) {
            /**
             * elasticsearch doc key ---> value
             */
            Map<String, Object> objectMap = hit.getSource();
            // ...封装
        }
        return list;
    }

    @Override
    public List<?> parseAggregationTotalHits(List list, String finalValue) {
        // convert 聚合函数的结果到count中
        return super.parseAggregationTotalHits(list, finalValue);
    }
}

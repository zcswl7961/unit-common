package com.common.util.es;

import com.google.common.collect.Lists;
import org.elasticsearch.action.ActionRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.nlpcn.es4sql.SearchDao;
import org.nlpcn.es4sql.query.SqlElasticRequestBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhoucg on 2019-01-28.
 * es 读取文档 使用sql语句操作
 */
@Service
public class EsReadClient {

    public List findMessageList(String sql, TransportClient client) {
        List list = Lists.newArrayList();
        try{
            SearchResponse searchResponse = executeEsSql(sql,client);
            if(searchResponse.getScrollId()!=null) {
                String next = searchResponse.getScrollId();
                TimeValue timeValue = new TimeValue(30000);
                while (true) {
                    SearchResponse sr = client.prepareSearchScroll(next).setScroll(timeValue).get();
                    if(sr.getHits().getHits().length ==0) break;
                    //do something as for subclass
                    return (List) this.parseSourceTotalHits(list,sr.getHits().getHits());
                }
                return list;
            } else {
                if (searchResponse.getAggregations() != null && searchResponse.getAggregations().asMap().size() > 0) {
                    Map<String, Aggregation> aggregations = searchResponse.getAggregations().asMap();
                    Aggregation aggregation = aggregations.get("count");
                    String finalValue = aggregation.getProperty("value").toString();
                    //do something as for subclass
                    return (List) this.parseAggregationTotalHits(list,finalValue);
                } else {
                    SearchHits hits = searchResponse.getHits();
                    //do something as for subclass
                    return (List) this.parseSourceTotalHits(list,hits.getHits());
                }
            }

        } catch ( Exception e) {
            return list;
        }
    }

    /**
     * 子类重写该方法
     * @param list
     * @param hits
     * @return
     */
    public List<?> parseSourceTotalHits(List<?> list, SearchHit[] hits) {return list;}

    /**
     * es聚合类sql语句调用该方法
     * 子类可重写该方法
     * @param list
     * @return
     */
    public List<?> parseAggregationTotalHits(List list,String finalValue) {
        Map<String,String> aggergationMap = new HashMap<>();
        aggergationMap.put("total",finalValue);
        list.add(aggergationMap);
        return list;
    }

    public static SearchResponse executeEsSql(String sql, TransportClient client) throws Exception {
        SearchDao searchDao = new SearchDao(client);
        SqlElasticRequestBuilder explain = searchDao.explain(sql).explain();
        ActionRequestBuilder builder = explain.getBuilder();
        if(builder instanceof SearchRequestBuilder) {
            ((SearchRequestBuilder)builder).setIndicesOptions(IndicesOptions.lenientExpandOpen());
            ((SearchRequestBuilder)builder).setSize(10000);

        }
        SearchResponse searchResponse = (SearchResponse) builder.execute().get();
        return searchResponse;
    }

}

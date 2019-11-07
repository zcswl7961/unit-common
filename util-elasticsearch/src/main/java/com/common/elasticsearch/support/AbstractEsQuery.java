package com.common.elasticsearch.support;

import com.common.elasticsearch.EsQuery;
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
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.elasticsearch.search.aggregations.metrics.valuecount.InternalValueCount;
import org.nlpcn.es4sql.SearchDao;
import org.nlpcn.es4sql.query.SqlElasticRequestBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zhoucg
 * @date: 2019-10-11
 */
public abstract class AbstractEsQuery<T> implements EsQuery<T> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractEsQuery.class);

    /**
     * 返回es中的结果映射单个示例
     * @param sql
     * @param client
     * @return
     */
    @Override
    public T findMessage(String sql, TransportClient client) {
        return null;
    }

    @Override
    public T findMessage(String sql, TransportClient client, String ...params) {
        if(params != null && !"".equals(params)) {
            sql = String.format(sql,params);
        }
        return this.findMessage(sql,client);
    }

    /**
     * 封装对应的查询条件，实现改抽象类，重写parseSourceTotalHits方法实现相关的业务处理
     * @param sql
     * @param client
     * @param params
     * @return
     */
    @Override
    public List<T> findMessageList(String sql, TransportClient client, String ...params) {

        if(params != null && !"".equals(params)) {
            sql = String.format(sql,params);
        }
        return this.findMessageList(sql,client);
    }

    @Override
    public List<T> findMessageList(String sql, TransportClient client) {
        List list = Lists.newArrayList();
        try{
            SearchResponse searchResponse = executeEsSql(sql,client);
            if(searchResponse.getScrollId()!=null) {
                String next = searchResponse.getScrollId();
                TimeValue timeValue = new TimeValue(30000);
                while (true) {
                    SearchResponse sr = client.prepareSearchScroll(next).setScroll(timeValue).get();
                    if(sr.getHits().getHits().length ==0) {
                        break;
                    }
                    //do something as for subclass
                    return (List<T>) this.parseSourceTotalHits(list,sr.getHits().getHits());
                }
                return list;
            } else {
                if (searchResponse.getAggregations() != null && searchResponse.getAggregations().asMap().size() > 0) {
                    Map<String, Aggregation> aggregations = searchResponse.getAggregations().asMap();
                    //es5版本升级,聚合函数升级 add by zhoucg
                    try{
                        InternalSum internalSum = (InternalSum)aggregations.get("count");
                        String finalValue = internalSum.getValue()+"";
                        return (List<T>) this.parseAggregationTotalHits(list,finalValue);
                    } catch (Exception e) {
                        //
                        InternalValueCount internalValueCount = (InternalValueCount) aggregations.get("count");
                        String finalValue = internalValueCount.getValue()+"";
                        return (List<T>) this.parseAggregationTotalHits(list,finalValue);
                    }
                } else {
                    SearchHits hits = searchResponse.getHits();
                    //do something as for subclass
                    return (List<T>) this.parseSourceTotalHits(list,hits.getHits());
                }
            }

        } catch ( Exception e) {
            logger.error("调用es获取数据出错，当前错误信息：{}",e.getMessage());
            return list;
        }
    }

    /**
     * 该方法应由子类重写，将对应es的每一条查询数据{@code hit.getSource()} 的键值映射到返回的结果map数据中，
     * 标准的获取数据的实现方法<pre>
     *     {@code
     *      parseSourceTotalHits(List list SearchHit[] hits) {
     *          for(SearchHit hit : hits) {
     *              Map<String,Object> hitMap = hit.getSource();
     *              for(Map.Entry<String,Object> entity : hitMap.entrySet()) {
     *                  String key = entity.getKey();//该值表示每一个映射行的rowName
     *                  Object value = entity.getValue();//该值表示对应rowName的结果
     *                  //将对应的结果映射到list中
     *                 ......
     *              }
     *          }
     *      }
     *      }
     * </pre>
     * @param list 传递的需要映射的list，也可以通过返回一个新的list获取对应的数据
     * @param hits es查询到的数据
     * @return 返回转换之后的映射后的结果
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

    /**
     * es调用
     * @param sql
     * @param client
     * @return
     * @throws Exception
     */
    private SearchResponse executeEsSql(String sql, TransportClient client) throws Exception {
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

package com.common.util.es;


import com.google.gson.Gson;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by zhoucg on 2019-01-28.
 *
 * es 批量插入文档信息
 */
@Service
public class EsWriterClient {

    private static final Logger logger = LoggerFactory.getLogger(EsWriterClient.class);

    private ElasticSearchConnection connection = null;

    private BulkProcessor bulkProcessor;

    public EsWriterClient(@Value("${elasticsearch.hosts}") String esHosts,
                          @Value("${elasticsearch.cluster.name}") String esClusterName) {
        logger.info("LogSearchService init es:{}, cluster:{}", esHosts, esClusterName);
        connection = new ElasticSearchConnection(esHosts,esClusterName);
    }

    @PostConstruct
    public void connect() {
        connection.connect();
        bulkProcessor = BulkProcessor.builder(
                connection.getClient(),
                new BulkProcessor.Listener() {
                    @Override
                    public void beforeBulk(long executionId, BulkRequest request) {

                    }

                    @Override
                    public void afterBulk(long executionId,
                                          BulkRequest request, BulkResponse response) {

                    }

                    @Override
                    public void afterBulk(long executionId,
                                          BulkRequest request, Throwable failure) {
                        logger.error("尝试插入数据失败", failure);
                    }
                })
                //基于条数
                .setBulkActions(100)
                //基于容量
                .setBulkSize(new ByteSizeValue(2, ByteSizeUnit.MB))
                //基于时间
                .setFlushInterval(TimeValue.timeValueSeconds(2))
                //并行度
                .setConcurrentRequests(5)
                //如果线程池满了，重试的频率
                .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueSeconds(1), 10))
                .build();
        logger.info("初始化es写入操作开始");
    }


    /**
     * 将一个待插入到es中的对象存入到bulkProcessor中
     * @param index 索引
     * @param type 类型
     * @param id 每一个document的id值标识
     * @param obj 写入es的对象
     * @return
     */
    public String save(String index,String type,String id,Object obj) {

        bulkProcessor.add(new IndexRequest(index,type,id).source((obj instanceof  String) ? (String)obj : new Gson().toJson(obj)));
        return id;
    }
}

package com.common.elasticsearch;

import org.elasticsearch.client.transport.TransportClient;

/**
 * es客户端连接
 * @author: zhoucg
 * @date: 2018-09-11
 */
public class EsClient {

    private TransportClient transportClient;
    /**
     * es集群信息：${elasticsearch5.cluster.name}
     */
    private String clusterName;

    /**
     * es主机信息: ${elasticsearch5.hosts}
     */
    private String hosts;

    public TransportClient getTransportClient() {

        if(transportClient == null) {
            transportClient = EsTransportClient.getTransportClient(clusterName,hosts);
        }
        return transportClient;
    }

    public EsClient(String clusterName, String hosts) {
        super();
        this.clusterName = clusterName;
        this.hosts = hosts;
    }

    public EsClient() {
        super();
    }

}

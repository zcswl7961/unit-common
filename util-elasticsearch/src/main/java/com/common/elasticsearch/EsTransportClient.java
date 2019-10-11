package com.common.elasticsearch;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author: zhoucg
 * @date: 2019-10-11
 */
public class EsTransportClient {

    private static Logger logger = LoggerFactory.getLogger(EsTransportClient.class);

    private static TransportClient transportClient;

    /**
     * 获取es client连接
     * @param clusterName
     * @param hosts
     * @return
     */
    public static TransportClient getTransportClient(String clusterName,String hosts) {

        Settings settings = Settings.builder().put("cluster.name", clusterName).build();
        transportClient = new PreBuiltTransportClient(settings);
        if(hosts != null){
            for(String address:hosts.split(",")) {
                String[] iport = address.split(":");
                TransportAddress iAddress;
                try {
                    iAddress = new InetSocketTransportAddress(InetAddress.getByName(iport[0]), Integer.valueOf(iport[1]));
                    transportClient.addTransportAddress(iAddress);
                } catch (NumberFormatException | UnknownHostException e) {
                    logger.error("当前es获取数据连接失败，主机：{}，错误信息：{}",hosts,e.getMessage());
                }
            }
        }
        return transportClient;
    }
}

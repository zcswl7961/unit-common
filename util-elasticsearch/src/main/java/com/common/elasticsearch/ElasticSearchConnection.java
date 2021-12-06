package com.common.elasticsearch;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * @author zhoucg
 * @date 2019-10-7
 */
public class ElasticSearchConnection {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchConnection.class);
    private TransportClient client;
    /**
     * 192.168.129.128:9200,192.168.129.129:9200
     */
    private String hosts;
    /**
     * elasticsearch
     */
    private String clusterName;

    public ElasticSearchConnection(String hosts, String clusterName) {
        this.hosts = hosts;
        this.clusterName = clusterName;
    }

    public void connect() {
        try {
            Settings settings;
            if (clusterName != null && !clusterName.isEmpty())
                settings = Settings.builder().put("cluster.name", clusterName).build();
            else
                settings = Settings.builder().build();
            client = new PreBuiltTransportClient(settings);

            addHost(client, hosts);
            logger.info("elastisearch  info :{}, cluster.name: {} ", hosts, clusterName);
        } catch (UnknownHostException e) {
        }
    }

    private void addHost(TransportClient client, String hosts) throws UnknownHostException {
        String[] items = hosts.split(",");
        for (String item : items) {
            String[] fields = item.split(":");
            String host = fields[0];
            int port = 9300;
            if (fields.length > 1)
                port = Integer.parseInt(fields[1]);
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
        }
    }

    public void disconnect() {
        if (client != null) {
            client.close();
            client = null;
        }
    }

    public TransportClient getClient() {
        return client;
    }
}

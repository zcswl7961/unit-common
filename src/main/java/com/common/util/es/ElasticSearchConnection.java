package com.common.util.es;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.omg.CORBA.portable.UnknownException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by zhoucg on 2019-01-28.
 * es 连接工具
 */
public class ElasticSearchConnection {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchConnection.class);
    private TransportClient client;
    private String hosts;
    private String clusterName;

    public ElasticSearchConnection(String hosts, String clusterName) {
        this.hosts = hosts;
        this.clusterName = clusterName;
    }

    public void connect() {
        try {
            Settings settings;
            if(this.clusterName != null && !this.clusterName.isEmpty()) {
                settings = Settings.builder().put("cluster.name", this.clusterName).build();
            } else {
                settings = Settings.builder().build();
            }

            this.client = TransportClient.builder().settings(settings).build();
            this.addHost(this.client, this.hosts);
            logger.info("elastisearch  info :{}, cluster.name: {} ", this.hosts, this.clusterName);
        } catch (UnknownHostException var2) {
            throw new UnknownException(var2);
        }
    }

    private void addHost(TransportClient client, String hosts) throws UnknownHostException {
        String[] items = hosts.split(",");
        String[] var4 = items;
        int var5 = items.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String item = var4[var6];
            String[] fields = item.split(":");
            String host = fields[0];
            int port = 9300;
            if(fields.length > 1) {
                port = Integer.parseInt(fields[1]);
            }

            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
        }

    }

    public void disconnect() {
        if(this.client != null) {
            this.client.close();
            this.client = null;
        }

    }

    public TransportClient getClient() {
        return this.client;
    }
}

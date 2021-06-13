package com.hadoop.demo.yarn;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.api.protocolrecords.RegisterApplicationMasterResponse;
import org.apache.hadoop.yarn.client.api.AMRMClient;
import org.apache.hadoop.yarn.client.api.async.AMRMClientAsync;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnException;

import java.io.IOException;

/**
 * @author zhoucg
 * @date 2021-02-19 23:17
 * @see <a href="https://github.com/fireflyc/learnyarn/blob/v0.1/src/main/java/im/lsn/learnyarn/am/ApplicationMaster.java">ApplicationMaster</a>
 */
public class ApplicationMaster {

    public static void main(String[] args) throws IOException, YarnException, InterruptedException {
        Configuration conf = new YarnConfiguration();

        //便于使用minicluster调试
        boolean isDebug = false;
        if (args.length >= 1 && args[0].equalsIgnoreCase("debug")) {
            isDebug = true;
        }
        if (isDebug) {

            /**
             * yarn.resourcemanager.address
             */
            conf.set(YarnConfiguration.RM_ADDRESS, "192.168.129.128:8031");
            conf.set(YarnConfiguration.RM_HOSTNAME, "192.168.129.128");
            /**
             * yarn.resourcemanager.scheduler.address
             */
            conf.set(YarnConfiguration.RM_SCHEDULER_ADDRESS, "192.168.129.128:8030");
            conf.set(YarnConfiguration.RM_RESOURCE_TRACKER_ADDRESS, "localhost:8031");
            /**
             * yarn.resourcemanager.webapp.address
             */
            conf.set(YarnConfiguration.RM_WEBAPP_ADDRESS, "192.168.129.128:8088");
            conf.setBoolean(YarnConfiguration.YARN_MINICLUSTER_FIXED_PORTS, true);
        }
        AMRMClientAsync<AMRMClient.ContainerRequest> resourceManager = AMRMClientAsync.createAMRMClientAsync(1000,
                new ApplicationMasterCallback());
        resourceManager.init(conf);
        resourceManager.start();
        RegisterApplicationMasterResponse registration = resourceManager.registerApplicationMaster("", -1, "");
        while (true) {
            System.out.println("(stdout)Hello World");
            System.err.println("(stderr)Hello World");
            Thread.sleep(1000);
        }
    }
}

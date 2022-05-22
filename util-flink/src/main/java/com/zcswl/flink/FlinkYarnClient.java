package com.zcswl.flink;

import org.apache.flink.client.deployment.ClusterSpecification;
import org.apache.flink.client.deployment.application.ApplicationConfiguration;
import org.apache.flink.client.program.ClusterClient;
import org.apache.flink.client.program.ClusterClientProvider;
import org.apache.flink.configuration.*;
import org.apache.flink.yarn.YarnClientYarnClusterInformationRetriever;
import org.apache.flink.yarn.YarnClusterDescriptor;
import org.apache.flink.yarn.YarnClusterInformationRetriever;
import org.apache.flink.yarn.configuration.YarnConfigOptions;
import org.apache.flink.yarn.configuration.YarnDeploymentTarget;
import org.apache.flink.yarn.configuration.YarnLogConfigUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.conf.YarnConfiguration;

import java.util.Collections;

/**
 * @author zhoucg
 * @date 2022-05-02 12:00
 */
public class FlinkYarnClient {

    public static void main(String[] args) {
        //flink的本地配置目录，为了得到flink的配置
        // 如果出现org.apache.flink.streaming.runtime.tasks.StreamTaskException: Cannot instantiate user function.错误
        // 则在flink-config.yaml加入
        // classloader.resolve-order: parent-first
        String configurationDirectory = "/opt/module/flink-1.11.4/conf";
        // String configurationDirectory = "/home/lxj/workspace/Olt-Test/bigdata/bigdataserver/src/main/resources/flink/conf";

        //存放flink集群相关的jar包目录
        String flinkLibs = "hdfs://hadoop113:8020/jar/flink11/libs";
        //用户jar
        String userJarPath = "hdfs://hadoop113:8020/jar/userTask/Flink1.11-1.0-SNAPSHOT-jar-with-dependencies.jar";
        String flinkDistJar = "hdfs://hadoop113:8020/jar/flink11/libs/flink-dist_2.12-1.11.4.jar";

        YarnClient yarnClient = YarnClient.createYarnClient();
        YarnConfiguration yarnConfiguration = new YarnConfiguration();
        yarnClient.init(yarnConfiguration);
        yarnClient.start();

        // 设置日志的，没有的话看不到日志
        YarnClusterInformationRetriever clusterInformationRetriever = YarnClientYarnClusterInformationRetriever
                .create(yarnClient);

        //获取flink的配置
        Configuration flinkConfiguration = GlobalConfiguration.loadConfiguration(
                configurationDirectory);

        flinkConfiguration.set(CheckpointingOptions.INCREMENTAL_CHECKPOINTS, true);

        flinkConfiguration.set(
                PipelineOptions.JARS,
                Collections.singletonList(userJarPath));

        Path remoteLib = new Path(flinkLibs);
        flinkConfiguration.set(
                YarnConfigOptions.PROVIDED_LIB_DIRS,
                Collections.singletonList(remoteLib.toString()));

        flinkConfiguration.set(
                YarnConfigOptions.FLINK_DIST_JAR,
                flinkDistJar);

        // 设置为application模式
        flinkConfiguration.set(
                DeploymentOptions.TARGET,
                YarnDeploymentTarget.APPLICATION.getName());

        // yarn application name
        flinkConfiguration.set(YarnConfigOptions.APPLICATION_NAME, "flink-application");

        YarnLogConfigUtil.setLogConfigFileInConfig(flinkConfiguration, configurationDirectory);

        ClusterSpecification clusterSpecification = new ClusterSpecification.ClusterSpecificationBuilder()
                .createClusterSpecification();

        // 设置用户jar的参数和主类
        ApplicationConfiguration appConfig = new ApplicationConfiguration(new String[] {"test"}, "com.starnet.server.bigdata.flink.WordCount");

        YarnClusterDescriptor yarnClusterDescriptor = new YarnClusterDescriptor(
                flinkConfiguration,
                yarnConfiguration,
                yarnClient,
                clusterInformationRetriever,
                true);

        try {
            ClusterClientProvider<ApplicationId> clusterClientProvider = yarnClusterDescriptor.deployApplicationCluster(
                    clusterSpecification,
                    appConfig);

            ClusterClient<ApplicationId> clusterClient = clusterClientProvider.getClusterClient();

            ApplicationId applicationId = clusterClient.getClusterId();
            String webInterfaceURL = clusterClient.getWebInterfaceURL();

            // 退出
            // yarnClusterDescriptor.killCluster(applicationId);
        } catch (Exception e){
        }
    }
}

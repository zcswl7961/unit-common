package com.hadoop.demo.yarn;

import org.apache.hadoop.yarn.api.records.Container;
import org.apache.hadoop.yarn.api.records.ContainerStatus;
import org.apache.hadoop.yarn.api.records.NodeReport;
import org.apache.hadoop.yarn.client.api.async.AMRMClientAsync;

import java.util.List;

/**
 * @author zhoucg
 * @date 2021-02-19 23:18
 */
public class ApplicationMasterCallback implements AMRMClientAsync.CallbackHandler {
    @Override
    public void onContainersCompleted(List<ContainerStatus> list) {

    }

    @Override
    public void onContainersAllocated(List<Container> list) {

    }

    @Override
    public void onShutdownRequest() {

    }

    @Override
    public void onNodesUpdated(List<NodeReport> list) {

    }

    @Override
    public float getProgress() {
        return 0;
    }

    @Override
    public void onError(Throwable throwable) {

    }
}

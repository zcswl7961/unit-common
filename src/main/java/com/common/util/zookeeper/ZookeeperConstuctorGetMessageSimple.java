package com.common.util.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhoucg on 2019-04-19.
 * zookeeper对于节点的数据获取，同样存在同步和异步的方式
 */
public class ZookeeperConstuctorGetMessageSimple  implements Watcher{

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk = null;
    private static Stat stat = new Stat();


    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            if (Event.EventType.None == event.getType() && null == event.getPath()) {
                connectedSemaphore.countDown();
            } else if (event.getType() == Event.EventType.NodeDataChanged) {
                try {
                    System.out.println("the data of znode " + event.getPath() + " is : " + new String(zk.getData(event.getPath(), true, stat)));
                    System.out.println("czxID: " + stat.getCzxid() + ", mzxID: " + stat.getMzxid() + ", version: " + stat.getVersion());
                } catch (Exception e) {
                }
            }
        }
    }


    /**
     * 同步的方式获取zookeeper节点内容信息
     * @throws IOException
     * @throws InterruptedException
     * @throws KeeperException
     */
    @Test
    public void getMessage() throws IOException, InterruptedException, KeeperException {
        String path = "/zk-book";
        zk = new ZooKeeper("10.1.241.37:2181", 5000,
                new ZookeeperConstuctorGetMessageSimple());
        connectedSemaphore.await();
        zk.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("success create znode: " + path);

        System.out.println("the data of znode " + path + " is : " + new String(zk.getData(path, true, stat)));
        System.out.println("czxID: " + stat.getCzxid() + ", mzxID: " + stat.getMzxid() + ", version: " + stat.getVersion());

        zk.setData(path, "123".getBytes(), -1);

        Thread.sleep(Integer.MAX_VALUE);
    }


    /**
     * 异步的方式获取节点内容
     */
    @Test
    public void synGetMessage() throws InterruptedException, IOException, KeeperException {
        String path = "/zk-book";
        zk = new ZooKeeper("10.1.241.37:2181", 5000,
                new ZookeeperConstuctorGetMessageSimple());
        connectedSemaphore.await();

        zk.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("success create znode: " + path);

        zk.getData(path, true, new IDataCallback(), null);
        zk.setData(path, "123".getBytes(), -1);
        Thread.sleep(Integer.MAX_VALUE);
    }

    class IDataCallback implements AsyncCallback.DataCallback {
        public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
            System.out.println("rc: " + rc + ", path: " + path + ", data: " + new String(data));
            System.out.println("czxID: " + stat.getCzxid() + ", mzxID: " + stat.getMzxid() + ", version: " + stat.getVersion());
        }
    }


}

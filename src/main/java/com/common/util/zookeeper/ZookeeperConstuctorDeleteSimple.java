package com.common.util.zookeeper;

import org.apache.zookeeper.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhoucg on 2019-04-19.
 * zookeeper节点的删除
 */
public class ZookeeperConstuctorDeleteSimple implements Watcher{

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    private static ZooKeeper zk;


    @Override
    public void process(WatchedEvent event) {

        System.out.println("event 的回调事件"+event);

        if(Event.KeeperState.SyncConnected == event.getState()) {
            if(Event.EventType.None == event.getType() && null == event.getPath()) {
                connectedSemaphore.countDown();
            }
        }
    }


    /**
     * 同步的方式删除节点，只允许删除叶子节点，即一个节点如果有子节点，那么该节点将无法直接删除，必须先删除其所有的子节点，
     * @throws InterruptedException
     * @throws KeeperException
     * @throws IOException
     */
    @Test
    public void delete() throws InterruptedException, KeeperException, IOException {
        String path = "/zk-book";
        zk = new ZooKeeper("10.1.241.37:2181", 5000,
                new ZookeeperConstuctorDeleteSimple());
        connectedSemaphore.await();

        zk.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("success create znode: " + path);
        zk.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("success create znode: " + path + "/c1");
        try {
            zk.delete(path, -1);
        } catch (Exception e) {
            System.out.println("fail to delete znode: " + path);
        }

        zk.delete(path + "/c1", -1);
        System.out.println("success delete znode: " + path + "/c1");
        zk.delete(path, -1);
        System.out.println("success delete znode: " + path);

        Thread.sleep(Integer.MAX_VALUE);
    }


    /**
     * zookeeper异步的方式删除节点
     * @throws IOException
     * @throws InterruptedException
     * @throws KeeperException
     */
    @Test
    public void synDelete() throws IOException, InterruptedException, KeeperException {
        String path = "/zk-book";
        zk = new ZooKeeper("10.1.241.37:2181", 5000,
                new ZookeeperConstuctorDeleteSimple());
        connectedSemaphore.await();

        zk.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("success create znode: " + path);
        zk.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("success create znode: " + path + "/c1");

        zk.delete(path, -1, new IVoidCallback(), null);
        zk.delete(path + "/c1", -1, new IVoidCallback(), null);
        zk.delete(path, -1, new IVoidCallback(), null);

        Thread.sleep(Integer.MAX_VALUE);
    }

    class IVoidCallback implements AsyncCallback.VoidCallback {
        public void processResult(int rc, String path, Object ctx) {
            System.out.println(rc + ", " + path + ", " + ctx);
        }
    }

}

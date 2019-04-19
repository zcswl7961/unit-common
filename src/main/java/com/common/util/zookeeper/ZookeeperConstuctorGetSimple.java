package com.common.util.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.List;

/**
 * Created by zhoucg on 2019-04-19.
 * zookeeper获取子节点信息
 * 值得注意的是，Watcher通知是一次性的，即一旦触发一次通知后，
 * 该Watcher就失效了，因此客户端需要反复注册Watcher，
 * 即程序中在process里面又注册了Watcher，否则，将无法获取c3节点的创建而导致子节点变化的事件。
 *
 */
public class ZookeeperConstuctorGetSimple implements Watcher{

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk = null;

    @Override
    public void process(WatchedEvent event) {
        if (Event.KeeperState.SyncConnected == event.getState()) {
            if (Event.EventType.None == event.getType() && null == event.getPath()) {
                connectedSemaphore.countDown();
            } else if (event.getType() == Event.EventType.NodeChildrenChanged) {
                try {
                    System.out.println("ReGet Child:" + zk.getChildren(event.getPath(), true));
                } catch (Exception e) {
                }
            }
        }
    }

    @Test
    public void get() throws IOException, InterruptedException, KeeperException {
        String path = "/zk-book-2";
        zk = new ZooKeeper("10.1.241.37:2181", 5000, new ZookeeperConstuctorGetSimple());
        connectedSemaphore.await();

        zk.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("success create znode: " + path);
        zk.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("success create znode: " + path + "/c1");
        List<String> childrenList = zk.getChildren(path, true);
        System.out.println(childrenList);

        zk.create(path + "/c2", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("success create znode: " + path + "/c2");
        Thread.sleep(1000);
        zk.create(path + "/c3", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("success create znode: " + path + "/c3");
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 异步的方式
     * @throws InterruptedException
     * @throws IOException
     * @throws KeeperException
     */
    @Test
    public void synGet() throws InterruptedException, IOException, KeeperException {
        String path = "/zk-book";
        zk = new ZooKeeper("10.1.241.37:2181", 5000, new ZookeeperConstuctorGetSimple());
        connectedSemaphore.await();
        zk.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("success create znode: " + path);
        zk.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("success create znode: " + path + "/c1");

        zk.getChildren(path, true, new IChildren2Callback(), null);

        zk.create(path + "/c2", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("success create znode: " + path + "/c2");

        Thread.sleep(Integer.MAX_VALUE);
    }

    class IChildren2Callback implements AsyncCallback.Children2Callback {
        public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
            System.out.println("Get Children znode result: [response code: " + rc + ", param path: " + path + ", ctx: "
                    + ctx + ", children list: " + children + ", stat: " + stat);
        }
    }
}

package com.common.util.zookeeper;

import org.apache.zookeeper.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhoucg on 2019-04-19.
 * zookeeper节点的增删改查操作 java API
 * @see {https://www.cnblogs.com/leesf456/p/6028416.html}
 */
public class ZookeeperConstructorSimple implements Watcher{


    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    public void process(WatchedEvent event) {
        System.out.println("Receive watched event : " + event);
        if(Event.KeeperState.SyncConnected == event.getState()) {
            countDownLatch.countDown();
        }
    }


    /**
     * zookeeper创建节点
     * 同步的方式进行创建节点
     * 无论是异步还是同步的方式，zookeeper都不支持递归调用，
     *  即无法在父节点不存在的情况下创建一个子节点，
     *  如在/zk-ephemeral节点不存在的情况下创建/zk-ephemeral/ch1节点；并且如果一个节点已经存在，那么创建同名节点
     * @throws IOException
     * @throws InterruptedException
     * @throws KeeperException
     */
    @Test
    public void testCreate() throws IOException, InterruptedException, KeeperException {
        ZooKeeper zookeeper = new ZooKeeper("10.1.241.37:2181", 5000, new ZookeeperConstructorSimple());
        System.out.println(zookeeper.getState());
        countDownLatch.await();

        String path1 = zookeeper.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("Success create znode: " + path1);

        String path2 = zookeeper.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("Success create znode: " + path2);


    }

    /**
     * 使用异步的方式进行创建节点
     * 使用异步方式于同步方式的区别在于节点的创建过程（包括网络通信和服务端的节点创建过程）是异步的，在同步接口调用过程中，
     * 开发者需要关注接口抛出异常的可能，但是在异步接口中，接口本身不会抛出异常，所有异常都会在回调函数中通过Result Code来体现。
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void synTestCreate() throws IOException, InterruptedException {
        ZooKeeper zookeeper = new ZooKeeper("10.1.241.37:2181", 5000, new ZookeeperConstructorSimple());
        System.out.println(zookeeper.getState());
        countDownLatch.await();

        zookeeper.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
                new IStringCallback(), "I am context. ");

        zookeeper.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
                new IStringCallback(), "I am context. ");

        zookeeper.create("/zk-test-ephemeral-", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL,
                new IStringCallback(), "I am context. ");


    }




    class IStringCallback implements AsyncCallback.StringCallback {

        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            System.out.println("Create path result: [" + rc + ", " + path + ", " + ctx + ", real path name: " + name);
        }
    }


    public static void main(String[] args) throws IOException {
        ZooKeeper zookeeper = new ZooKeeper("10.1.241.37:2181", 5000, new ZookeeperConstructorSimple());
        System.out.println(zookeeper.getState());
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Zookeeper session established");
    }

}

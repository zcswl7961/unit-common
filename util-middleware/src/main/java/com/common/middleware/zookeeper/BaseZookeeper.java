package com.common.middleware.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;
import java.util.List;

/**
 * @author: zhoucg
 * @date: 2019-10-21
 */
public class BaseZookeeper implements Watcher {

    private ZooKeeper zookeeper;

    private static final int SESSION_TIMEOUT = 10000;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    public void process(WatchedEvent event) {
        System.out.println("PROCESS");
        if(event.getState() == Event.KeeperState.SyncConnected) {
            System.out.println("Watch received event");
            countDownLatch.countDown();
        }
    }

    /**
     * 连接zookeeper
     * @param host
     * @throws Exception
     */
    public void connectZookeeper(String host) throws Exception{
        zookeeper = new ZooKeeper(host, SESSION_TIMEOUT, this);
        countDownLatch.await();
        System.out.println("zookeeper connection success");
    }

    /**
     * 创建节点
     * @param path
     * @param data
     * @return
     */
    public String creatNode(String path,String data) throws KeeperException, InterruptedException {

        return this.zookeeper.create(path,data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    /**
     * 获取路径下得所有子节点
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public List<String> getChildren(String path) throws KeeperException, InterruptedException {
        List<String> children = zookeeper.getChildren(path, false);
        return children;
    }


    /**
     * 获取节点上的数据
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String getData(String path) throws KeeperException, InterruptedException {
        byte[] data = zookeeper.getData(path,false,null);
        if (data == null) {
            return "";
        }
        return new String(data);
    }

    /**
     * 设置节点数据
     * @param path
     * @param data
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public Stat setData(String path, String data) throws KeeperException, InterruptedException {
        Stat stat = zookeeper.setData(path,data.getBytes(),-1);
        return stat;
    }

    /**
     * 删除节点
     * @param path
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void deleteNode(String path) throws KeeperException, InterruptedException {
        zookeeper.delete(path,-1);
    }

    /**
     * 获取指定路径下的孩子的个数
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public Integer getChildrenNum(String path) throws KeeperException, InterruptedException {
        int childrenNum = zookeeper.getChildren(path,false).size();
        return childrenNum;
    }

    /**
     * 获取创建时间
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String getCtime(String path) throws KeeperException, InterruptedException {
        Stat stat = zookeeper.exists(path, false);
        return String.valueOf(stat.getCtime());
    }

    /**
     * 关闭连接
     * @throws InterruptedException
     */
    public void closeConnection() throws InterruptedException{
        if (zookeeper != null) {
            zookeeper.close();
        }
    }
}

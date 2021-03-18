package com.common.middleware.zookeeper;
import org.apache.zookeeper.*;

import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 1,锁是公平锁
 * 2,我们分析一下这个锁是否为重入锁
 *      不可重入锁
 * @author zhoucg
 * @date 2019-10-31 18:15
 */
public class DistributeLock {

    /**
     * 根节点，在第一次创建时，zookeeper需要存在该节点
     */
    private static final String ROOT_LOCKS = "/LOCK";

    /**
     *
     */
    private ZooKeeper zookeeper;

    /**
     * 会话超时时间
     */
    private int sessionTimeout;

    /**
     *  记录锁节点ID
     */
    private String lockID;

    /**
     * 保存的数据
     */
    private static final byte[] data = {1,2};

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * 构造函数传入对应的
     * @param zookeeper
     */
    public DistributeLock(ZooKeeper zookeeper) {
        this.zookeeper = zookeeper;
        this.sessionTimeout = 4000;
    }


    /**
     * 获取锁的过程
     * @return 是否成功获取到锁
     */
    public boolean lock() throws KeeperException, InterruptedException {
        /**
         *1，客户端调用create方法在 ROOT_LOCKS 目录下创建临时顺序节点
         * zookeeper在为我们创建节点是时候会帮我
         们生成这样的节点LOCKS/00000001，自动带上序号
         */
        lockID = zookeeper.create(ROOT_LOCKS + "/", data, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);

        /**
         * 2，客户端调用getChildren(“locknode”)方法来获取所有已经创建的子节点。
         */
        List<String> childrenNodes = zookeeper.getChildren(ROOT_LOCKS,false);

        // 这个有点问题:
        // getChildren()返回的介绍
        // an unordered array of children of the node with the given path
        SortedSet<String> sortedSet = new TreeSet();
        for(String c:childrenNodes) {
            sortedSet.add(ROOT_LOCKS+"/"+c);
        }
        /**
         * 3，客户端获取到所有子节点path之后，如果发现自己在步骤1中创建的节点是所有节点中序号最小的，那么就认为这个客户端获得了锁。
         */
        String first = sortedSet.first();
        if(lockID.equals(first)) {
            System.out.println("线程Thread:"+Thread.currentThread().getName()+" 快速的拿到了锁");
            return true;
        }

        SortedSet<String> lessThanLockedId = sortedSet.headSet(lockID);
        if(!lessThanLockedId.isEmpty()) {
            //拿到比当前lockID这个节点更小的上一个节点
            // 这个实际上就是公平锁
            String prevLockID = lessThanLockedId.last();
            /**这个地方其实是去监听prevLockID，为什么要这样做呢，因为我当前的lockID不能
             成功获得锁，那其实也就意味着我这个节点下面的更不可能获得锁了，获得锁的只有可
             能是他往前的节点，所以去监听上一个节点
             */
            zookeeper.exists(prevLockID, new LockWatcher(countDownLatch));
            /**
             传了一个计数器到watcher也就是监听，当我监听到了该节点做了删除操作或者它的会
             话超时了，那么这个时候计数器减1
             */
            System.out.println("线程Thread:"+Thread.currentThread().getName()+"等待释放锁");
            countDownLatch.await();
            // 这个也有问题呀,设置了await的超时时间
            //countDownLatch.await(sessionTimeout, TimeUnit.MILLISECONDS);
            System.out.println("线程Thread:"+Thread.currentThread().getName()+"成功获取到了锁信息");
        }

        return true;
    }

    public boolean unlock() {

        System.out.println(Thread.currentThread().getName() + "-->开始释放锁["
                + lockID + "]");
        try {
            //这里delete的时候版本传的-1，意思是忽略版本的控制
            zookeeper.delete(lockID, -1);
            System.out.println("节点[" + lockID + "]成功被删除");

        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeeperException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) throws Exception {

        BaseZookeeper baseZookeeper = new BaseZookeeper();
        ZooKeeper zooKeeper = baseZookeeper.connectZookeeper("192.168.129.128:2181");

        Random random = new Random();
        CountDownLatch latch = new CountDownLatch(10);
        for(int i = 0; i < 10; i++) {
            new Thread(() -> {
                DistributeLock lock = null;

                try {
                    lock = new DistributeLock(zooKeeper);
                    latch.countDown();
                    latch.await();
                    try {
                        lock.lock();
                    } catch (KeeperException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println("线程Thread:"+Thread.currentThread().getName()+" 获取到了锁，执行一定的任务");
                    Thread.sleep(5000L);
                    //Thread.sleep(random.nextInt(500));
                }  catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    if(lock != null) {
                        System.out.println("线程Thread:"+Thread.currentThread().getName()+ " 执行任务完毕，释放锁");
                        lock.unlock();
                    }
                }
            }).start();
        }

    }


    public static class LockWatcher implements  Watcher {
        private CountDownLatch latch;
        //构造函数传了计数器进来
        public LockWatcher(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void process(WatchedEvent event) {
            // TODO Auto-generated method stub
            //当事件类型为删除节点时，计数器减1
            if(event.getType() == Event.EventType.NodeDeleted) {
                latch.countDown();
            }
        }
    }

}

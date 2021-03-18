package com.common.middleware.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;
import java.util.List;
/**
 * https://zookeeper.apache.org/doc/r3.4.9/zookeeperOver.html
 *
 * @author zhoucg
 * @date 2019-10-21
 */
public class BaseZookeeper implements Watcher {

    private ZooKeeper zookeeper;

    private static final int SESSION_TIMEOUT = 10000;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * Watcher的一次性
     */
    @Override
    public void process(WatchedEvent event) {
        /**
         * 监听事件操作
         */
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
    public ZooKeeper connectZookeeper(String host) throws Exception{
        zookeeper = new ZooKeeper(host, SESSION_TIMEOUT, this);
        countDownLatch.await();
        System.out.println("zookeeper connection success");
        return zookeeper;
    }

    /**
     * 创建节点
     * @param path
     * @param data
     * @return
     */
    public String creatNode(String path,String data) throws KeeperException, InterruptedException {

        /*
            zookeeper.create(final String path, byte data[], List<ACL> acl,
            CreateMode createMode )
                path:创建znode的时候指定的路径：/zhoucg
                data[]:节点数据
                acl:是创建节点的时候的策略。
                    acl:CL全称为Access Control List（访问控制列表），用于控制资源的访问权限。Zookeeper利用ACL策略控制节点的访问权限，如节点数据读写、节点创建、节点删除、读取子节点列表、设置节点权限等。
                        标准格式：scheme:id:permission。
                            schema:代表授权策略
                            id:代表用户
                            permission：代表权限
                        OPEN_ACL_UNSAFE  : 完全开放的ACL，任何连接的客户端都可以操作该属性znode
                        CREATOR_ALL_ACL : 只有创建者才有ACL权限 这个ACL赋予那些授权了的用户具备权限
                        READ_ACL_UNSAFE：只能读取ACL 这个ACL赋予用户读的权限，也就是获取数据之类的权限。
                        https://blog.csdn.net/weixin_40861707/article/details/80403213?utm_medium=distribute.pc_relevant.none-task-blog-baidujs_title-0&spm=1001.2101.3001.4242

                CreateMode createMode：创建的节点类型
                    PERSISTENT：             持久节点，会话结束后不会自动删除节点
                    PERSISTENT_SEQUENTIAL：  持久节点，会话结束后不会自动删除节点，所创建的节点名后会有一个单调递增的数值
                    EPHEMERAL：              临时型类型节点，在回话结束之后，自动删除。
                    EPHEMERAL_SEQUENTIAL :   临时型类型节点，在回话结束之后，自动删除。所创建的临时型节点名后面会有一个单调递增的数值。

            返回创建节点的实际路径
         */
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
        /*
         List<String> getChildren(String path, boolean watch)
            path: 路径
            watch：是否设置默认的Zookeeper对象附带的Watcher 进行监听

        */

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

        /*
            byte[] getData(final String path, Watcher watcher, Stat stat)
                path: 路径
                watcher: 设置当前路径的观察类，
                stat：stat类，保存节点的信息。例如数据版本信息，创建时间，修改时间等信息
         */
        Stat stat = new Stat();
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
        /*
            Stat setData(final String path, byte data[], int version)
                path :znode 路径
                data[] 数据字节数组
                version:  if the given version is -1, it matches any node's versions
            This operation, if successful, will trigger all the watches on the node of the given path left by getData calls.

         */
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
        /*
            delete(final String path, int version)
                version: -1 匹配所有
         */
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
        /*
            Stat：
                private long czxid;             节点创建时的事务id
                private long mzxid;             节点更新时的事务id
                private long ctime;             节点创建时间
                private long mtime;             节点更新时间
                private int version;            当前节点版本号（可以理解为修改次数，每修改一次值+1）
                private int cversion;           子节点版本号（子节点修改次数，每修改一次值+1）
                private int aversion;           当前节点acl版本号（acl节点被修改次数，每修改一次值+1）
                private long ephemeralOwner;    临时节点标示，当前节点如果是临时节点，则存储的创建者的会话id（sessionId），如果不是，那么值=0
                private int dataLength;         节点对应的数据长度
                private int numChildren;        节点对应子节点个数
                private long pzxid;             节点对应父节点的事务id

         */
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


    public ZooKeeper getZookeeper() {
        return this.zookeeper;
    }
}

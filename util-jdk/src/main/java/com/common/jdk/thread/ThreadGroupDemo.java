package com.common.jdk.thread;

/**
 * 线程组的概念
 *
 * https://my.oschina.net/cqqcqqok/blog/1941629
 *
 * @author zhoucg
 * @date 2021-01-20 14:46
 */
public class ThreadGroupDemo {


    public static void main(String[] args) {
        // 主线程对应的线程组
        // 线程组为main父线程组为system
        printGroupInfo(Thread.currentThread());

        //新建线程，系统默认的线程组
        Thread appThread = new Thread(()->{},"appThread");
        printGroupInfo(appThread);//线程组为main父线程组为system


        //自定义线程组
        ThreadGroup factoryGroup=new ThreadGroup("factory");
        Thread workerThread=new Thread(factoryGroup,()->{},"worker");
        printGroupInfo(workerThread);//线程组为factory，父线程组为main


        //设置父线程组
        ThreadGroup deviceGroup=new ThreadGroup(factoryGroup,"device");
        Thread pcThread=new Thread(deviceGroup,()->{},"pc");
        printGroupInfo(pcThread);//线程组为device，父线程组为factory
    }


    static void printGroupInfo(Thread t) {
        ThreadGroup group = t.getThreadGroup();
        System.out.println("thread 【" + t.getName()
                + "】 group name is 【"+ group.getName()
                + "】 max priority is 【" + group.getMaxPriority()
                + "】 thread count is 【" + group.activeCount()
                + "】 parent group is 【"+ (group.getParent()==null?null:group.getParent().getName()) + "】");

        ThreadGroup parent=group;
        do {
            ThreadGroup current = parent;
            parent = parent.getParent();
            if (parent == null) {
                break;
            }
            System.out.println(current.getName() +" Group's  parent group name is 【"+parent.getName()+"】");

        } while (true);
        System.out.println("--------------------------");
    }

}

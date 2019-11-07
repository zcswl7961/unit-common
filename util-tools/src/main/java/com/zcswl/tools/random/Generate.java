package com.zcswl.tools.random;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.UUID;
import java.util.concurrent.*;

/**
 * 生成唯一编码 八位
 *
 * <p>1，random uuid：cb595b98-ecf8-47d0-8742-a4ba43c58cbd
 * <p>2，去除-
 * <p>3，i==> 0-8 截取uuid i*4 - i * 4 + 4 四个字符，转成16进制 x
 * <p>4，x%0x3E(62) 取模，在获取chars
 *
 * <p>在单线程和多线程测试环境下，该随机数据的产生在1亿下没有重复
 * @author zhoucg
 * @date 2019-11-07 17:36
 */
public final class Generate {

    /**
     * 随机数长度
     */
    private static final int LENGTH = 8;

    /**
     * 随机数范围
     */
    private static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    /**
     * Generate unique coding
     * @return unique code
     */
    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid1 = UUID.randomUUID().toString();
        String uuid = uuid1.replace("-", "");
        for (int i = 0; i < LENGTH; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

    public static void main(String[] args) throws InterruptedException {
//        multTest();
        singleTest();
    }

    /**
     * 单线程测试
     */
    private static void singleTest() {
        String str = generateShortUuid();
        System.out.println("原始的str的值："+str);
        int i= 0;
        while(true){
            String newStr = generateShortUuid();
            i++;
            System.out.println("生成的新的值："+newStr+"当前测试次数："+i);
            if(str.equals(newStr)) {
                System.out.println("系统在进行了最终得尝试【"+i+"】之后，系统出现了重复，最终重复:"+newStr);
                break;
            }
        }
        System.out.println("结束："+str);
    }

    private static volatile boolean flag = true;

    /**
     * 多线程测试
     */
    private static void multTest() throws InterruptedException {

        String str = generateShortUuid();
        System.out.println("原始的str的值："+str);
        ThreadFactory testThreadFactory = new ThreadFactoryBuilder().setNameFormat("test-pool-%d").build();
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10,10,
                1L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>(1024),testThreadFactory,new ThreadPoolExecutor.DiscardPolicy());
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for(int i=0;i<10;i++) {
            poolExecutor.execute(() -> {
                int j=0;
                while(true){
                    if(!flag) {
                        return;
                    }
                    String newStr = generateShortUuid();
                    j++;
                    System.out.println("当前线程"+Thread.currentThread().getName()+" 生成的新的值："+newStr+"当前测试次数："+j);
                    if(str.equals(newStr)) {
                        System.out.println("当前线程"+Thread.currentThread().getName()+"系统在进行了最终得尝试【"+j+"】之后，系统出现了重复，最终重复:"+newStr);
                        flag = true;
                        countDownLatch.countDown();
                        break;
                    }
                }
            });
        }
        countDownLatch.await();
        poolExecutor.shutdown();


    }
}

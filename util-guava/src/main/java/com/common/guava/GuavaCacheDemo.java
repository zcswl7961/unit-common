package com.common.guava;

import com.google.common.base.Optional;
import com.google.common.cache.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * google guava Cache实现 :https://www.cnblogs.com/fanguangdexiaoyuer/p/6420929.html
 *
 * <p>CacheLoader 是被 LoadingCache附带构建而成缓存实现，创建自己的CacheLoader通常只需要简单的实现V load()
 *
 * <p>google cache 基于callback形式实现缓存demo的数据参考cache() 方法
 *
 * <p>guava做cache时候数据的移除方式，在guava中数据的移除分为被动移除和主动移除两种。
 * <p>被动移除数据的方式，guava默认提供了三种方式：
 *　　1.基于大小的移除:看字面意思就知道就是按照缓存的大小来移除，如果即将到达指定的大小，那就会把不常用的键值对从cache中移除。
 *　　定义的方式一般为 CacheBuilder.maximumSize(long)，还有一种一种可以算权重的方法，个人认为实际使用中不太用到。就这个常用的来看有几个注意点，
 *　　　　其一，这个size指的是cache中的条目数，不是内存大小或是其他；
 *　　　　其二，并不是完全到了指定的size系统才开始移除不常用的数据的，而是接近这个size的时候系统就会开始做移除的动作；
 *　　　　其三，如果一个键值对已经从缓存中被移除了，你再次请求访问的时候，如果cachebuild是使用cacheloader方式的，那依然还是会从cacheloader中再取一次值，如果这样还没有，就会抛出异常
 *   2.基于时间的移除：guava提供了两个基于时间移除的方法
 *　　　　expireAfterAccess(long, TimeUnit)  这个方法是根据某个键值对最后一次访问之后多少时间后移除
 *　　　　expireAfterWrite(long, TimeUnit)  这个方法是根据某个键值对被创建或值被替换后多少时间移除
 *   3.基于引用的移除：
 　　这种移除方式主要是基于java的垃圾回收机制，根据键或者值的引用关系决定移除
 　　   主动移除数据方式，主动移除有三种方法：
 　　     1.单独移除用 Cache.invalidate(key)
 　　2.批量移除用 Cache.invalidateAll(keys)
 　　3.移除所有用 Cache.invalidateAll()
 　　如果需要在移除数据的时候有所动作还可以定义Removal Listener，但是有点需要注意的是默认Removal Listener中的行为是和移除动作同步执行的，如果需要改成异步形式，可以考虑使用RemovalListeners.asynchronous(RemovalListener, Executor)   (******)
 * @author zhoucg
 * @date 2019-11-09 17:27
 */
public class GuavaCacheDemo {

    public static void main(String[] args) throws Exception {

        //cache();

        RemovalListener<Integer,Student> removalListener = notification ->  System.out.println(notification.getKey() + " was removed, cause is " + notification.getCause());

        CacheLoader<Integer,Student> cacheLoader = new CacheLoader<Integer, Student>() {
            @Override
            public Student load(Integer key) throws Exception {
                System.out.println("load student " + key);
                Student student = new Student();
                student.setId(key);
                student.setName("name " + key);
                return student;
            }
        };

        //缓存接口这里是LoadingCache，LoadingCache在缓存项不存在时可以自动加载缓存
        LoadingCache<Integer,Student> studentCache
                //CacheBuilder的构造函数是私有的，只能通过其静态方法newBuilder()来获得CacheBuilder的实例
                = CacheBuilder.newBuilder()
                //设置并发级别为8，并发级别是指可以同时写缓存的线程数
                .concurrencyLevel(8)
                //设置写缓存后8秒钟过期
                .expireAfterWrite(8, TimeUnit.SECONDS)
                //设置缓存容器的初始容量为10
                .initialCapacity(10)
                //设置缓存最大容量为100，超过100之后就会按照LRU最近最少使用算法来移除缓存项
                .maximumSize(100)
                //设置要统计缓存的命中率
                .recordStats()
                //设置缓存的移除通知
                .removalListener(removalListener).build(cacheLoader);

        for (int i=0;i<20;i++) {
            //从缓存中得到数据，由于我们没有设置过缓存，所以需要通过CacheLoader加载缓存数据
            Student student = studentCache.get(1);
            System.out.println(student);
            //休眠1秒
            TimeUnit.SECONDS.sleep(1);
        }

        System.out.println("cache stats:");
        //最后打印缓存的命中率等 情况
        System.out.println(studentCache.stats().toString());

        /**
         * guava的内部缓存很强大
         *
         * 一次性获取多个key的缓存值
         * studentCache.getAllPresent(Iterable keys)
         *
         * put和putAll方法向缓存中加入一个或者多个缓存值
         *
         * invalidate 和 invalidateAll方法从缓存中移除缓存项
         *
         * asMap()方法获得缓存数据的ConcurrentMap<K, V>快照
         *
         * cleanUp()清空缓存
         *
         * refresh(Key) 刷新缓存，即重新取缓存数据，更新缓存
         */
    }
    private static class Student{
        private Integer id;
        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    /**
     * google guava cache 基于callback的形式实现缓存demo
     * @throws ExecutionException
     */
    private static void cache() throws Exception {

        System.out.println(Thread.currentThread().getName()+"_cache get_1:"+get("t1").orNull());
        System.out.println(Thread.currentThread().getName()+"_cache get_2:"+get("t1").orNull());
        clearAll();
        System.out.println(Thread.currentThread().getName()+"_cache get_3:"+get("t1").orNull());
        System.out.println(Thread.currentThread().getName()+"_cache get_4:"+get("t1").orNull());

        System.out.println(Thread.currentThread().getName()+"caches_map:"+viewCache());
    }

    private static  Cache<String, Optional<String>> cache = CacheBuilder.newBuilder()
            //.refreshAfterWrite(1, TimeUnit.HOURS)   //写入一小时后自动刷新
            //.expireAfterAccess(1, TimeUnit.HOURS)// 最后一次访问之后多少时间后移除，即失效
            //最后一次写入之后多少时间后移除，即失效
            .expireAfterWrite(5, TimeUnit.MINUTES)
            //最大缓存记录数
            .maximumSize(1000)
            .build();



    private static  Optional<String> get(String key) throws Exception{
        Optional<String> data = Optional.fromNullable(null);
        data = cache.get(key,  new Callable<Optional<String>>() {
            @Override
            public Optional<String> call() throws Exception {
                List<String> datas=new ArrayList<String>();
                datas.add("data1111");
                datas.add("data2222");
                System.out.println(Thread.currentThread().getName()+"_未经过缓存获取数据！！！！");
                return Optional.fromNullable(datas.get(0));
            }
        });
        return data;
    }

    /**
     * 清空缓存内数据
     * 操作基础数据时，清除重新查询
     */
    public static void clearAll(){
        //全部清除缓存
        cache.invalidateAll();
    }

    /**
     * 查看缓存中内容
     */
    public static ConcurrentMap<String, Optional<String>> viewCache(){
        //查看内容
        return cache.asMap();
    }
}

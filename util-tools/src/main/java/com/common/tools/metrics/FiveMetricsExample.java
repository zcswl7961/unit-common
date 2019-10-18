package com.common.tools.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhoucg on 2019-01-28.
 *
 * Metrics Registries类似一个metrics容器，维护一个Map，可以是一个服务一个实例。
 * 支持五种metric类型：Gauges、Counters、Meters、Histograms和Timers。
 * 可以将metrics值通过JMX,Console，CSV，和 SLF4J loggers 发布出去
 *
 * https://www.cnblogs.com/yangecnu/p/Using-Metrics-to-Profiling-WebService-Performance.html
 * https://www.cnblogs.com/nexiyi/p/metrics_sample_1.html
 */
public class FiveMetricsExample {



    /**
     * 实例化一个registry，最核心的一个模块，相当于一个应用程序的metrics系统的容器，维护一个Map
     */
    private static final MetricRegistry metrics = new MetricRegistry();

    private static Queue<String> queue = new LinkedBlockingDeque<>();

    /**
     * 表示将计算出来的指标打印到console中
     */
    private static ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).build();

    /**
     * 实例化一个Meter
     */
    private static final Meter request = metrics.meter(MetricRegistry.name(FiveMetricsExample.class,"request"));


    /**
     * Gauges是一个最简单的计量，一般用来统计瞬时状态的数据信息，比如系统中处于pending状态的job。测试代码
     * @throws Exception
     */
    public static void gauges() throws Exception {

        reporter.start(3, TimeUnit.MILLISECONDS);

        //示例一个Gauges
        //实例化一个Gauge
        Gauge<Integer> gauge = new Gauge<Integer>() {
            @Override
            public Integer getValue() {
                return queue.size();
            }
        };

        //注册到容器中
        metrics.register(MetricRegistry.name(FiveMetricsExample.class, "pending-job", "size"), gauge);

        //测试JMX
//        JmxReporter jmxReporter = JmxReporter.forRegistry(metrics).build();
//        jmxReporter.start();



        //模拟数据
        for (int i=0; i<20; i++){
            queue.add("a");
            Thread.sleep(1000);
        }
    }


    /**
     * Meters用来度量某个时间段的平均处理次数（request per second），每1、5、15分钟的TPS。比如一个service的请求数，
     * 通过metrics.meter()实例化一个Meter之后，然后通过meter.mark()方法就能将本次请求记录下来。
     * 统计结果有总的请求数，平均每秒的请求数，以及最近的1、5、15分钟的平均TPS。
     *
     * TPS：是Transactions Per Second的缩写，也就是事务数/秒。它是软件测试结果的测量单位。一个事务是指一个客户机向服务器发送请求然后服务器做出反应的过程。
     * 客户机在发送请求时开始计时，收到服务器响应后结束计时，以此来计算使用的时间和完成的事务个数。
     * QPS：是Queries Per Second的缩写，意思是每秒查询率，是一台服务器每秒能够相应的查询次数，是对一个特定的查询服务器在规定时间内所处理流量多少的衡量标准。
     * @throws Exception
     *
     * 下面的示例用来度量请求handleRequest的每秒的处理次数
     */
    public static void main(String[] args) throws Exception{

        reporter.start(10,TimeUnit.SECONDS);

        while(true) {
            handleRequest();
            Thread.sleep(100);
        }
    }

    private static void handleRequest() {
        request.mark();
    }



}

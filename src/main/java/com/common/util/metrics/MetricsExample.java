package com.common.util.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhoucg on 2019-01-28.
 * metrics 是一个开源的统计工具包
 */
public class MetricsExample {

    static final MetricRegistry metrics = new MetricRegistry();

    public static void main(String[] args) {
        startReport();
        metrics.meter("requests").mark();
        wait5Second();
}

    static void startReport() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
    }

    static void wait5Second() {
        try {
            Thread.sleep(5*1000);
        }
        catch(InterruptedException e) {}
    }
}

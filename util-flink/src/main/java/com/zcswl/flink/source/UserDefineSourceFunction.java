package com.zcswl.flink.source;

import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.table.planner.expressions.Rand;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 自定义source function
 * @author xingyi
 * @date 2022/1/12
 */
public class UserDefineSourceFunction implements SourceFunction<UserDefineSourceFunction.Sensor> {

    private volatile boolean flag = true;

    private static final Random random = new Random();

    @Override
    public void run(SourceContext<Sensor> ctx) throws Exception {
        Map<String, Double> sensorMap = new HashMap<>(16);
        for (int i = 0; i < 10; i++) {
            sensorMap.put("sensor_" + (i+1), 60 + random.nextGaussian() * 20);
        }

        while (flag) {
            for (String sensorId: sensorMap.keySet()) {
                Double template = sensorMap.get(sensorId);
                Sensor sensor = new Sensor(sensorId, template + random.nextGaussian(), System.currentTimeMillis());
                ctx.collect(sensor);
            }
            Thread.sleep(200);
        }
    }

    @Override
    public void cancel() {
        flag = false;
    }

    public static class Sensor {
        private String sensorId;
        private Double template;
        private Long timestamp;


        public String getSensorId() {
            return sensorId;
        }

        public void setSensorId(String sensorId) {
            this.sensorId = sensorId;
        }

        public Double getTemplate() {
            return template;
        }

        public void setTemplate(Double template) {
            this.template = template;
        }

        public Long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }

        public Sensor(String sensorId, Double template, Long timestamp) {
            this.sensorId = sensorId;
            this.template = template;
            this.timestamp = timestamp;
        }
    }
}


package com.zcswl.flink.job;

import org.apache.flink.annotation.PublicEvolving;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhoucg
 * @date 2021-02-24 11:06
 */
@PublicEvolving
@SuppressWarnings("unused")
public class AlertSinkWorkCount implements SinkFunction<WordCount.WordWithCount> {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(AlertSinkWorkCount.class);

    @Override
    public void invoke(WordCount.WordWithCount value, Context context) throws Exception {
        LOG.info("zhoucg:"+value.toString());
    }
}

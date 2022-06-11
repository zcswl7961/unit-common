package com.zcswl.flink.source;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

/**
 * https://blog.csdn.net/zhuzuwei/article/details/107137527
 *
 * RichParallelSourceFunction
 * RichInputFormat
 *
 *
 * RichSourceFunction 内部使用RichInputFormat进行source生成
 * 自定义source
 *
 *
 * 带并行度操作的RichSourceFunction
 * @author xingyi
 * @date 2022/5/21
 */
public class UserDefinedRichParallelSourceFunction<OUT> extends RichParallelSourceFunction<OUT> {
    @Override
    public void run(SourceContext<OUT> sourceContext) throws Exception {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
    }

    @Override
    public void close() throws Exception {
        super.close();
    }


}

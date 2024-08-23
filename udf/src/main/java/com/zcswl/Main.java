package com.zcswl;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * @author xingyi
 * @date ${DATE}
 */
public class Main extends UDF{

    public String evaluate(String source) {
        if (source == null || source.length() == 0) {
            return "EMP";
        }
        return "zhoucg :" + source;
    }
}
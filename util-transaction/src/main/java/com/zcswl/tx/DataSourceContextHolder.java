package com.zcswl.tx;

/**
 * 主（从） 数据库切换操作
 * @author zhoucg
 * @date 2019-12-30 15:23
 */
public class DataSourceContextHolder {

    private static ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<>();


    public static void set(DataSourceType dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    public static DataSourceType get() {
        DataSourceType type = contextHolder.get();
        if(type == null) {
            return DataSourceType.WRITE;
        }
        return type;
    }

    public static void remove(){
        contextHolder.remove();
    }
}



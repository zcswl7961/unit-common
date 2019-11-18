package com.zcswl.db.logic;

import java.util.Date;

/**
 * @author zhoucg
 * @date 2019-11-15 16:45
 */
public interface VersionManager {

    /**
     * 获取系统版本号
     * @return
     */
    String getVersionNum();

    /**
     * 获取系统构建日期号
     * @return
     */
    Date getBuildDate();

    /**
     * 获取数据库版本号
     * @return
     */
    String getDBVersionNum();

    /**
     * 获取数据库构建日期号
     * @return
     */
    Date getDBBuildDate();

    /**
     * 检查数据库是否需要初始化与升级，如需要则进行相应的操作
     * @throws Exception
     */
    void smartUpdate() throws Exception;

    /**
     * 判断当前数据库是否需要初始化
     * @return
     */
    boolean isNeedInit();

    /**
     * 判断当前数据库是否需要升级
     */
    boolean isNeedUpdate();
}

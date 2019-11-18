package com.zcswl.db.dao;

import com.zcswl.db.entity.VersionRecord;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author zhoucg
 * @date 2019-11-15 16:48
 */
public interface VersionDao {

    /**
     * 判断当前数据库是否支持version标记。
     *
     * @return 如果支持返回true，否则返回false
     */
    boolean isVersionSupported();

    /**
     * 验证数据库连接，如果连接失败则抛出异常。
     *
     * @param validationQuery 用于验证的select语句
     */
    void validateConnection(String validationQuery) throws SQLException;

    /**
     * 新增版本记录信息。
     *
     * @param versionInfo 版本记录信息
     * @throws SQLException
     */
    void addVsersionInfo(VersionRecord versionInfo) throws SQLException;

    /**
     * 返回指定的版本记录信息。
     *
     * @param id 版本记录ID
     * @return 版本记录信息
     * @throws SQLException
     */
    VersionRecord getVersionInfo(String id) throws SQLException;

    /**
     * 返回所有版本记录信息。
     *
     * @param condition 匹配所有有值的字段
     * @return 版本记录列表，找不到记录时返回空列表，不能返回null
     * @throws SQLException
     */
    List<VersionRecord> selectVersionInfo(VersionRecord condition) throws SQLException;

    /**
     * 更新版本记录信息。
     *
     * @param versionInfo 要更新的版本记录信息
     * @throws SQLException
     */
    void updateVersionInfo(VersionRecord versionInfo) throws SQLException;

    /**
     * 数据库是否初始化过，通过判断第一个表是否存在来实现。
     *
     * @return 如果初始化过返回true，否则返回false
     */
    boolean isDBInited();

    /**
     * 返回数据库连接。
     *
     * @return 数据库连接
     */
    Connection getConnection() throws SQLException;
}

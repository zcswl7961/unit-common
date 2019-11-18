package com.zcswl.db.logic;

import com.zcswl.db.dao.VersionDao;
import com.zcswl.db.entity.Parameter;
import com.zcswl.db.entity.VersionRecord;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhoucg
 * @date 2019-11-15 16:46
 */
@Slf4j
public class VersionManagerNonSpringImpl implements VersionManager{

    public final static String versionTag = "--version ";

    private VersionDao versionDao;

    /**
     * 主数据库脚本升级文件
     */
    private File dbScriptIndex;

    /**
     * 配置对应的参数信息
     */
    private Parameter configParam;

    /**
     * 待升级的目标版本对象。
     */
    private VersionNumExpr targetVersion = null;

    /**
     * 日志前缀。
     */
    private String logPrefix = "";


    public VersionManagerNonSpringImpl(Parameter configParam, VersionDao versionDao, File dbScriptIndex) {
        this.configParam = configParam;
        this.versionDao = versionDao;
        this.dbScriptIndex = dbScriptIndex;
        this.logPrefix = configParam.getLogPrefix();
        if (configParam.getVersionNumber() != null) {
            this.targetVersion = new VersionNumExpr(configParam.getVersionNumber());
        }
    }

    /**
     * 初始化方法。
     *
     * @throws Exception
     */
    public void init() throws Exception {
        if (dbScriptIndex == null) {
            throw new Exception("待升级的数据库脚本文件没有指定！");
        }
        smartUpdate();
    }

    /*
     * 测试数据库连接。
     */
    private void validateConnection() {
        String validationQuery = configParam.getValidationQuery();
        int validationCount = configParam.getValidationCount();
        int validationInterval = configParam.getValidationInterval();

        if (validationQuery == null) {
            log.error("请配置用于验证数据库连接的SQL！示例：<property name=\"validationQuery\" value=\"select 1 from dual\" />");
        }

        // 验证版本控制数据库
        for (int i = 0; i < validationCount; i++) {
            try {
                versionDao.validateConnection(validationQuery);
                break;
            } catch (SQLException e) {
                if (i < validationCount - 1) {
                    log.error("连接版本控制数据库失败，" + validationInterval + "秒后做第" + (i + 2) + "次尝试...", e);
                    try {
                        Thread.sleep(validationInterval * 1000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    log.error("连接版本控制数据库" + validationCount + "次均失败，退出版本控制模块...");
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void smartUpdate() throws Exception {
        validateConnection();
        Connection conn = null;
        try {
            conn = versionDao.getConnection();
            doUpdate(conn);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }

    private void doUpdate(Connection conn) throws Exception {
        StringBuffer result = new StringBuffer();
        //需要初始化
        if (isNeedInit()) {
            log.info("系统数据库为空，进行初始化.......");
            try {
                upgrade(conn);
            } catch (Exception ex) {
                log.info(result.toString());
                String msg = "数据库初始化错误！";
                throw new Exception(msg, ex);
            }

            // 保存版本信息
            versionDao.addVsersionInfo(new VersionRecord(configParam.getVersionId(), configParam.getVersionNumber(), configParam.getBuildDate()));

            //需要升级
        } else if (isNeedUpdate()) {
            try {
                upgrade(conn);
            } catch (Exception ex) {
                String msg = "数据库智能升级错误，请检查数据库脚本文件中的版本标识！";
                throw new Exception(msg, ex);
            }

            // 更新版本记录
            versionDao.updateVersionInfo(new VersionRecord(configParam.getVersionId(), configParam.getVersionNumber(), configParam.getBuildDate()));

        } else {
        }
    }

    /*
     * 进行数据库脚本升级处理。
     */
    protected void upgrade(Connection conn) throws Exception {
        String updateSQL = getUpdateSQL(dbScriptIndex);
        if(updateSQL == null || updateSQL.length() == 0) {
            return;
        }

        final PipedOutputStream pout = new PipedOutputStream();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(new PipedInputStream(pout)));

        //输出到日志
        Thread output = new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        String str = reader.readLine();
                        if (str != null) {
                            if (str.startsWith("<警告>")) {
                            } else {
                            }
                        } else {
                            break;
                        }
                    }
                } catch (Exception ex) {
                }
            }
        });
        output.setName("DBUpgradeOutputThread");
        output.start();

        //下面进行升级
        try {
            SQLExec exec = new SQLExec();
            exec.setAutocommit(false);
            exec.setPrint(true);
            exec.setOutput(pout);
            exec.setConnection(conn);
            exec.setSqlFileCharsetName(configParam.getSqlFileCharsetName());
            SQLExec.OnError onError = new SQLExec.OnError();
            onError.setValue(SQLExec.OnError.CONTINUE);
            exec.setOnerror(onError);
            exec.addCommentPrefix("--");
            exec.addCommentPrefix("//");
            exec.addCommentPrefix("#");
            exec.addCommentPrefix("/*");
            exec.addCommentPrefix("*/");
            exec.setDelimiter(";");
            exec.setSqlBlockStartTag(configParam.getSqlBlockStartTag());
            exec.setSqlBlockEndTag(configParam.getSqlBlockEndTag());
            exec.setImportSqlDelimiter(configParam.getImportSqlDelimiter());
            exec.addMissionText(updateSQL);
            exec.setSqlFileBaseDir(dbScriptIndex.getParent()); //SQL脚本的上级目录
            exec.execMission();


            pout.close();
            //等待输出线程执行完毕
            output.join();

        } finally {
            if (pout != null) {
                pout.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
    }

    /*
     * @see com.broada.module.version.logic.VersionManager#isNeedInit()
     */
    public boolean isNeedInit() {
        return !versionDao.isDBInited();
    }

    /*
     * @see com.broada.module.version.logic.VersionManager#isNeedUpdate()
     */
    public boolean isNeedUpdate() {
        // 当前数据库中保存的版本
        VersionRecord versionInfo = null;
        try {
            versionInfo = versionDao.getVersionInfo(configParam.getVersionId());
        } catch (SQLException e) {
        }
        if (versionInfo == null) {
            return true;
        }

        // 未指定目标版本号，根据SQL文件上的版本信息来决定是否需要升级
        if (targetVersion == null || configParam.getBuildDate() == null) {
            targetVersion = new VersionNumExpr(getVersionNum()); // 强制获取最新版本
        }

        VersionNumExpr dbVersionNumExpr = new VersionNumExpr(versionInfo.getVersionNum());
        int compare = targetVersion.compareTo(dbVersionNumExpr);

        if (compare > 0) {
            return true;
        } else if (compare < 0) {
            String msg = "系统版本错误，待升级版本[ " + targetVersion + " ]，低于当前版本[ " + dbVersionNumExpr + " ]！";
            throw new RuntimeException(msg);
        }

        Date dbBuildDate = versionInfo.getBuildDate();
        compare = VersionChecker.compareDate(configParam.getBuildDate(), dbBuildDate);
        if (compare > 0) {
            return true;
        } else if (compare < 0) {
        }
        return false;
    }

    /*
     * 根据当前数据库的版本以及指定的数据库版本（在system.properties文件中指定），读取需要升级的脚本。
     */
    private String getUpdateSQL(File scriptIndexFile) throws Exception {
        StringBuffer updateSQL = new StringBuffer();
        VersionChecker.VersionCheckResult versionCheckResult = null;
        BufferedReader br = null;

        VersionNumExpr dbVersionNumExpr = null;
        Date dbBuildDate = null;

        boolean hasDbInited = versionDao.isDBInited();
        // 目标数据库指明需要初始化
        if (!hasDbInited) {	// 数据库没有初始化，全部执行
            dbVersionNumExpr = new VersionNumExpr("0.0.0.0");
            dbBuildDate = new java.sql.Date(0);

            // 如果不存在版本记录
        } else if (!versionDao.isVersionSupported() || versionDao.selectVersionInfo(null).isEmpty()) {
            // 版本信息没有，至少从第一个有版本tag的地方开始执行
            dbVersionNumExpr = new VersionNumExpr("0.0.0.0");
            dbBuildDate = new java.sql.Date(0);

            // 存在版本记录
        } else {
            VersionRecord versionRecord = versionDao.getVersionInfo(configParam.getVersionId());
            // 指定的数据库版本记录存在
            if (versionRecord != null) {
                dbBuildDate = versionRecord.getBuildDate();
                dbVersionNumExpr = new VersionNumExpr(versionRecord.getVersionNum());

                // 目标版本数据库不需要初始化，而又不存在版本记录
            } else {
                throw new IllegalStateException("指定的版本记录[id = " + configParam.getVersionId() + " ]不存在。");
            }
        }

        VersionChecker versionChecker = new VersionChecker();

        try {
            String sqlFileCharsetName = configParam.getSqlFileCharsetName();
            if (sqlFileCharsetName == null || sqlFileCharsetName.trim().length() == 0) {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(scriptIndexFile)));
            } else {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(scriptIndexFile), sqlFileCharsetName));
            }
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }

                // --version的方式打头的行
                if (line.startsWith(versionTag)) { //进行版本控制

                    // 如果为老的版本控制方式，即“--version 3.6.0,build 2011-01-01”形式
                    if (VersionChecker.isOldVersionTag(line)) {
                        versionCheckResult = versionChecker.checkByOldPattern(line, this.targetVersion, dbVersionNumExpr, configParam.getBuildDate(), dbBuildDate);

                        //否则尝试使用新的版本控制方式
                    } else {
                        versionCheckResult = versionChecker.checkByNewPattern(line, dbVersionNumExpr, dbBuildDate);
                    }
                }

                if (versionCheckResult != null && versionCheckResult.isNeedUpate() || !hasDbInited) { //如果数据库还未被初始化,则需要执行全部SQL
                    updateSQL.append(line).append("\n");
                }

            } // while

            // 检查结果中保存的版本作为升级目标的版本
            if (configParam.getVersionNumber() == null || configParam.getBuildDate() == null) {
                configParam.setVersionNumber(versionCheckResult.getVersionNum().toString());
                //YYYY-MM-DD格式的数据信息
                configParam.setBuildDate(versionCheckResult.getBuildDate().toString());
            }
            return updateSQL.toString();
        } catch (FileNotFoundException ex) {
            throw new Exception("读取系统数据库升级的脚本文件[" + scriptIndexFile + " ]时出现错误,请检查该文件是否有效!\n", ex);
        } catch (Exception ex) {
            throw new Exception("获取升级脚本信息时出现错误,可能是脚本格式不正确,请联系管理员.", ex);
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ex) {
            }
        }
    }

    public String getVersionNum() {
        // 如果未指定升级目标版本,则获取当前SQL文件中的最新版本号
        if (configParam.getVersionNumber() == null) {
            try {
                getUpdateSQL(dbScriptIndex); // 强制获取最新版本
            } catch (Exception e) {
                return null;
            }
        }
        return configParam.getVersionNumber();
    }

    public Date getBuildDate() {
        // 如果未指定升级目标版本,则获取当前SQL文件中的最新构建日期
        if (configParam.getVersionNumber() == null) {
            getVersionNum(); // 强制获取最新版本
        }
        return configParam.getBuildDate();
    }

    /*
     * @see java.lang.Object#toString()
     */
    public String toString() {
        if (getBuildDate() == null) {
            return "Version " + getVersionNum();
        }
        return "Version " + getVersionNum() + ",build " + getDateString(getBuildDate());
    }

    /*
     * @see com.broada.module.version.logic.VersionManager#getDBVersionNum()
     */
    public String getDBVersionNum() {
        VersionRecord vr = null;
        try {
            vr = versionDao.getVersionInfo(configParam.getVersionId());
        } catch (SQLException e) {
            log.error("获取版本信息错误", e);
        }
        if (vr != null) {
            return (new VersionNumExpr(vr.getVersionNum())).toString();
        } else {
            return null;
        }
    }

    /*
     * @see com.broada.module.version.logic.VersionManager#getDBBuildDate()
     */
    public java.util.Date getDBBuildDate() {
        VersionRecord vr = null;
        try {
            vr = versionDao.getVersionInfo(configParam.getVersionId());
        } catch (SQLException e) {
        }
        return vr != null ? vr.getBuildDate() : null;
    }

    private String getDateString(Date date) {
        if (date == null)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}

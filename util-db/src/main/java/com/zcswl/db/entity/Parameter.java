package com.zcswl.db.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author zhoucg
 * @date 2019-11-15 16:22
 */
@Slf4j
@Data
public class Parameter {

    /**
     * 默认的语句块的缺省开始标记 。
     */
    public final static String SQL_BLOCK_START_TAG = "--<{";

    /**
     * 默认的语句块的缺省结束标记。
     */
    public final static String SQL_BLOCK_END_TAG = "--}>";

    /**
     * 默认的日志前缀。
     */
    public final static String LOG_PREFIX = "系统版本控制";

    /**
     * 升级目标数据库名称
     */
    private String targetDBName;

    /**
     * 数据库版本记录ID，用于定位已保存的版本记录
     */
    private String versionId;

    /**
     * 升级目标版本号，当大于版本记录对应的版本号时，表示可以升级
     */
    private String versionNumber;


    /**
     * 升级目标构建日期，当大于版本记录对应的构建日期时，表示可以升级
     */
    private Date buildDate;

    /**
     * 日志记录前缀
     */
    private String logPrefix = LOG_PREFIX;

    /**
     * 语句块的开始标记.(系统使用String.startsWith进行判断)
     */
    private String sqlBlockStartTag = SQL_BLOCK_START_TAG;

    /**
     * 语句块的结束标记.(系统使用String.startsWith进行判断)
     */
    private String sqlBlockEndTag = SQL_BLOCK_END_TAG;

    /**
     * SQL文件的字符集
     */
    private String sqlFileCharsetName;

    /**
     * 数据库脚本中，导入SQL语句的分隔符：用于分割导入命令符、SQL文件名、起始以及结束版本
     * 例如，
     * 以空格为分隔符的例子：--import aa.sql 3.0.112233 3.0.223333
     * 以#号为分隔符的例子：--import#aa.sql#3.0.112233#3.0.223333
     */
    private String importSqlDelimiter = "#";

    /**
     * 用于验证数据库连接的select语句
     */
    private String validationQuery = "select 1 from dual";

    /**
     * 最大验证次数（验证失败时重试，但总计验证次数不超过validationCount）
     */
    private int validationCount = 2;

    /**
     * 验证间隔（验证失败时重试，重试间隔为validationInterval，单位为秒）
     */
    private int validationInterval = 30;

    public Parameter(String versionId) {
        if(ObjectUtils.isEmpty(versionId)) {
            throw new IllegalArgumentException("parameter 1 [ versionId ]  is required!");
        }
        this.versionId = versionId;
    }

    public Parameter(String versionId,File databaseFile) {
        String lastVersion = null;
        String versionNumber = null;
        String buildDate = null;
        try{
            List<String> readLines = FileUtils.readLines(databaseFile, "UTF-8");
            for(String line : readLines) {
                if(line.startsWith("--version")) {
                    lastVersion = line;
                }
            }

            String[] controllerInfo = StringUtils.split(lastVersion);
            if (controllerInfo.length != 4) {
                throw new RuntimeException(
                        String.format("数据库初始化出错，schema.sql版本格式异常，%s", new Object[] { lastVersion }));
            }

            versionNumber = controllerInfo[1].split(",")[0];
            buildDate = controllerInfo[2];
        } catch (IOException e) {
            log.error("省级数据库错误，当前错误信息：{}",e);
        }
        this.versionId = versionId;
        this.versionNumber = versionNumber;
        this.sqlFileCharsetName = "UTF-8";
        setBuildDate(buildDate);
    }

    public void setBuildDate(String buildDate) {
        if(ObjectUtils.isEmpty(buildDate)) {
            return;
        }
        try{
            this.buildDate = DateUtils.parseDate(buildDate,"YYYY-MM-DD");
        } catch (ParseException e) {
            throw new IllegalArgumentException("版本构建日期[ " + buildDate + " ]格式错误，必须为为 yyyy-mm-dd 格式。");
        }
    }

    public Parameter(String versionId, String versionNumber, String buildDate,String sqlFileCharsetName) {
        if (versionId == null || versionId.trim().length() == 0) {
            throw new IllegalArgumentException("parameter 1 [ versionId ]  is required!");
        }
        this.versionId = versionId;
        this.versionNumber = versionNumber;
        this.sqlFileCharsetName = sqlFileCharsetName;
        setBuildDate(buildDate);
    }

}

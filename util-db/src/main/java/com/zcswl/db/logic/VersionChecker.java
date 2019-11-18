package com.zcswl.db.logic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * @author zhoucg
 * @date 2019-11-15 17:47
 */
public class VersionChecker {

    private static final Log logger = LogFactory.getLog(VersionChecker.class);

    //老的版本控制行的格式： --version build, 2011-01-01 xxxx
    private static Pattern oldVersionTagPattern = Pattern.compile("^--version\\s[0-9\\.]+,build\\s.*");

    /**
     * 根据旧的版本标识规则判断当前SQL行是否需要加入执行队列。
     * --version 1.1.1,build 2006-04-12
     */
    public VersionCheckResult checkByOldPattern(String versionLine, VersionNumExpr versionNumExpr,
                                                VersionNumExpr dbVersionNumExpr, Date buildDate, Date dbBuildDate) {

        StringTokenizer st = new StringTokenizer(versionLine, ", "); // ',' or ' '
        if (st.countTokens() < 4) {
            return new VersionCheckResult(false, null, null);
        }
        VersionNumExpr sqlVerNumExpr = null;
        Date date = null;

        st.nextToken(); // --version
        String ver = st.nextToken();
        try {
            sqlVerNumExpr = new VersionNumExpr(ver);
        } catch (Exception e) {
            return new VersionCheckResult(false, null, null);
        }
        st.nextToken(); // build
        String d = st.nextToken();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = new Date(sdf.parse(d).getTime());
        } catch (ParseException ex) {
            return new VersionCheckResult(false, null, null);
        }
        //当前SQL版本大于数据库版本，需要升级
        if (sqlVerNumExpr.compareTo(dbVersionNumExpr) > 0
                || (sqlVerNumExpr.compareTo(dbVersionNumExpr) == 0 && compareDate(date, dbBuildDate) > 0)) {
            return new VersionCheckResult(true, sqlVerNumExpr, date);
        }

        //升级目标数据库版本一定大于或等于当前数据库版本，否则通不过升级组件的验证

        // 如果指定了升级目标版本
        if (versionNumExpr != null && buildDate != null) {
            //当前SQL版本大于升级目标数据库版本，不升级
            if (sqlVerNumExpr.compareTo(versionNumExpr) > 0
                    || (sqlVerNumExpr.compareTo(versionNumExpr) == 0 && compareDate(date, buildDate) > 0)) {
                return new VersionCheckResult(false, sqlVerNumExpr, date);
            }
        }

        return new VersionCheckResult(false, sqlVerNumExpr, date);
    }

    /**
     * 根据新的build规则进行判断，版本和build日期合并。
     * --version 20120309
     */
    public VersionCheckResult checkByNewPattern(String versionLine, VersionNumExpr dbVersionNumExpr, Date dbBuildDate) {
        String[] tokens = versionLine.split("\\s");
        if (tokens.length < 2) {
            return new VersionCheckResult(false, null);
        }

        VersionNumExpr sqlVerNum = null;

        try {
            sqlVerNum = new VersionNumExpr(tokens[1]); //yyMMdd格式，可以转为int型
        } catch (Exception e) {
            return new VersionCheckResult(false, null);
        }

        // 不需要比较build日期，新的版本标识规则中，不再使用build日期
        if (sqlVerNum.compareTo(dbVersionNumExpr) > 0) {
            return new VersionCheckResult(true, sqlVerNum);
        }

        return new VersionCheckResult(false, sqlVerNum);
    }

    /**
     * 比较两个日期的大小。
     *
     * @param d1 日期1
     * @param d2 日期2
     * @return 如果日期1大，则返回1，相等返回0，否则返回-1
     */
    public static int compareDate(Date d1, Date d2) {
        if (d1 == null && d2 == null) {
            return 0;
        } else if (d1 == null) {
            return -1;
        } else if (d2 == null) {
            return 1;
        }
        return d1.compareTo(d2);
    }

    /**
     * 检查指定的字符串是否为旧的版本控制行。
     *
     * @param tagLine 字符串行
     * @return 是或者否
     */
    public static boolean isOldVersionTag(String tagLine) {
        return oldVersionTagPattern.matcher(tagLine).matches();
    }


    /*
     * 保存版本检查结果信息。
     */
    public static class VersionCheckResult {

        /*
         * 是否需要升级
         */
        private boolean needUpate;

        /*
         * 本次检查时从SQL的版本控制行所得到的版本号
         */
        private VersionNumExpr versionNum;

        /*
         * 本次检查时从SQL的版本控制行所得到的构建日期
         * 如果采用新的版本号，则该字段为null
         */
        private Date buildDate;

        public VersionCheckResult(boolean needUpate, VersionNumExpr versionNum) {
            this.needUpate = needUpate;
            this.versionNum = versionNum;
        }

        public VersionCheckResult(boolean needUpate, VersionNumExpr versionNum, Date buildDate) {
            this.needUpate = needUpate;
            this.versionNum = versionNum;
            this.buildDate = buildDate;
        }

        public boolean isNeedUpate() {
            return needUpate;
        }

        public VersionNumExpr getVersionNum() {
            return versionNum;
        }

        public Date getBuildDate() {
            return buildDate;
        }
    }
}

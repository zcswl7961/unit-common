package com.zcswl.db.logic;


import com.zcswl.db.entity.Parameter;
import com.zcswl.db.util.BeanUtil;
import com.zcswl.db.util.BeanUtilException;
import com.zcswl.db.util.ClassUtil;

import java.io.*;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

/**
 * @author zhoucg
 * @date 2019-11-15 17:37
 */
public class SQLExec {

    /** 语句块的开始标记.(系统使用String.startsWith进行判断) */
    private String sqlBlockStartTag = Parameter.SQL_BLOCK_START_TAG;

    /** 语句块的结束标记.(系统使用String.startsWith进行判断) */
    private String sqlBlockEndTag = Parameter.SQL_BLOCK_END_TAG;

    private int goodSql = 0, totalSql = 0;

    private Vector fileset = new Vector();

    private Connection conn = null;

    private boolean autocommit = false;

    private Statement statement = null;

    private String driver = null;

    private String url = null;

    private String userId = null;

    private String password = null;

    private String sqlCommand = "";

    private Vector transactions = new Vector();

    private String delimiter = ";";

    private String delimiterType = DelimiterType.NORMAL;

    private boolean print = false;

    private boolean showheaders = true;

    private PrintStream out = System.out;

    private String onError = OnError.ABORT;

    private int missionCount = 0;

    // 增加自定义注释前缀
    private List<String> commentPrefixs = new ArrayList<String>();

    public void addCommentPrefix(String prefix) {
        this.commentPrefixs.add(prefix);
    }

    private String[] getCommentPrefixs() {
        if (commentPrefixs.isEmpty()) {
            commentPrefixs.add("--"); // 增加默认的注释符
            commentPrefixs.add("//");
        }
        return (String[]) commentPrefixs.toArray(new String[commentPrefixs.size()]);
    }

    // SQL文件的字符集,为null则不设定
    private String sqlFileCharsetName;

    public String getSqlFileCharsetName() {
        return sqlFileCharsetName;
    }

    public void setSqlFileCharsetName(String sqlFileCharsetName) {
        this.sqlFileCharsetName = sqlFileCharsetName;
    }

    // SQL文所在的目录
    private String sqlFileBaseDir;

    public String getSqlFileBaseDir() {
        return sqlFileBaseDir;
    }

    public void setSqlFileBaseDir(String sqlFileBaseDir) {
        if (sqlFileBaseDir != null && !sqlFileBaseDir.endsWith(File.separator)) {
            sqlFileBaseDir += File.separator;
        }
        this.sqlFileBaseDir = sqlFileBaseDir;
    }

    // 导入SQL语句的分隔符：用于分割导入命令符、SQL文件名、起始以及结束版本
    private String importSqlDelimiter = "#";

    public String getImportSqlDelimiter() {
        return importSqlDelimiter;
    }

    public void setImportSqlDelimiter(String importSqlDelimiter) {
        if (importSqlDelimiter != null && importSqlDelimiter.length() > 0) {
            this.importSqlDelimiter = importSqlDelimiter;
        }
    }

    public void addMissionText(String sql) {
        this.sqlCommand += sql;
    }

    public void addMissionFile(File file) {
        fileset.addElement(file);
    }

    public Transaction createTransaction() {
        Transaction t = new Transaction();
        transactions.addElement(t);
        return t;
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    public Connection getConnection() {
        return conn;
    }

    public void setAutocommit(boolean autocommit) {
        this.autocommit = autocommit;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public void setDelimiterType(DelimiterType delimiterType) {
        this.delimiterType = delimiterType.getValue();
    }

    public void setPrint(boolean print) {
        this.print = print;
    }

    public void setShowheaders(boolean showheaders) {
        this.showheaders = showheaders;
    }

    public void setOutput(OutputStream output) {
        if (output != null) {
            out = new PrintStream(new BufferedOutputStream(output), true);
        }
    }

    public void setOnerror(OnError action) {
        this.onError = action.getValue();
    }

    public String getSqlBlockEndTag() {
        return sqlBlockEndTag;
    }

    public void setSqlBlockEndTag(String sqlBlockEndTag) {
        this.sqlBlockEndTag = sqlBlockEndTag;
    }

    public String getSqlBlockStartTag() {
        return sqlBlockStartTag;
    }

    public void setSqlBlockStartTag(String sqlBlockStartTag) {
        this.sqlBlockStartTag = sqlBlockStartTag;
    }

    public void execMission() throws Exception {
        // 装载sql脚本和语句（顺序 fileset->srcfile->text)
        this.missionCount++;
        this.totalSql = 0;
        this.goodSql = 0;
        out.println("开始执行SQL任务(" + missionCount + ")......");
        if (sqlCommand.length() == 0 && fileset.isEmpty()) {
            if (transactions.size() == 0) {
                throw new Exception("待执行的SQL脚本或者语句没有指定。");
            }
        } else {
            // deal with the fileset
            for (Iterator it = fileset.iterator(); it.hasNext();) {
                File file = (File) it.next();
                if (!file.exists()) {
                    out.println("指定的SQL脚本文件没有找到。系统忽略该脚本。(" + file.getAbsolutePath() + ")");
                    continue;
                }
                Transaction t = createTransaction();
                t.setSrc(file);
            }

            Transaction t = createTransaction();
            t.addText(sqlCommand);
        }
        if (conn == null) {
            createDbConnection();
        }

        // 设置属性，执行脚本
        // if (!isValidRdbms(conn)) return;
        // out.println("开启数据库连接......");
        conn.setAutoCommit(autocommit);
        statement = conn.createStatement();
        try {
            // Process all transactions
            for (Enumeration e = transactions.elements(); e.hasMoreElements();) {
                ((Transaction) e.nextElement()).runTransaction(out);

                if (!autocommit) {
                    out.println("提交事务......");
                    out.println("-------------------------------------------");
                    conn.commit();
                }
            }
        } catch (IOException e) {
            if (!autocommit && conn != null && onError.equals("abort")) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                }
            }
            throw new Exception("执行SQL过程中出现I/O错。", e);

        } catch (SQLException e) {
            if (!autocommit && conn != null && onError.equals("abort")) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                }
            }
            throw new Exception("执行SQL过程中出现错误。", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    // 不管是否conn原来是否auto commit的，直接置为autocommit
                    conn.setAutoCommit(true);
                    out.println("关闭数据库连接......");
                    conn.close();
                    // out.println("关闭数据库连接......");
                    // conn.close();
                }
                // 清空任务
                // out.println("清空任务");
                this.fileset.clear();
                this.sqlCommand = "";
                this.transactions.clear();
            } catch (SQLException e) {
            }
        }

        out.println("SQL任务(" + missionCount + ")完成..... " + totalSql + "条语句中的" + goodSql + "条执行成功");
        if (totalSql != goodSql)
            out.println("SQL脚本执行中有警告或错误,请查看系统数据信息...");
        out.println("*******************************************************************************************\n");
    }

    /**
     * 根据预先设定的数据库连接参数获取数据库连接。
     *
     * @throws Exception
     */
    protected void createDbConnection() throws Exception {
        if (driver == null) {
            throw new Exception("数据库的驱动程序没有指定。");
        }
        if (userId == null) {
            throw new Exception("数据库用户名没有指定。");
        }
        if (password == null) {
            throw new Exception("数据库的用户名密码没有制定。");
        }
        if (url == null) {
            throw new Exception("数据库的连接描述符没有指定。");
        }

        // 装载驱动
        Driver driverInstance = null;
        try {
            Class dc = Class.forName(driver);
            driverInstance = (Driver) dc.newInstance();
        }
        // 由于使用内置了oralce驱动程序，所以这些情况不再考虑汉化。
        catch (ClassNotFoundException e) {
            throw new Exception("Class Not Found: JDBC driver " + driver + " could not be loaded", e);
        } catch (IllegalAccessException e) {
            throw new Exception("Illegal Access: JDBC driver " + driver + " could not be loaded", e);
        } catch (InstantiationException e) {
            throw new Exception("Instantiation Exception: JDBC driver " + driver + " could not be loaded", e);
        }
        // 连接数据库
        try {
            if (conn == null) {
                Properties info = new Properties();
                info.put("user", userId);
                info.put("password", password);
                conn = driverInstance.connect(url, info);
            }

            if (conn == null) {
                // Driver doesn't understand the URL
                throw new SQLException("没有合适的驱动程序对于(" + url + ")！");
            }
        } catch (SQLException e) {
            throw new Exception("数据库连接失败，请确保数据库连接测试通过！", e);
        }
    }

    protected void runStatements(String fileDir, Reader reader, PrintStream out, String beginVersion, String endVersion)
            throws SQLException, IOException {
        int quoteNum = 0;
        String sql = "";
        String line = "";
        // 标识是否开始执行
        boolean isStarted = (beginVersion == null || "".equals(beginVersion.trim()));
        // 标识是否中止执行
        boolean willStop = false;
        boolean isStoped = false;
        // 是否正在处理语句块
        boolean isInBlockFlag = false;

        BufferedReader in = new BufferedReader(reader);
        try {
            while ((line = in.readLine()) != null) {
                if (isStoped) {
                    continue;
                }
                // 开始执行状态设定
//				if (!isStarted && line.startsWith("--version " + beginVersion)) {
//					isStarted = true;
//				}
//
//				if(!isStarted && line.startsWith("--version ")){
//					String[] versions = line.split(",");
//					String start = beginVersion.split(" ")[1];
//					String end = endVersion.split(" ")[1];
//					int compareStart = versions[1].split(" ")[1].compareTo(start);
//					int compareEnd = versions[1].split(" ")[1].compareTo(end);
//					if(compareStart > 0 && compareEnd < 0){
//						isStarted = true;
//					}
//				}

                if(!isStarted && line.startsWith("--version")){
                    if(line.startsWith("--version " + beginVersion)){
                        isStarted = true;
                    } else {
                        String[] versions = line.split(",");
                        String start = beginVersion.split(" ")[1];
                        String end = endVersion.split(" ")[1];
                        int compareStart = versions[1].split(" ")[1].compareTo(start);
                        int compareEnd = versions[1].split(" ")[1].compareTo(end);
                        if(compareStart > 0 && compareEnd <= 0){
                            isStarted = true;
                        }
                    }
                }

                if (!isStarted) {
                    continue;
                }

                // 停止执行状态设定
                if (willStop && line.startsWith("--version ")) {
                    isStoped = true;
                    continue;
                }
                if (endVersion != null && !"".equals(endVersion.trim()) && line.startsWith("--version " + endVersion)) {
                    willStop = true;
                }

                // 增加自定义注释前缀
                if (isCommentLine(line)) {
                    continue;
                } else if (line.startsWith("--import")) {
                    // 文件应用处理
                    if (line.length() > 8) {
                        String[] importInfo = line.split(importSqlDelimiter);
                        String beginV = "";
                        String endV = "";
                        File subFile = null;

                        if (importInfo.length < 2) {
                            out.print("错误的导入SQL语句，必须指定导入的SQL文件（" + line + ")。");
                            continue;
                        }
                        // 这个地方加入嵌套import支持
                        if (fileDir == null) {
                            fileDir = this.sqlFileBaseDir;
                        }
                        if (!fileDir.startsWith(File.separator)) {
                            fileDir = fileDir + File.separator;
                        }
                        try {
                            // 这个地方加入相对路径处理
                            subFile = new File(fileDir, importInfo[1]);
                        } catch (Exception ee) {
                            out.println("获取文件 [" + importInfo[1] + "] 失败.\n" + ee.toString());
                            continue;
                        }
                        if (importInfo.length >= 3) {
                            beginV = importInfo[2];
                        }
                        if (importInfo.length >= 4) {
                            endV = importInfo[3];
                        }
                        InputStreamReader subFileReader = null;
                        if (sqlFileCharsetName == null || sqlFileCharsetName.trim().length() == 0) {
                            subFileReader = new InputStreamReader(new FileInputStream(subFile));
                        } else {
                            subFileReader = new InputStreamReader(new FileInputStream(subFile), sqlFileCharsetName);
                        }

                        out.println("读取导入的文件：" + subFile + "开始版本：" + beginV + " 结束版本：" + endV);
                        runStatements(subFile.getParent(), subFileReader, out, beginV, endV);
                        subFileReader.close();
                    }
                    continue;
                } else if (line.startsWith(sqlBlockStartTag)) { // 语句块开始

                    isInBlockFlag = true;
                    continue;

                } else if (line.startsWith(sqlBlockEndTag)) { // 语句块结束,执行语句块

                    isInBlockFlag = false;
                    out.println("执行存储过程或函数");
                    execSQL(sql, out); // 执行语句块
                    sql = "";
                    quoteNum = 0;// 每行SQL语句结束，都认为字符串已经结束了。
                    continue;
                } else if (line.startsWith("--class")) {
                    try {
                        runJavaClass(line, out);
                    } catch (Exception ee) {
                        out.println("执行:" + line);
                        out.println("<警告>>>>>>>>>>>>>>> 执行JAVA方法时发生异常： >>>>>>>>>>>>>>>>>>>>>");
                        ee.printStackTrace(out);
                    } catch (BeanUtilException e) {
                        e.printStackTrace();
                    }
                    continue;
                } else if (quoteNum % 2 == 0 && line.trim().startsWith("--")) {
                    continue;
                }

                StringTokenizer st = new StringTokenizer(line);
                if (st.hasMoreTokens()) {
                    String token = st.nextToken();
                    if ("REM".equalsIgnoreCase(token)) {
                        continue;
                    }
                }

                if (isInBlockFlag) { // 如果当前正在处理的是语句块,则将后续的SQL语句补充完后再执行
                    sql += "\n" + line;
                    continue;
                }

                if (quoteNum % 2 == 0)
                    sql += " " + line;
                else
                    sql += "\n" + line;

                quoteNum += countCharNum(line, '\'');

                String tempLine = line.trim();
                if (delimiterType.equals(DelimiterType.NORMAL) && tempLine.endsWith(delimiter)) {
                    // log("SQL: " + sql, Project.MSG_VERBOSE);
                    execSQL(sql.substring(0, sql.lastIndexOf(delimiter)), out);
                    sql = "";
                    quoteNum = 0;
                }
            }

            // Catch any statements not followed by ;
            if (!sql.equals("")) {
                if (sql.trim().endsWith(delimiter))
                    execSQL(sql.substring(0, sql.lastIndexOf(delimiter)), out);
                else
                    execSQL(sql, out);
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * 统一一个字符串中，指定字符出现的次数
     *
     * @param line
     * @param c
     * @return
     */
    private int countCharNum(String line, char c) {
        int count = 0;
        char[] chars = line.toCharArray();
        for (char curr : chars) {
            if (curr == c)
                count++;
        }
        return count;
    }

    /**
     * 是否是注释行。
     *
     * @param line
     *            当前需要判断的行（字符串）
     * @return 如果是返回true，否则返回false
     */
    protected boolean isCommentLine(String line) {
        // 特殊的标识，非注释
        if (line.startsWith("--import") || line.startsWith("--class") || line.startsWith("--version")
                || line.startsWith(Parameter.SQL_BLOCK_START_TAG) || line.startsWith(Parameter.SQL_BLOCK_END_TAG)) {
            return false;
        }

        String[] prefixs = getCommentPrefixs();
        boolean skip = false;
        for (int i = 0; i < prefixs.length; i++) {
            if (line.startsWith(prefixs[i])) {
                skip = true;
                break;
            }
        }
        return skip;
    }

    /**
     * 执行初始化中配置的class的方法 注意: classInfoStr的格式必须为--class className methodName
     * paramType1,paramType2 paramValue1,paramValue2的格式，其中，参数类型以及参数值可以不设置
     *
     * @param classInfoStr
     * @throws BeanUtilException
     */
    public void runJavaClass(String classInfoStr, PrintStream out) throws BeanUtilException {
        String[] classInfo = classInfoStr.trim().split(" ");
        if (classInfo.length <= 2) {
            out.println("不正确的--class设置[" + classInfoStr + "]");
            return;
        }
        String className = classInfo[1].trim();
        String methodName = classInfo[2].trim();
        Class[] parameterTypes = null;
        Object[] initargs = null;
        Object obj = null;
        try {
            obj = ClassUtil.getInstance(className);
        } catch (Exception ee) {
            throw new RuntimeException("获取类[" + className + "]失败！");
        }

        if (classInfo.length == 5) {

            String[] types = classInfo[3].split(",");
            String[] values = classInfo[4].split(",");
            parameterTypes = new Class[types.length];
            initargs = new Object[types.length];
            for (int i = 0; i < types.length; i++) {
                parameterTypes[i] = ClassUtil.getClass(types[i].trim());
                initargs[i] = BeanUtil.convert(values[i], parameterTypes[i]);
            }
        }

        if (obj instanceof DBUpgradeHelper) {
            Method setConnMethod = ClassUtil.getMethod(className, "setConnection", new Class[] { Connection.class });
            ClassUtil.invokeMethod(setConnMethod, obj, new Object[] { conn });
        }

        Method method = null;
        try {
            method = ClassUtil.getMethod(className, methodName, parameterTypes);
        } catch (Exception ee) {
            throw new RuntimeException("获取方法[" + methodName + "]失败！");
        }
        ClassUtil.invokeMethod(method, obj, initargs);
    }

    protected void execSQL(String sql, PrintStream out) throws SQLException {
        // Check and ignore empty statements
        if ("".equals(sql.trim())) {
            return;
        }

        out.println("执行SQL语句:" + sql);

        try {
            totalSql++;
            if (!statement.execute(sql)) {
                // log(statement.getUpdateCount()+" rows affected",
                // Project.MSG_VERBOSE);
            } else {
                if (print) {
                    printResults(out);
                }
            }

            SQLWarning warning = conn.getWarnings();
            while (warning != null) {
                // log(warning + " sql warning", Project.MSG_VERBOSE);
                out.println(warning);
                warning = warning.getNextWarning();
            }
            conn.clearWarnings();
            goodSql++;
        } catch (SQLException e) {
            // log("Failed to execute: " + sql, Project.MSG_ERR);
            if (!onError.equals("continue")) {
                throw e;
            } else {
                out.println("<警告>>>>>>>>>>>>>>> 运行SQL脚本时发生异常： >>>>>>>>>>>>>>>>>>>>>");
                out.println("<警告>SQL:" + sql);
                out.println("<警告>异常描述: " + e.toString());
                out.println("<警告><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            }
            // log(e.toString(), Project.MSG_ERR);
        }
    }

    // 该函数打印出sql语句的结果集。
    protected void printResults(PrintStream out) throws java.sql.SQLException {
        ResultSet rs = null;
        do {
            rs = statement.getResultSet();
            if (rs != null) {
                // log("Processing new result set.", Project.MSG_VERBOSE);
                ResultSetMetaData md = rs.getMetaData();
                int columnCount = md.getColumnCount();
                StringBuffer line = new StringBuffer();
                if (showheaders) {
                    for (int col = 1; col < columnCount; col++) {
                        line.append(md.getColumnName(col));
                        line.append(",");
                    }
                    line.append(md.getColumnName(columnCount));
                    out.println(line);
                    line.setLength(0);
                }
                while (rs.next()) {
                    boolean first = true;
                    for (int col = 1; col <= columnCount; col++) {
                        String columnValue = rs.getString(col);
                        if (columnValue != null) {
                            columnValue = columnValue.trim();
                        }

                        if (first) {
                            first = false;
                        } else {
                            line.append(",");
                        }
                        line.append(columnValue);
                    }
                    out.println(line);
                    line.setLength(0);
                }
            }
        } while (statement.getMoreResults());
        out.println();
    }

    public class Transaction {
        private File tSrcFile = null;
        private String tSqlCommand = "";

        public void setSrc(File src) {
            this.tSrcFile = src;
        }

        public void addText(String sql) {
            this.tSqlCommand += sql;
        }

        private void runTransaction(PrintStream out) throws IOException, SQLException {
            if (tSrcFile != null) {
                out.println("执行SQL脚本文件:" + tSrcFile.getAbsolutePath());

                InputStreamReader reader = null;
                if (sqlFileCharsetName == null || sqlFileCharsetName.trim().length() == 0) {
                    reader = new InputStreamReader(new FileInputStream(tSrcFile));
                } else {
                    reader = new InputStreamReader(new FileInputStream(tSrcFile), sqlFileCharsetName);
                }

                runStatements(this.tSrcFile.getParent(), reader, out, "", "");
                reader.close();
            }
            // 有待改进
            if (tSrcFile == null && tSqlCommand != null && tSqlCommand.length() >= 0) {
                out.println("执行附加SQL命令");
                runStatements(null, new StringReader(tSqlCommand), out, "", "");
            }
        }
    }

    public static class OnError extends EnumeratedAttribute {
        static public final String CONTINUE = "continue";
        static public final String STOP = "stop";
        static public final String ABORT = "abort";

        public String[] getValues() {
            return new String[] { CONTINUE, STOP, ABORT };
        }
    }

    public static class DelimiterType extends EnumeratedAttribute {
        static public final String NORMAL = "normal";
        static public final String ROW = "row";

        public String[] getValues() {
            return new String[] { NORMAL, ROW };
        }
    }

    public static void main(String[] args) throws Exception {
        SQLExec sql = new SQLExec();
        sql.setAutocommit(false);
        DelimiterType type = new DelimiterType();
        type.setValue(type.NORMAL);
        sql.setDelimiterType(type);
        // sql.setDriver("oracle.jdbc.driver.OracleDriver");
        // sql.setPassword("nms2");
        // sql.setUrl("jdbc:oracle:thin:@netadmin:1521:orcl");
        // sql.setUserid("nms2");
        sql.setPrint(true);

        sql.setDelimiter(";");
        sql.addMissionFile(new File("../database/openview/drop_common.oracle"));
        sql.addMissionFile(new File("../database/openview/drop_event.oracle"));
        sql.addMissionFile(new File("../database/openview/drop_topo.oracle"));
        sql.addMissionFile(new File("../database/openview/drop_trend.oracle"));
        // sql.addFile(new File("../database/openview/drop_groups.oracle"));

        sql.addMissionFile(new File("../database/netadmin/drop_commonview.sql"));
        sql.addMissionFile(new File("../database/netadmin/drop_ifspeed.sql"));
        sql.addMissionFile(new File("../database/netadmin/drop_statconf.sql"));
        sql.addMissionFile(new File("../database/netadmin/drop_user.sql"));
        sql.execMission();

        sql.setDelimiter(";");
        sql.addMissionFile(new File("../database/openview/tables_common.oracle"));
        sql.addMissionFile(new File("../database/openview/tables_topo.oracle"));
        sql.addMissionFile(new File("../database/openview/tables_trend.oracle"));
        sql.addMissionFile(new File("../database/openview/tables_groups.oracle"));
        sql.addMissionFile(new File("../database/openview/tables_event.oracle"));

        sql.addMissionFile(new File("../database/netadmin/commonview.sql"));
        sql.addMissionFile(new File("../database/netadmin/statconf.sql"));
        sql.addMissionFile(new File("../database/netadmin/user.sql"));
        sql.execMission();

        sql.setDelimiter("/");
        sql.addMissionFile(new File("../database/netadmin/ifspeed.sql"));
        sql.execMission();

        // sql.setSrc(new File("../database/netadmin/test.sql"));
        // sql.addText("select * from dual; select * from dual;");
        // sql.execute();
    }
}

abstract class EnumeratedAttribute {

    protected String value;

    public abstract String[] getValues();

    public EnumeratedAttribute() {
    }

    public final void setValue(String value) throws Exception {
        if (!containsValue(value)) {
            throw new Exception(value + " is not a legal value for this attribute");
        }
        this.value = value;
    }

    public final boolean containsValue(String value) {
        String[] values = getValues();
        if (values == null || value == null) {
            return false;
        }

        for (int i = 0; i < values.length; i++) {
            if (value.equals(values[i])) {
                return true;
            }
        }
        return false;
    }

    public final String getValue() {
        return value;
    }
}

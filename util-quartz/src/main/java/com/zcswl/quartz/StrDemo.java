package com.zcswl.quartz;

import ch.qos.logback.classic.pattern.SyslogStartConverter;
import cn.hutool.core.util.StrUtil;
import com.google.common.base.Joiner;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xingyi
 * @date 2022/6/24
 */
public class StrDemo {

    public static void main(String[] args) {
        String body = "org.apache.flink.client.program.ProgramInvocationException: The main method caused an error: com.dtstack.flinkx.throwable.DtSqlParserException: \n" +
                "----------sql start---------\n" +
                "1>    \n" +
                "2>    insert \n" +
                "3>    into\n" +
                "4>        sink\n" +
                "5>        select\n" +
                "6>            * \n" +
                "7>        from\n" +
                "8>            source\n" +
                "\n" +
                "----------sql end--------- \n" +
                "\n" +
                "Could not find any factory for identifier 'stream-qwxx' that implements 'org.apache.flink.table.factories.DynamicTableSourceFactory' in the classpath.\n" +
                "\n" +
                "Available factory identifiers are:\n" +
                "\n" +
                "datagen\n" +
                "filesystem\n" +
                "iceberg\n" +
                "kafka\n" +
                "kafka-x\n" +
                "mysql-x\n" +
                "rocketmq-x\n" +
                "upsert-kafka\n" +
                "upsert-kafka-x \n" +
                "\n" +
                "Unable to create a source for reading table 'default_catalog.default_database.source'.\n" +
                "\n" +
                "Table options are:\n" +
                "\n" +
                "'connector'='stream-qwxx'\n" +
                "'rows-per-second'='1'\n" +
                "org.apache.flink.client.program.ProgramInvocationException: The main method caused an error: com.dtstack.flinkx.throwable.DtSqlParserException: \n" +
                "----------sql start---------\n" +
                "1>    \n" +
                "2>    insert \n" +
                "3>    into\n" +
                "4>        sink\n" +
                "5>        select\n" +
                "6>            * \n" +
                "7>        from\n" +
                "8>            source\n" +
                "\n" +
                "----------sql end--------- \n" +
                "\n" +
                "Could not find any factory for identifier 'stream-qwxx' that implements 'org.apache.flink.table.factories.DynamicTableSourceFactory' in the classpath.\n" +
                "\n" +
                "Available factory identifiers are:\n" +
                "\n" +
                "datagen\n" +
                "filesystem\n" +
                "iceberg\n" +
                "kafka\n" +
                "kafka-x\n" +
                "mysql-x\n" +
                "rocketmq-x\n" +
                "upsert-kafka\n" +
                "upsert-kafka-x \n" +
                "\n" +
                "Unable to create a source for reading table 'default_catalog.default_database.source'.\n" +
                "\n" +
                "Table options are:\n" +
                "\n" +
                "'connector'='stream-qwxx'\n" +
                "'rows-per-second'='1'\n" +
                "\tat org.apache.flink.client.program.PackagedProgram.callMainMethod(PackagedProgram.java:372)\n" +
                "\tat org.apache.flink.client.program.PackagedProgram.invokeInteractiveModeForExecution(PackagedProgram.java:224)\n" +
                "\tat org.apache.flink.client.program.PackagedProgramUtils.getPipelineFromProgram(PackagedProgramUtils.java:158)\n" +
                "\tat org.apache.flink.client.program.PackagedProgramUtils.createJobGraph(PackagedProgramUtils.java:82)\n" +
                "\tat org.apache.flink.client.program.PackagedProgramUtils.createJobGraph(PackagedProgramUtils.java:117)\n" +
                "\tat com.dtstack.engine.flink.FlinkClient.grammarCheck(FlinkClient.java:1411)\n" +
                "\tat com.dtstack.engine.common.client.ClientProxy.lambda$null$21(ClientProxy.java:376)\n" +
                "\tat com.dtstack.engine.common.callback.ClassLoaderCallBackMethod.callbackAndReset(ClassLoaderCallBackMethod.java:13)\n" +
                "\tat com.dtstack.engine.common.client.ClientProxy.lambda$grammarCheck$22(ClientProxy.java:376)\n" +
                "\tat java.util.concurrent.CompletableFuture$AsyncSupply.run(CompletableFuture.java:1590)\n" +
                "\tat java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n" +
                "\tat java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n" +
                "\tat java.lang.Thread.run(Thread.java:748)\n" +
                "Caused by: com.dtstack.flinkx.throwable.FlinkxRuntimeException: com.dtstack.flinkx.throwable.DtSqlParserException: \n" +
                "----------sql start---------\n" +
                "1>    \n" +
                "2>    insert \n" +
                "3>    into\n" +
                "4>        sink\n" +
                "5>        select\n" +
                "6>            * \n" +
                "7>        from\n" +
                "8>            source\n" +
                "\n" +
                "----------sql end--------- \n" +
                "\n" +
                "Could not find any factory for identifier 'stream-qwxx' that implements 'org.apache.flink.table.factories.DynamicTableSourceFactory' in the classpath.\n" +
                "\n" +
                "Available factory identifiers are:\n" +
                "\n" +
                "datagen\n" +
                "filesystem\n" +
                "iceberg\n" +
                "kafka\n" +
                "kafka-x\n" +
                "mysql-x\n" +
                "rocketmq-x\n" +
                "upsert-kafka\n" +
                "upsert-kafka-x \n" +
                "\n" +
                "Unable to create a source for reading table 'default_catalog.default_database.source'.\n" +
                "\n" +
                "Table options are:\n" +
                "\n" +
                "'connector'='stream-qwxx'\n" +
                "'rows-per-second'='1'\n" +
                "\tat com.dtstack.flinkx.Main.exeSqlJob(Main.java:167)\n" +
                "\tat com.dtstack.flinkx.Main.main(Main.java:113)\n" +
                "\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n" +
                "\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n" +
                "\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n" +
                "\tat java.lang.reflect.Method.invoke(Method.java:498)\n" +
                "\tat org.apache.flink.client.program.PackagedProgram.callMainMethod(PackagedProgram.java:355)\n" +
                "\t... 12 more\n" +
                "Caused by: com.dtstack.flinkx.throwable.DtSqlParserException: \n" +
                "----------sql start---------\n" +
                "1>    \n" +
                "2>    insert \n" +
                "3>    into\n" +
                "4>        sink\n" +
                "5>        select\n" +
                "6>            * \n" +
                "7>        from\n" +
                "8>            source\n" +
                "\n" +
                "----------sql end--------- \n" +
                "\n" +
                "Could not find any factory for identifier 'stream-qwxx' that implements 'org.apache.flink.table.factories.DynamicTableSourceFactory' in the classpath.\n" +
                "\n" +
                "Available factory identifiers are:\n" +
                "\n" +
                "datagen\n" +
                "filesystem\n" +
                "iceberg\n" +
                "kafka\n" +
                "kafka-x\n" +
                "mysql-x\n" +
                "rocketmq-x\n" +
                "upsert-kafka\n" +
                "upsert-kafka-x \n" +
                "\n" +
                "Unable to create a source for reading table 'default_catalog.default_database.source'.\n" +
                "\n" +
                "Table options are:\n" +
                "\n" +
                "'connector'='stream-qwxx'\n" +
                "'rows-per-second'='1'\n" +
                "\tat com.dtstack.flinkx.sql.parser.SqlParser.lambda$parseSql$1(SqlParser.java:73)\n" +
                "\tat java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)\n" +
                "\tat java.util.stream.ReferencePipeline$2$1.accept(ReferencePipeline.java:175)\n" +
                "\tat java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1374)\n" +
                "\tat java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:481)\n" +
                "\tat java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:471)\n" +
                "\tat java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:151)\n" +
                "\tat java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:174)\n" +
                "\tat java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)\n" +
                "\tat java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:418)\n" +
                "\tat com.dtstack.flinkx.sql.parser.SqlParser.parseSql(SqlParser.java:66)\n" +
                "\tat com.dtstack.flinkx.Main.exeSqlJob(Main.java:158)\n" +
                "\t... 18 more\n" +
                "Caused by: org.apache.flink.table.api.ValidationException: Could not find any factory for identifier 'stream-qwxx' that implements 'org.apache.flink.table.factories.DynamicTableSourceFactory' in the classpath.\n" +
                "\n" +
                "Available factory identifiers are:\n" +
                "\n" +
                "datagen\n" +
                "filesystem\n" +
                "iceberg\n" +
                "kafka\n" +
                "kafka-x\n" +
                "mysql-x\n" +
                "rocketmq-x\n" +
                "upsert-kafka\n" +
                "upsert-kafka-x \n" +
                "\n" +
                "Unable to create a source for reading table 'default_catalog.default_database.source'.\n" +
                "\n" +
                "Table options are:\n" +
                "\n" +
                "'connector'='stream-qwxx'\n" +
                "'rows-per-second'='1'\n" +
                "\tat org.apache.flink.table.factories.FactoryUtil.createTableSource(FactoryUtil.java:177)\n" +
                "\tat org.apache.flink.table.planner.plan.schema.CatalogSourceTable.createDynamicTableSource(CatalogSourceTable.java:254)\n" +
                "\tat org.apache.flink.table.planner.plan.schema.CatalogSourceTable.toRel(CatalogSourceTable.java:100)\n" +
                "\tat org.apache.calcite.sql2rel.SqlToRelConverter.toRel(SqlToRelConverter.java:3585)\n" +
                "\tat org.apache.calcite.sql2rel.SqlToRelConverter.convertIdentifier(SqlToRelConverter.java:2507)\n" +
                "\tat org.apache.calcite.sql2rel.SqlToRelConverter.convertFrom(SqlToRelConverter.java:2144)\n" +
                "\tat org.apache.calcite.sql2rel.SqlToRelConverter.convertFrom(SqlToRelConverter.java:2093)\n" +
                "\tat org.apache.calcite.sql2rel.SqlToRelConverter.convertFrom(SqlToRelConverter.java:2050)\n" +
                "\tat org.apache.calcite.sql2rel.SqlToRelConverter.convertSelectImpl(SqlToRelConverter.java:663)\n" +
                "\tat org.apache.calcite.sql2rel.SqlToRelConverter.convertSelect(SqlToRelConverter.java:644)\n" +
                "\tat org.apache.calcite.sql2rel.SqlToRelConverter.convertQueryRecursive(SqlToRelConverter.java:3438)\n" +
                "\tat org.apache.calcite.sql2rel.SqlToRelConverter.convertQuery(SqlToRelConverter.java:570)\n" +
                "\tat org.apache.flink.table.planner.calcite.FlinkPlannerImpl.org$apache$flink$table$planner$calcite$FlinkPlannerImpl$$rel(FlinkPlannerImpl.scala:165)\n" +
                "\tat org.apache.flink.table.planner.calcite.FlinkPlannerImpl.rel(FlinkPlannerImpl.scala:157)\n" +
                "\tat org.apache.flink.table.planner.operations.SqlToOperationConverter.toQueryOperation(SqlToOperationConverter.java:902)\n" +
                "\tat org.apache.flink.table.planner.operations.SqlToOperationConverter.convertSqlQuery(SqlToOperationConverter.java:871)\n" +
                "\tat org.apache.flink.table.planner.operations.SqlToOperationConverter.convert(SqlToOperationConverter.java:250)\n" +
                "\tat org.apache.flink.table.planner.operations.SqlToOperationConverter.convertSqlInsert(SqlToOperationConverter.java:564)\n" +
                "\tat org.apache.flink.table.planner.operations.SqlToOperationConverter.convert(SqlToOperationConverter.java:248)\n" +
                "\tat org.apache.flink.table.planner.delegation.ParserImpl.parse(ParserImpl.java:77)\n" +
                "\tat org.apache.flink.table.api.internal.StatementSetImpl.addInsertSql(StatementSetImpl.java:50)\n" +
                "\tat com.dtstack.flinkx.sql.parser.InsertStmtParser.execStmt(InsertStmtParser.java:47)\n" +
                "\tat com.dtstack.flinkx.sql.parser.AbstractStmtParser.handleStmt(AbstractStmtParser.java:50)\n" +
                "\tat com.dtstack.flinkx.sql.parser.AbstractStmtParser.handleStmt(AbstractStmtParser.java:52)\n" +
                "\tat com.dtstack.flinkx.sql.parser.AbstractStmtParser.handleStmt(AbstractStmtParser.java:52)\n" +
                "\tat com.dtstack.flinkx.sql.parser.SqlParser.lambda$parseSql$1(SqlParser.java:69)\n" +
                "\t... 29 more\n" +
                "Caused by: java.lang.RuntimeException: Could not find any factory for identifier 'stream-qwxx' that implements 'org.apache.flink.table.factories.DynamicTableSourceFactory' in the classpath.\n" +
                "\n" +
                "Available factory identifiers are:\n" +
                "\n" +
                "datagen\n" +
                "filesystem\n" +
                "iceberg\n" +
                "kafka\n" +
                "kafka-x\n" +
                "mysql-x\n" +
                "rocketmq-x\n" +
                "upsert-kafka\n" +
                "upsert-kafka-x\n" +
                "\tat org.apache.flink.table.factories.FactoryUtil.discoverFactory(FactoryUtil.java:303)\n" +
                "\tat org.apache.flink.table.factories.FactoryUtil.getDynamicTableFactory(FactoryUtil.java:420)\n" +
                "\tat org.apache.flink.table.factories.FactoryUtil.createTableSource(FactoryUtil.java:173)\n" +
                "\t... 54 more\n";
        StringBuilder stringBuilder = new StringBuilder();
        String[] split = StrUtil.split(body, "\n");

        List<String> strings = Arrays.asList(split);
        LinkedHashSet<String> strings1 = new LinkedHashSet<>(strings);
        ArrayList<String> strings2 = new ArrayList<>(strings1);
        for (String s : strings2) {
            stringBuilder.append(s).append("\n");
        }
        System.out.println(stringBuilder.toString());


    }

    private int lengthOfLongestSubstring(String s) {
        // 用来保存字符和它对应的位置
        Map<Character, Integer> map = new HashMap<>();
        // 以-1作为起点，长度初始化为0
        int start = -1,res = 0;
        // end循环
        for (int end=0;end<s.length();end++) {
            // 如果map中存在了这个值，将start向后移动
            if (map.containsKey(s.charAt(end))) {
                start = Math.max(start,map.get(s.charAt(end)));
            }
            // 更新字符对应的位置
            map.put(s.charAt(end),end);
            // 通过end-start来获取长度，本身是end-(start+1)+1
            res = Math.max(res,end-start);
        }
        return res;
    }

    private static int lengthOfLongestSubstring1(String s) {
        // 用来保存字符和它对应的位置
        Map<Character, Integer> map = new HashMap<>();
        // tmp用来存储右移一位之后的长度
        int res = 0, tmp = 0;
        for (int i = 0; i < s.length(); i++) {
            // 获取当前字符是否存在，返回位置
            int j = map.getOrDefault(s.charAt(i),-1);
            // 更新字符位置
            map.put(s.charAt(i),i);
            // 如果上一次移动的结果小于本次移动的结果，那么返回上一次移动的+1
            // 如果大于，则说明本次移动是一个缩短移动，更新上一次移动的结果
            // ex:[abcaabc]
            // tmp = 1->2->3->3->1->2->3
            tmp = tmp<i-j?tmp+1:i-j;
            // 保存最大值并返回
            res = Math.max(res,tmp);
        }
        return res;
    }

    public static List<String> findMaxStr(List<String> str) {
        List<String> noRep = new ArrayList<>();
        for (int len = str.size(); len > 0; len --) {
            List<String> list = subStr(str, len);
            noRep.clear();
            if (checkRepeat(list)) {
                noRep.addAll(list);
            }
            if (noRep.size() > 0) {
                return noRep;
            }
        }
        return noRep;
    }

    /**
     * 判断是否是有重复
     * @param str
     * @return
     */
    private static boolean checkRepeat(List<String> str) {
        for (int i = 0; i < str.size(); i++) {
            for(int j = i+1; j < str.size(); j++) {
                if (str.get(i).equals(str.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    private static List<String> subStr(List<String> str, int subLength) {
        List<String> list = new ArrayList<>();
        if (subLength < 1 || str == null || str.size() < subLength) {
            return list;
        }
        for (int i = 0; i + subLength <= str.size(); i++) {
            for (int j = i; j < i+subLength; j++) {
                list.add(str.get(j));
            }
        }
        return list;
    }

    /**
     * 查询不重复子串
     * @param str
     * @return
     */
    public static List<String> findMaxStr(String str) {
        List<String> noRep = new ArrayList<>();
        for (int len = str.length(); len > 0; len--) {
            List<String> list = subStr(str, len);
            noRep.clear();
            for (String s : list) {
                if (checkRepeat(s)) {
                    noRep.add(s);
                }
            }
            if (noRep.size() > 0) {
                return noRep;
            }
        }
        return noRep;
    }


    /**
     * 判断是否是有重复
     * @param str
     * @return
     */
    private static boolean checkRepeat(String str) {
        char[] arr = str.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            for(int j = i+1; j < arr.length; j++) {
                if (arr[i] == arr[j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 获取字符串中的长度为length的所有子串
     * @param str
     * @param subLength
     * @return
     */
    private static List<String> subStr(String str, int subLength) {
        List<String> list = new ArrayList<>();
        if (subLength < 1 || str == null || str.length() < subLength) {
            return list;
        }
        for (int i = 0; i + subLength <= str.length(); i++) {
            list.add(str.substring(i, i + subLength));
        }
        return list;
    }
}

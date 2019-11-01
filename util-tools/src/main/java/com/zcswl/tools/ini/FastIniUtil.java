package com.zcswl.tools.ini;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ini文件解析操作
 * @author zhoucg
 * @date 2018-11-29
 */
public class FastIniUtil {

    /**
     * 去除ini文件中的注释，以";"或"#"开头，顺便去除UTF-8等文件的BOM头
     * @param source
     * @return
     */
    private static String removeIniCommments(String source) {
        String result = source;
        if(result.contains(";")) {
            result = result.substring(0,result.indexOf(";"));
        }
        if(result.contains("#")) {
            result = result.substring(0,result.indexOf("#"));
        }
        return result.trim();
    }

    /**
     * 读取 INI 文件，存放到Map中
     *
     * 支持以‘#’或‘;’开头的注释；
     * 支持行连接符（行末的'\'标记）；
     * 支持缺省的global properties；
     * 支持list格式（非name=value格式处理为list格式）；
     * 支持空行、name/value前后的空格；
     * 如果有重名，取最后一个；
     *
     * 格式（例子）如下
     *
     * # 我是注释
     * ; 我也是注释
     *
     * name0=value0  # 我是global properties
     * name10=value10
     *
     * [normal section] # 我是普通的section
     * name1=value1 # 我是name和value
     *
     * [list section] # 我是只有value的section，以第一个是否包含'='为判断标准
     * value1
     * value2
     *
     * @param bufferedReader ini读取文件流
     * @return Map<sectionName, Map<String,Stirng>> object是一个Map（存放name=value对）或List（存放只有value的properties）
     *      key -> reSection:表达标签
     *      Map<String,String>  -> 标签对应数据
     */
    public static Map<String,Map<String,String>> readIniFile(BufferedReader bufferedReader) {
        Map<String, List<String>> listResult = new HashMap<>();
        Map<String,Map<String,String>> result = new HashMap<>();
        String globalSection = "global";

        try{
            String str;
            String currentSection = globalSection;
            List<String> currentProperties = new ArrayList<>();

            boolean lineContinued = false;
            String tempStr = null;

            while((str = bufferedReader.readLine()) != null) {
                str = removeIniCommments(str).trim();
                if("".equals(str) || str == null) {
                    continue;
                }

                if(lineContinued) {
                    str = tempStr + str;
                }

                if(str.endsWith("\\")) {
                    lineContinued = true;
                    tempStr = str.substring(0,str.length()-1);
                    continue;
                } else {
                    lineContinued = false;
                }

                if(str.startsWith("[") && str.endsWith("]")) {
                    /**
                     * 获取对应新得newSection
                     */
                    String newSection = str.substring(1,str.length() -1).trim();

                    /**
                     * 如果是心的section的话，存入对应的section的值，然后把当前的section存入listResult中
                     */
                    if(!currentSection.equals(newSection)) {
                        listResult.put(currentSection,currentProperties);

                        currentSection = newSection;
                        currentProperties = listResult.get(currentSection);

                        if(currentProperties == null) {
                            currentProperties = new ArrayList<>();
                        }
                    }
                } else {
                    currentProperties.add(str);
                }
            }

            listResult.put(currentSection,currentProperties);
            bufferedReader.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e1) {
                }
            }
        }

        /**
         * 获取对应的section，然后拆开对应的数据
         */
        for(String key : listResult.keySet()) {
            List<String> tempList = listResult.get(key);
            if(tempList == null || tempList.size() == 0) {
                continue;
            }
            Map<String,String> properties = new HashMap<>();
            tempList.stream().forEach(str -> {
                if(str.contains("=")) {
                    int delimiterPos = str.indexOf("=");
                    properties.put(str.substring(0,delimiterPos).trim(),str.substring(delimiterPos+1,str.length()).trim());
                }
            });

            result.put(key,properties);

        }
        return result;

    }


}

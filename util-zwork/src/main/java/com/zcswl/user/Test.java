package com.zcswl.user;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.io.File;
import java.util.Optional;

/**
 * @author xingyi
 * @date 2021/8/11 2:48 下午
 */
public class Test {

    public static void main(String[] args) {
        String s100 = String.join(File.separator, Lists.newArrayList(System.getProperty("user.dir"), "12", String.format("TASK_%s", "1212"), "12", "12")) + File.separator;
        System.out.println(s100);
        String join = Joiner.on(File.separator).join(System.getProperty("user.dir"), "12", String.format("TASK_%s", "1212"), "12", "12");
        System.out.println(join);


        String TEMP_TABLE_PLACEHOLDER = "\\$\\{tempTable\\}";
        String sql = "${tempTable} ${createDirtyTable} select * from test";
        String replace = sql.replace("${createDirtyTable}", "");
        String replace1 = replace.replaceAll(TEMP_TABLE_PLACEHOLDER, "12");
        System.out.println(replace1);


        String str1 = "Aoc.Iop.Aoc.Iop.Aoc";		//定义三个一样的字符串
        String str2 = "Aoc.Iop.Aoc.Iop.Aoc";
        String str3 = "Aoc.Iop.Aoc.Iop.Aoc";

        String str11 = str1.replace(".", "#");		// str11 = "Aoc#Iop#Aoc#Iop#Aoc"
        String str22 = str2.replaceAll(".", "#");	// str22 = "###################"
        String str33 = str3.replaceFirst(".", "#");	// str33 = "#oc.Iop.Aoc.Iop.Aoc"
        System.out.println(1);


        Optional<String> s = Optional.of("12");
        String result = s.orElse(returnStr());
        System.out.println(result);



    }

    public static String returnStr() {
        return "123";
    }
}


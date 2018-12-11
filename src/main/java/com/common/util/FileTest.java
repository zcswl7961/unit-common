package com.common.util;

//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.time.format.DateTimeFormatter;

/**
 * Created by zhoucg on 2018-11-26.
 */
public class FileTest {


    public static void main(String[] args) {


//        String str = "SLO/";
//        if(str.indexOf("/") != -1) {
//            int lastIndex = str.lastIndexOf("/");
//            System.out.println(lastIndex);
//            str = str.substring(0,lastIndex);
//            System.out.println(str);
//        }

//        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("GMT-0"));
//        String test = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd").withZone(ZoneId.of("GMT-0")));
//
//        System.out.println(test);


        String line = "current line：-rw-rw-rw- 1 cmacast qxdata  616 2018-11-12 12:50 notice_258859642_20181017061649.txt";
        String[] arr = line.split(" ");
        String last = arr[arr.length-1];
        System.out.println(last);

    }
}

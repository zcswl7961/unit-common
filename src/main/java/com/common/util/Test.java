package com.common.util;


import org.apache.commons.lang3.StringUtils;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by zhoucg on 2018-10-17.
 */
public class Test {

    public static void main(String[] args) throws Exception{
//        Map<String,String> a= new HashMap<>();
////        String str = "B.0007.0002.R001:B.0007.0002.P001:B.0007.0002.S001";
////                      B.0007.0002.R001
////        String a = str.substring(0,16);
//
//        a.put("风廓线雷达径向谱数据文件","B.0007.0002.R001:B.0007.0002.P001:B.0007.0002.S001");
//        a.put("风廓线雷达功率谱数据文件","B.0007.0001.R001:B.0007.0001.P001:B.0007.0001.S001");
//        Map<String,String> b = new HashMap<>();
//        a.keySet().stream().forEach(str->{
//            b.put(str,a.get(str).substring(0,16));
//        });
//        System.out.println(b);

//        String url = "http://10.1.65.182/mcp/openapi/v2/queryProvinceDataInfo?apikey=%s";
//        String a = String.format(url,"asdfsadf");
//        System.out.println(a);

//        String partten = "^Z_RADA_I_[a-zA-Z0-9]\\d{4}_(20\\d{2})(0[1-9]|1[0-2])(\\d{2})(\\d{2})(\\d{2})(\\d{2})_O_WPRD_LC_RAD.(txt|TXT)$";
//        String fileName = "Z_RADA_I_59476_20180823000002_O_WPRD_LC_RAD.TXT";
//
//        Pattern pattern = Pattern.compile(partten);
//        Matcher matcher = pattern.matcher(fileName);
//
//        System.out.println("matcher=="+matcher.matches());

//        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String str = "2018-11-15 3:00:00";
//        Date now = formate.parse(str);
//
//
//
//
//        /**
//         * 获取当前时间的年月日
//         */
//        SimpleDateFormat ymdFormate = new SimpleDateFormat("yyyy-MM-dd");
//        String yearMonthDayStr = ymdFormate.format(now);
//        String gmt_zero = " 8:00:00";
//        String fullEigthTime = yearMonthDayStr+gmt_zero;
//        Date eigth = formate.parse(fullEigthTime);
//        System.out.println(now.before(eigth));
//
//
//        if(now.before(eigth)) {
//            now = getYesterdayDay();
//        }
//        System.out.println(now);

//        String dataTimeStr = "2019-02-27 01:54:27";
//        int len = dataTimeStr.length();
//        System.out.println(len);

//        String a = "1";
//        switch (a) {
//            case "1" :
//                System.out.println("找个输出的是1");
//                break;
//            case "2" :
//                System.out.println("找个输出的是2");
//                break;
//            default:
//                break;
//        }

//        List<String> a= new ArrayList<>();
//        a.add("a");
//        a.add("b");
//        a.add("c");
//        a.add("d");
//        List<String> b = a.stream().filter(str -> str.equals("a")).collect(Collectors.toList());
//
//        for(String strA : b) {
//            System.out.println(strA);
//        }

//        String test = "-100";
//        int testInt = Integer.parseInt(test);
//        int sbtestInt = -testInt;
//        System.out.println(sbtestInt);
//        System.out.println(testInt);
//        test = test.substring(1);
//        boolean flag = StringUtils.isNumeric(test);
//        System.out.println(flag);
//
//        Date date = new Date();
//        ZonedDateTime d = ZonedDateTime.ofInstant(date.toInstant(),ZoneId.of("GMT-0"));
//        System.out.println(d);

//        String str = timeStamp2Date("1553483333043","yyyy-MM-dd HH:mm:ss.SSS");
//        System.out.println(str);

//        int a =18;
//        int b = a >>> 1;
//        System.out.println(b);

//        ConcurrentHashMap<String,String> concurrentHashMap = new ConcurrentHashMap<>(18);
//        concurrentHashMap.put("a","b");

        List<User> userList = new ArrayList<>();
        userList.add(new User("1","A.0001.0003.R001:A.0013.0001.P006:A.0013.0001.S001"));
        userList.add(new User("1","J.0003.0003.R002:J.0003.0003.P002:J.0003.0003.S001"));
        userList.add(new User("1","M.0032.0002.R001:M.0033.0001.P002:M.0033.0001.S001"));
        userList.add(new User("1","J.0003.0014.R001:J.0003.0014.P001:J.0003.0014.S001"));
        userList.add(new User("1","A.0001.0040.R001:A.0001.0032.PA02:A.0001.0025.S004"));
        userList.add(new User("1","A.0001.0032.R001:A.0010.0001.P006:A.0010.0001.S001"));
        userList.add(new User("1","A.0001.0031.R001:A.0011.0001.P001:A.0011.0001.S001"));
        userList.add(new User("1","B.0001.0008.R001:B.0001.0008.PT01:B.0001.0024.S001"));
        userList.add(new User("1","G.0002.0004.R001:G.0002.0004.P001:G.0002.0001.S001"));

        System.out.println(userList);



    }

    private static class User{
        private String name;
        private String code;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public User(String name,String code) {
            this.name = name;
            this.code = code;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", code='" + code + '\'' +
                    '}';
        }
    }


    public static String timeStamp2Date(String seconds,String format) {
                 if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
                         return "";
                     }
                 if(format == null || format.isEmpty()){
                         format = "yyyy-MM-dd HH:mm:ss";
                     }
                 SimpleDateFormat sdf = new SimpleDateFormat(format);
                 return sdf.format(new Date(Long.valueOf(seconds+"000")));
    }

    public static Date getYesterdayDay() {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date time = cal.getTime();
        return time;

    }
}

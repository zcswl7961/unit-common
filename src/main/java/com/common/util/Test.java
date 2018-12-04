package com.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = "2018-11-15 3:00:00";
        Date now = formate.parse(str);




        /**
         * 获取当前时间的年月日
         */
        SimpleDateFormat ymdFormate = new SimpleDateFormat("yyyy-MM-dd");
        String yearMonthDayStr = ymdFormate.format(now);
        String gmt_zero = " 8:00:00";
        String fullEigthTime = yearMonthDayStr+gmt_zero;
        Date eigth = formate.parse(fullEigthTime);
        System.out.println(now.before(eigth));


        if(now.before(eigth)) {
            now = getYesterdayDay();
        }
        System.out.println(now);

    }

    public static Date getYesterdayDay() {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date time = cal.getTime();
        return time;

    }
}

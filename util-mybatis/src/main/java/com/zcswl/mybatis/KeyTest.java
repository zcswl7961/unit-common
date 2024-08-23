package com.zcswl.mybatis;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author xingyi
 * @date 2024/8/9
 */
public class KeyTest {

    public static void main(String[] args) throws Exception {
        Map<String, Object> param = new HashMap<>();
        String sign = getSign("ec04bd901a294362a7208e2fcd7f5d8d", param);
        System.out.println("md5信息摘要后: " + sign);
    }

    public static String getSign(String appSecret, Map<String, Object> param) throws Exception {
        String formattedString = formatSignatureParam(appSecret, param);
        System.out.println("原始参数md5前: " + formattedString);
        return Hashing.md5().hashBytes(formattedString.getBytes(Charsets.UTF_8)).toString();
    }

    /**
     * @param sk    app secret
     * @param param 参数map，包括header，query和body中的参数
     * @return
     */

    private static String formatSignatureParam(String sk, Map<String, Object> param) {
        if (param == null) {
            throw new RuntimeException("error param");
        }
        //申请api时的apiId
        param.put("X-Auth-ActionId", 655);
        //app key
        param.put("X-Auth-Key", 27182819);
        //当前时间戳，如果时间与服务端时间相差大于10分钟，此次请求将判定为签名错误
        param.put("X-Auth-Timestamp", System.currentTimeMillis());

        //todo:post请求加上body内参数，get请求加上form表单内参数
        //需根据自身API的入参替换这部分内容
        param.put("id","2001");

        System.out.println("时间戳:" + System.currentTimeMillis());
        //使用TreeMap可以自动按照key字典顺序排序
        TreeMap<String, String> paramMap = new TreeMap<>();
        for (Map.Entry<String, Object> en : param.entrySet()) {
            if (null == en.getValue()) {
                continue;
            }
            //参数应当均为基本类型
            paramMap.put(en.getKey(), en.getValue().toString());
        }
        Set<Map.Entry<String, String>> entries = paramMap.entrySet();
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String val = entry.getValue();
            builder.append(key)
                    .append("=")
                    .append(val)
                    .append("&");
        }
        return builder.append(sk).toString();
    }
}

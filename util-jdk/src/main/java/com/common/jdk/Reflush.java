package com.common.jdk;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * @author zhoucg
 * @date 2020-12-05 11:55
 */
public class Reflush {

    public static String str="taskkill /F /IM chrome.exe";
    //这里firefox也可以改为iexplore或者chrome等等,也就是指定打开网页的浏览器，后面这些参数就是网址，
    //实际上有文件来代替更好，主要是本人博客不多也懒得折腾了
    public static String str1="cmd /c start chrome "
            + "https://blog.csdn.net/zcswl7961/article/details/110673210 "
            + "https://blog.csdn.net/zcswl7961/article/details/110494515 "
            + "https://blog.csdn.net/zcswl7961/article/details/103831231 "
            + "https://blog.csdn.net/zcswl7961/article/details/103010917 "
            + "https://blog.csdn.net/zcswl7961/article/details/109582909 "
            + "https://blog.csdn.net/zcswl7961/article/details/110223878";

    // 可以分成两部分，或者更多的部分进行调用
    public static String str2 = "cmd /c start chrome "
            + "https://blog.csdn.net/zcswl7961/article/details/108152595 "
            + "https://blog.csdn.net/zcswl7961/article/details/107631188 "
            + "https://blog.csdn.net/zcswl7961/article/details/107506727 "
            + "https://blog.csdn.net/zcswl7961/article/details/106433265 "
            + "https://blog.csdn.net/zcswl7961/article/details/104923792 "
            + "https://blog.csdn.net/zcswl7961/article/details/103890638";


    public static ArrayList<String> strList=new ArrayList<String>();


    public Reflush(){
        strList.add(str1);
        strList.add(str2);
    }

    public static void main(String args[]) {
        Reflush openUrl=new Reflush();
        while(true){
            int i = 0;
            String strUrl = "";
            while (i < 20000) {
                System.out.println("========================================================");
                System.out.println("系统第【" + i + "】次自动调用开始。。。。。。。");
                strUrl = strList.get(i % 2);
                openFirefoxBrowser(strUrl, str);
                //每关闭一次浏览器，等待大概30s再重启，太过频繁浏览器会爆炸
                try {
                    System.out.println("当前服务器睡眠30秒");
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                i++;
                System.out.println("系统第【" + i + "】次自动调用结束。。。。。。。");
                System.out.println("========================================================");
            }
        }
    }

    //使用指定的浏览器打开
    public static void openFirefoxBrowser(String start,String stop) {
        // 启用cmd运行firefox的方式来打开网址。
        try {
            System.out.println("自动调用：服务器打开chrome， 当前时间：" + System.currentTimeMillis());
            Runtime.getRuntime().exec(start);
            try {
                System.out.println("休眠40秒（保持浏览器访问40秒）");
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("自动调用：服务器关闭chrome， 当前时间：" + System.currentTimeMillis());
            Runtime.getRuntime().exec(stop);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //使用操作系统默认的浏览器打开
    private static void defaultBrowserOpenUrl() {
        // ...
        try {
            Desktop.getDesktop().browse(new URI("http://blog.csdn.net/u012062455/article/details/52369288"));
        } catch (IOException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // 网址被屏蔽了，手动加网址试一下。
    }

}

package com.logging.test;

import java.io.IOException;
import java.util.logging.*;

/**
 * @author zhoucg
 * @date 2020-10-26 17:30
 */
public class JulTest {

    public static void main(String[] args) throws IOException {
        // 简单文本输出信息
        Formatter formatter = new SimpleFormatter();

        // 日志输出类
        Handler handler = new FileHandler("D:\\file.txt");
        handler.setLevel(Level.CONFIG);

        handler.setFormatter(formatter);

        // 定义日志格式
        Logger logger = Logger.getLogger(JulTest.class.getName());
        logger.addHandler(handler);

        logger.info("日志输出");
    }
}

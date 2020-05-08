package com.zcswl.nio;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * 简单的NIOserver服务
 * @author zhoucg
 * @date 2020-04-02 9:14
 */
public class NIOServerDemo {

    private Selector selector;


    public static void main(String[] args) throws IOException {

        // 获取一个ServerSocket通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 设置未非阻塞



    }
}

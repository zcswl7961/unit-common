package com.zcswl.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author zhoucg
 * @date 2020-03-31 15:43
 */
public class SelectorExample {


    public static void main(String[] args) throws IOException {


        /**
         * Selector 是JavaNIO 中能够检测一到多个NIO通道，并能够知晓是否为诸如读写事件做好准备的组件
         * 这样，一个单独的线程能够管理多个Channel，从而管理多个网络连接
         */


//        Selector selector = Selector.open();
//        FileChannel fileChannel = new FileInputStream("G:\\logs\\datacatalog.log").getChannel();

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("http://www.baidu.com",80));


        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        socketChannel.read(byteBuffer);

        byteBuffer.flip();

        byte b = byteBuffer.get();

    }
}

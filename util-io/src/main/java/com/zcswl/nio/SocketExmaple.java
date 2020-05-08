package com.zcswl.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * @author zhoucg
 * @date 2020-04-01 16:53
 */
public class SocketExmaple {


    public static void main(String[] args) throws IOException, InterruptedException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);


        socketChannel.connect(new InetSocketAddress("127.0.0.1",8081));

        if (socketChannel.finishConnect()) {
            int i= 0;
            while (true) {

                TimeUnit.SECONDS.sleep(1);
                String info = "I'm "+i+++"-th information from client";

                byteBuffer.clear();
                byteBuffer.put(info.getBytes());
                byteBuffer.flip();
                while(byteBuffer.hasRemaining()){
                    System.out.println(byteBuffer);
                    socketChannel.write(byteBuffer);
                }

            }
        }

    }
}

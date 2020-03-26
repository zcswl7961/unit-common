package com.zcswl.nio;


import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel
 *
 * @author zhoucg
 * @date 2020-03-22 13:10
 */
public class FileChannelExample {

    public static void main(String[] args) throws IOException {

        FileChannel fileChannel = new FileInputStream("G:\\logs\\datacatalog.log").getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);


        int bytesRead = fileChannel.read(byteBuffer);
        while (bytesRead != -1) {

            System.out.println("Read " + bytesRead);
            byteBuffer.flip();

            while(byteBuffer.hasRemaining()){
                System.out.print((char) byteBuffer.get());
            }
            byteBuffer.clear();
            bytesRead = fileChannel.read(byteBuffer);
        }
        fileChannel.close();

    }
}

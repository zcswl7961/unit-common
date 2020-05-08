package com.zcswl.nio;



import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author zhoucg
 * @date 2020-03-31 14:50
 */
public class BufferExample {

    public static void main(String[] args) throws IOException {

        FileChannel fileChannel = new FileInputStream("G:\\logs\\datacatalog.log").getChannel();


        ByteBuffer buffer = ByteBuffer.allocate(1024);

        byte[] bytes = "周".getBytes();

        buffer.put("a".getBytes());

        buffer.clear();

        buffer.compact();



        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body = ByteBuffer.allocate(1024);

        ByteBuffer[] bufferArray = { header, body };
        fileChannel.read(bufferArray);




    }
}

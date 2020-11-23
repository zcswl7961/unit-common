package com.zcswl.nio;



import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Buffer源码理解：https://javadoop.com/post/java-nio
 *
 * @author zhoucg
 * @date 2020-03-31 14:50
 */
public class BufferExample {

    public static void main(String[] args) throws IOException {

        // 文件通道
        FileChannel fileChannel = new FileInputStream("G:\\logs\\datacatalog.log").getChannel();


        ByteBuffer buffer = ByteBuffer.allocate(1024);

        byte[] bytes = "周".getBytes();

        buffer.put("a".getBytes());
        // 有点重置buffer 的意思。相当于重新实例化了一样。
        // 通常，我们会先填充 Buffer，然后从 Buffer 读取数据，之后我们再重新往里填充新的数据，我们一般在重新填充之前先调用 clear()。
        // clear() 方法并不会将 Buffer 中的数据清空，只不过后续的写入会覆盖掉原来的数据，也就相当于清空了数据了。
        buffer.clear();
        // 而 compact() 方法有点不一样，调用这个方法以后，会先处理还没有读取的数据，也就是 position 到 limit 之间的数据（还没有读过的数据），
        // 先将这些数据移到左边，然后在这个基础上再开始写入。很明显，此时 limit 还是等于 capacity，position 指向原来数据的右边。
        buffer.compact();



        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body = ByteBuffer.allocate(1024);

        ByteBuffer[] bufferArray = { header, body };
        fileChannel.read(bufferArray);




    }
}

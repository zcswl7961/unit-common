package com.zcswl.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author zhoucg
 * @date 2020-03-22 10:41
 */
public class NIOServer {


    /**
     * Selector
     */
    private static Selector selector;

    /**
     * 字符编码
     */
    private Charset charset = Charset.forName("UTF-8");

    //内容协议
    private static String USER_CONTENT_SPLIT = "#@#";
    //用户存在的提示
    private static String USER_EXIST = "系统提示：该昵称已经存在，请换一个昵称";
    //用来记录在线人数，以及昵称
    private static Set<String> users = new HashSet<>();


    public NIOServer(InetSocketAddress address) {

        try {

            selector = Selector.open();

            //获取ServerSocketChannel
            ServerSocketChannel chanel = ServerSocketChannel.open();

            //绑定地址和端口
            chanel.bind(address);

            //设置为非阻塞的，jdk为了兼容，默认为阻塞的
            chanel.configureBlocking(false);

            //将ServerSocketChannel注册到selector上，感兴趣的事件为接受连接
            chanel.register(selector, SelectionKey.OP_ACCEPT);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void listen(){
        try {
            while (true){
                //获取事件，这一步是阻塞的，所以用while(true)没有关系，返回的是基于上一次select之后的事件
                int select = selector.select();
                if(select == 0){
                    continue;
                }
                //返回就绪事件列表 SelectionKey包含事件信息
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    //处理事件业务
                    processKey(selectionKey);
                    //移除事件 因为selector不会自动移除，如果不收到移除，下次selectedKeys()还会继续存在该selectionKey
                    iterator.remove();
                }
            }
        }catch (Exception e){

        }

    }

    private void processKey(SelectionKey selectionKey) throws IOException {
        if(selectionKey.isAcceptable()){//事件类型为接受连接
            //获取对应的ServerSocketChannel
            ServerSocketChannel server = (ServerSocketChannel)selectionKey.channel();
            //为每一个连接创建一个SocketChannel，这个SocketChannel用来读写数据
            SocketChannel client = server.accept();
            //设置为非阻塞
            client.configureBlocking(false);
            //注册selector 感兴趣事件为读数据，意思就是客户端发送写数据时，selector就可以接收并读取数据
            client.register(selector, SelectionKey.OP_READ);
            //继续可以接收连接事件
            selectionKey.interestOps(SelectionKey.OP_ACCEPT);
        }else if(selectionKey.isReadable()){//事件类型为读取数据
            //得到SocketChannel
            SocketChannel client = (SocketChannel)selectionKey.channel();
            //定义缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            StringBuilder content = new StringBuilder();
            while (client.read(buffer) > 0){
                //buffer由写模式变成读模式，因为client.read(buffer)是从管道写数据到缓冲区中
                buffer.flip();
                content.append(charset.decode(buffer));
            }
            //清空缓冲区
            buffer.clear();
            //继续注册读事件类型
            selectionKey.interestOps(SelectionKey.OP_READ);
            //业务处理
            if(content.length() > 0){
                String[] arrayContent = content.toString().split(USER_CONTENT_SPLIT);
                if(arrayContent != null && arrayContent.length == 1) {
                    String nickName = arrayContent[0];
                    if(users.contains(nickName)) {
                        client.write(charset.encode(USER_EXIST));
                    } else {
                        users.add(nickName);
                        int onlineCount = onlineCount();
                        String message = "欢迎 " + nickName + " 进入聊天室! 当前在线人数:" + onlineCount;
                        broadCast(null, message);
                    }
                }
                else if(arrayContent != null && arrayContent.length > 1) {
                    String nickName = arrayContent[0];
                    String message = arrayContent[1];
                    message = nickName + " 说 " + message;
                    if(users.contains(nickName)) {
                        //不回发给发送此内容的客户端
                        broadCast(client, message);
                    }
                }

            }
        }
    }

    public void broadCast(SocketChannel client, String content) throws IOException {
        //广播数据到所有的SocketChannel中
        for(SelectionKey key : selector.keys()) {
            java.nio.channels.Channel targetChannel = key.channel();
            //如果client不为空，不回发给发送此内容的客户端
            if(targetChannel instanceof SocketChannel && targetChannel != client) {
                SocketChannel target = (SocketChannel)targetChannel;
                target.write(charset.encode(content));
            }
        }
    }

    public int onlineCount() {
        int res = 0;
        for(SelectionKey key : selector.keys()){
            Channel target = key.channel();
            if(target instanceof SocketChannel){
                res++;
            }
        }
        return res;
    }


    public static void main(String[] args) {

        // current master_dev test测试数据
        // current master_dev


        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8081);
        new NIOServer(address).listen();
    }

}

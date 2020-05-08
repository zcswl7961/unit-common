package com.zcswl.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.DatagramChannel;
import java.nio.file.Paths;

/**
 * @author zhoucg
 * @date 2020-03-27 11:18
 */
public class ChannelsExample {

    public static void main(String[] args) throws IOException {


        DatagramChannel channel = DatagramChannel.open();

        channel.socket().bind(new InetSocketAddress(9999));

        ServerSocket socket = new ServerSocket(3360);
    }
}

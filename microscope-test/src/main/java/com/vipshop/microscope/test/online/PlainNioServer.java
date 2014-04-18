package com.vipshop.microscope.test.online;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Xu Fei
 * @version 1.0
 */
public class PlainNioServer {

    public void serve(int port) throws IOException {

        ServerSocketChannel channel;
        Selector selector;

        channel = ServerSocketChannel.open();
        ServerSocket ss = channel.socket();

        InetSocketAddress address = new InetSocketAddress(port);

        ss.bind(address);
        channel.configureBlocking(false);
        selector = Selector.open();

        channel.register(selector, SelectionKey.OP_ACCEPT);

        final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());

        while (true) {
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();

            while (iterator.hasNext()) {

                SelectionKey key = iterator.next();
                iterator.remove();
                try {
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel)
                                key.channel();
                        SocketChannel client = server.accept();
                        System.out.println("Accepted connection from " +
                                client);

                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, msg.duplicate());
                    }
                    if (key.isWritable()) {
                        SocketChannel client = (SocketChannel)
                                key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        while (buffer.hasRemaining()) {
                            if (client.write(buffer) == 0) {
                                break;
                            }
                        }
                        client.close();
                    }
                } catch (IOException ex) {
                    key.cancel();
                    try {
                        key.channel().close();
                    } catch (IOException cex) {
                    }
                }
            }
        }


    }
}

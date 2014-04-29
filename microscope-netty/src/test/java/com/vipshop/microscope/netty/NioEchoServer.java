package com.vipshop.microscope.netty;

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

public class NioEchoServer {

    public void serve(int port) throws IOException {
        System.out.println("Listening for connections on port " + port);
        ServerSocketChannel serverChannel;
        Selector selector;

        serverChannel = ServerSocketChannel.open();
        ServerSocket ss = serverChannel.socket();
        InetSocketAddress address = new InetSocketAddress(port);
        /**
         * Bind server to port
         */
        ss.bind(address);
        serverChannel.configureBlocking(false);
        selector = Selector.open();

        /**
         * Register the channel with the selector to be interested
         * in new Client connections that get accepted
         */
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);


        while (true) {

            try {
                /**
                 * Block until something is selected
                 */
                selector.select();
            } catch (IOException ex) {
                ex.printStackTrace();
                // handle in a proper way
                break;
            }

            /**
             * Get all SelectedKey instances
             */
            Set readyKeys = selector.selectedKeys();
            Iterator iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();

                /**
                 * Remove the SelectedKey from the iterator
                 */
                iterator.remove();
                try {
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();

                        /**
                         * Accept the client connection
                         */
                        SocketChannel client = server.accept();
                        System.out.println("Accepted connection from " + client);
                        client.configureBlocking(false);

                        /**
                         * Register connection to selector and set ByteBuffer
                         */
                        client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, ByteBuffer.allocate(100));
                    }

                    /**
                     * Check for SelectedKey for read
                     */
                    if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer output = (ByteBuffer) key.attachment();

                        /**
                         * Read data to ByteBuffer
                         */
                        client.read(output);
                    }

                    /**
                     * Check for SelectedKey for write
                     */
                    if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer output = (ByteBuffer) key.attachment();
                        output.flip();
                        /**
                         * Write data from ByteBuffer to channel
                         */
                        client.write(output);
                        output.compact();
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

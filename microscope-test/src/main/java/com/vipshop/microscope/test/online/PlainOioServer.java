package com.vipshop.microscope.test.online;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Xu Fei
 * @version 1.0
 */
public class PlainOioServer {

    public void serve(int port) throws IOException {

        ServerSocket socket = new ServerSocket(port);

        while (true) {

            final Socket clientSocket = socket.accept();

            System.out.print("Accepted connection from client socket");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    //To change body of implemented methods use File | Settings | File Templates.
                    try {
                        OutputStream out = clientSocket.getOutputStream();

                        out.write("hi \r\n".getBytes());
                        out.flush();
                        clientSocket.close();

                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                        try {
                            clientSocket.close();
                        } catch (IOException e1) {
                            //
                        }
                    }
                }
            }).start();
        }
    }
}

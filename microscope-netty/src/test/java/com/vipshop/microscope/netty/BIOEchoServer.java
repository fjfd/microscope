package com.vipshop.microscope.netty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BioEchoServer {

    public void serve(int port) throws IOException {
        /**
         * bind server on port
         */
        final ServerSocket socket = new ServerSocket(port);
        try {
            while (true) {

                // block until new client connection is accepted
                final Socket clientSocket = socket.accept();
                System.out.println("Accepted connection from " + clientSocket);

                // create new thread to handle client connection
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(clientSocket.getInputStream()));
                            PrintWriter writer = new PrintWriter(clientSocket
                                    .getOutputStream(), true);
                            // read data from client and write it back
                            while(true) {
                                writer.println(reader.readLine());
                                writer.flush();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                            try {
                                clientSocket.close();
                            } catch (IOException ex) {
                                // ignore on close
                            }
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
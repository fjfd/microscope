package com.vipshop.microscope.web.server.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

import com.vipshop.microscope.web.server.cons.Constant;

public class HttpServer {
	public static void main(String args[]) {
		int port;
		ServerSocket server_socket;
		try {
			port = Integer.parseInt(args[0]);
		} catch (Exception e) {
			port = 80;
		}
		try {

			server_socket = new ServerSocket(port);
			System.out.println("httpServer running on port " + server_socket.getLocalPort());

			// server infinite loop
			while (true) {
				Socket socket = server_socket.accept();
				System.out.println("New connection accepted " + socket.getInetAddress() + ":" + socket.getPort());

				// Construct handler to process the HTTP request message.
				try {
					httpRequestHandler request = new httpRequestHandler(socket);
					// Create a new thread to process the request.
					Thread thread = new Thread(request);

					// Start the thread.
					thread.start();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}

class httpRequestHandler implements Runnable {
	final static String CRLF = "\r\n";

	Socket socket;

	InputStream input;

	OutputStream output;

	BufferedReader br;

	public httpRequestHandler(Socket socket) throws Exception {
		this.socket = socket;
		this.input = socket.getInputStream();
		this.output = socket.getOutputStream();
		this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public void run() {
		try {
			processRequest();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void processRequest() throws Exception {
		while (true) {

			String headerLine = br.readLine();
			System.out.println(headerLine);
			if (headerLine.equals(CRLF) || headerLine.equals(""))
				break;

			StringTokenizer s = new StringTokenizer(headerLine);
			String temp = s.nextToken();

			if (temp.equals("GET")) {

				String fileName = s.nextToken();
				fileName = "." + fileName;

				FileInputStream fis = null;
				boolean fileExists = true;
				try {
					fis = new FileInputStream(Constant.ROOTPATH + fileName);
				} catch (FileNotFoundException e) {
					fileExists = false;
				}
				String serverLine = "Server: Simple Java Http Server";
				String statusLine = null;
				String contentTypeLine = null;
				String entityBody = null;
				String contentLengthLine = "error";
				if (fileExists) {
					statusLine = "HTTP/1.0 200 OK" + CRLF;
					contentTypeLine = "Content-type: " + contentType(fileName) + CRLF;
					contentLengthLine = "Content-Length: " + (new Integer(fis.available())).toString() + CRLF;
				} else {
					statusLine = "HTTP/1.0 404 Not Found" + CRLF;
					contentTypeLine = "text/html";
					entityBody = "<HTML>" + "<HEAD><TITLE>404 Not Found</TITLE></HEAD>" + "<BODY>404 Not Found" + "<br>usage:http://yourHostName:port/" + "fileName.html</BODY></HTML>";
				}

				// Send the status line.
				output.write(statusLine.getBytes());
				System.out.println(statusLine);

				// Send the server line.
				output.write(serverLine.getBytes());
				System.out.println(serverLine);

				// Send the content type line.
				output.write(contentTypeLine.getBytes());
				System.out.println(contentTypeLine);

				// Send the Content-Length
				output.write(contentLengthLine.getBytes());
				System.out.println(contentLengthLine);

				// Send a blank line to indicate the end of the header lines.
				output.write(CRLF.getBytes());
				System.out.println(CRLF);

				// Send the entity body.
				if (fileExists) {
					sendBytes(fis, output);
					fis.close();
				} else {
					output.write(entityBody.getBytes());
				}

			}
		}

		try {
			output.close();
			br.close();
			socket.close();
		} catch (Exception e) {
		}
	}

	private static void sendBytes(FileInputStream fis, OutputStream os) throws Exception {

		byte[] buffer = new byte[1024];
		int bytes = 0;

		while ((bytes = fis.read(buffer)) != -1) {
			os.write(buffer, 0, bytes);
		}
	}

	private static String contentType(String fileName) {
		if (fileName.endsWith(".htm") || fileName.endsWith(".html") || fileName.endsWith(".txt")) {
			return "text/html";
		} else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
			return "image/jpeg";
		} else if (fileName.endsWith(".gif")) {
			return "image/gif";
		} else {
			return "application/octet-stream";
		}
	}
}

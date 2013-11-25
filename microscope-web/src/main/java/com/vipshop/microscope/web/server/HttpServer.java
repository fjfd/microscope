package com.vipshop.microscope.web.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.common.util.ThreadPoolUtil;
import com.vipshop.microscope.web.server.handle.AnalyseHandle;

public class HttpServer implements Runnable {
	
	private static Logger logger = LoggerFactory.getLogger(HttpServer.class);

	public static void main(String[] args) throws IOException {
		int port = 80;
		
		try {
			port = Integer.parseInt(args[0]);
		} catch (Exception e) {
			port = 80;
		}
		
		Selector selector;

		selector = Selector.open();

		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.configureBlocking(false);
		ssc.socket().bind(new InetSocketAddress(port));
		ssc.register(selector, SelectionKey.OP_ACCEPT);

		logger.info("start server on port " + port);

		while (true) {

			selector.select();

			Set<SelectionKey> set = selector.selectedKeys();
			Iterator<SelectionKey> it = set.iterator();

			while (it.hasNext()) {

				SelectionKey key = it.next();
				it.remove();

				if (key.isAcceptable()) {
					new Session(key);
				} else if (key.isReadable()) {
					key.interestOps(0);
					Session session = (Session) key.attachment();
					AnalyseHandle analyseHandle = new AnalyseHandle(session);
					ThreadPoolUtil.getHttpServerExecutor().execute(analyseHandle);
				}

			}
		}
	}

	@Override
	public void run() {
		int port = 80;
		
		Selector selector;
		
		try {
			
			selector = Selector.open();
			
			ServerSocketChannel ssc = ServerSocketChannel.open();
			ssc.configureBlocking(false);
			ssc.socket().bind(new InetSocketAddress(port));
			ssc.register(selector, SelectionKey.OP_ACCEPT);
			
			logger.info("start server on port " + port);
			
			while (true) {
				
				selector.select();
				
				Set<SelectionKey> set = selector.selectedKeys();
				Iterator<SelectionKey> it = set.iterator();
				
				while (it.hasNext()) {
					
					SelectionKey key = it.next();
					it.remove();
					
					if (key.isAcceptable()) {
						new Session(key);
					} else if (key.isReadable()) {
						key.interestOps(0);
						Session session = (Session) key.attachment();
						AnalyseHandle analyseHandle = new AnalyseHandle(session);
						ThreadPoolUtil.getHttpServerExecutor().execute(analyseHandle);
					}
					
				}
			}
		} catch (Exception e) {
			
		}
		
	}
}

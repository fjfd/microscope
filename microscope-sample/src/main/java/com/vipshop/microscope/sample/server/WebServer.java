package com.vipshop.microscope.sample.server;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

public class WebServer implements Runnable {

	private static final String LOG_PATH = "./target/logs/access/yyyy_mm_dd.request.log";

	private static final String WEB_XML = "WEB-INF/web.xml";
	private static final String CLASS_ONLY_AVAILABLE_IN_IDE = "com.sjl.IDE";
	private static final String PROJECT_RELATIVE_PATH_TO_WEBAPP = "src/main/java/META-INF/webapp";

	public static interface WebContext {
		public File getWarPath();
		public String getContextPath();
	}
	
	private Server server;
	private int port;
	private String bindInterface;

	public WebServer(int aPort) {
		this(aPort, null);
	}

	public WebServer(int aPort, String aBindInterface) {
		port = aPort;
		bindInterface = aBindInterface;
	}
	
	public void start() throws Exception {
		server = new Server();

		server.setThreadPool(createThreadPool());
		server.addConnector(createConnector());
		server.setHandler(createHandlers());
		server.setStopAtShutdown(true);
		
		server.start();
	}

	public void join() throws InterruptedException {
		server.join();
	}

	public void stop() throws Exception {
		server.stop();
	}

	private ThreadPool createThreadPool() {
		// TODO: You should configure these appropriately
		// for your environment - this is an example only
		QueuedThreadPool threadPool = new QueuedThreadPool();
		threadPool.setMinThreads(100);
		threadPool.setMaxThreads(1000);
		return threadPool;
	}

	private SelectChannelConnector createConnector() {
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(port);
		connector.setHost(bindInterface);
		return connector;
	}

	private HandlerCollection createHandlers() {
		WebAppContext ctx = new WebAppContext();
		ctx.setContextPath("/");

		if (isRunningInShadedJar()) {
			ctx.setWar(getShadedWarUrl());
		} else {
			ctx.setWar(PROJECT_RELATIVE_PATH_TO_WEBAPP);
		}

		List<Handler> handlers = new ArrayList<Handler>();

		handlers.add(ctx);

		HandlerList contexts = new HandlerList();
		contexts.setHandlers(handlers.toArray(new Handler[0]));

		RequestLogHandler log = new RequestLogHandler();
		log.setRequestLog(createRequestLog());

		HandlerCollection result = new HandlerCollection();
		result.setHandlers(new Handler[] { contexts, log });

		return result;
	}

	private RequestLog createRequestLog() {
		NCSARequestLog log = new NCSARequestLog();

		File logPath = new File(LOG_PATH);
		logPath.getParentFile().mkdirs();

		log.setFilename(logPath.getPath());
		log.setRetainDays(90);
		log.setExtended(false);
		log.setAppend(true);
		log.setLogTimeZone("GMT");
		log.setLogLatency(true);
		return log;
	}

	private boolean isRunningInShadedJar() {
		try {
			Class.forName(CLASS_ONLY_AVAILABLE_IN_IDE);
			return false;
		} catch (ClassNotFoundException anExc) {
			return true;
		}
	}

	private URL getResource(String aResource) {
		return Thread.currentThread().getContextClassLoader().getResource(aResource);
	}

	private String getShadedWarUrl() {
		// Strip off "WEB-INF/web.xml"
		String urlStr = getResource(WEB_XML).toString();
		return urlStr.substring(0, urlStr.length() - 15);
	}

	@Override
	public void run() {
		server = new Server(9090);

		server.setThreadPool(createThreadPool());
		server.addConnector(createConnector());
		server.setHandler(createHandlers());
		server.setStopAtShutdown(true);

		try {
			server.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		new WebServer(9090).start();
	}
}
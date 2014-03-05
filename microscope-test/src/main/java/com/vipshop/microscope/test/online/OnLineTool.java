package com.vipshop.microscope.test.online;

import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.common.span.Category;
import com.vipshop.microscope.common.thrift.Span;
import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.common.util.IPAddressUtil;
import com.vipshop.microscope.common.util.SpanMockUtil;
import com.vipshop.microscope.storage.hbase.factory.HbaseFactory;
import com.vipshop.microscope.storage.mysql.domain.TopReport;
import com.vipshop.microscope.storage.mysql.factory.MySQLFactory;
import com.vipshop.microscope.trace.Tracer;

/**
 * Test tool online
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class OnLineTool {
	
	public static final String TRACE = "trace";
	public static final String HBASE = "hbase";
	public static final String MYSQL = "mysql";
	public static final String IP = "ip";
	public static final String IPCahe = "cache";
	
	public static void main(String[] args) throws Exception {
		String app = System.getProperty("app");
		if (app.equals(TRACE)) {
			trace();
		}
		if (app.equals(HBASE)) {
			hbase();
		}
		if (app.equals(MYSQL)) {
			mysql();
		}
		if (app.contains("ip")) {
			ipadress(app);
		}
		if (app.contains("cache")) {
			ipadressCache(app);
		}
		
	}
	
	public static void trace() throws InterruptedException {
		Tracer.clientSend("example", Category.Method);
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (Exception e) {
			Tracer.setResultCode(e);
		} finally {
			Tracer.clientReceive();
		}
		TimeUnit.SECONDS.sleep(10);
	}
	
	public static void hbase() {
		HbaseFactory.reinit();
	}
	
	public static void mysql() {
		TopReport report = new TopReport();
		CalendarUtil calendarUtil = new CalendarUtil();
		Span span = SpanMockUtil.mockSpan();

		report.updateReportInit(calendarUtil, span);
		report.updateReportNext(span);
		MySQLFactory.TOP.empty();

		report.saveReport();
		
		System.out.println(MySQLFactory.TOP.find(6));
		
		MySQLFactory.TOP.empty();
	}
	
	public static void ipadress(String app) throws UnknownHostException {
		int count = Integer.valueOf(app.split(":")[1]);
		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			IPAddressUtil.getLocalHost().getHostAddress();
		}
		long end = System.currentTimeMillis();
		
		System.out.println("get " + count + "  IPAdress time takes " + (end - start));
	}
	
	public static void ipadressCache(String app) throws UnknownHostException {
		int count = Integer.valueOf(app.split(":")[1]);
		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			IPAddressUtil.IPAddress();
		}
		long end = System.currentTimeMillis();
		
		System.out.println("get " + count + "  IPAdress time takes " + (end - start));
	}

	
}

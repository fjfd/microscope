package com.vipshop.microscope.test.online;

import java.util.concurrent.TimeUnit;

import com.vipshop.micorscope.framework.span.Category;
import com.vipshop.micorscope.framework.util.CalendarUtil;
import com.vipshop.microscope.report.condition.TopReportCondition;
import com.vipshop.microscope.report.domain.TopReport;
import com.vipshop.microscope.report.factory.MySQLFactory;
import com.vipshop.microscope.storage.hbase.HbaseRepository;
import com.vipshop.microscope.thrift.gen.Span;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.ResultCode;

public class MicroscopeTest {
	
	public static final String TRACE = "trace";
	public static final String HBASEREINIT = "hbasereinit";
	public static final String TESTMYSQL = "testmysql";
	
	public static void main(String[] args) throws Exception {
		String app = System.getProperty("app");
		
		if (app.equals(TRACE)) {
			trace();
		}
		
		if (app.equals(HBASEREINIT)) {
			hbasereinit();
		}
		
		if (app.equals(TESTMYSQL)) {
			testmysql();
		}
	}
	
	private static void trace() {
		Tracer.clientSend("example", Category.METHOD);
		
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
			System.out.println("example method invoke");
		} catch (Exception e) {
			Tracer.setResultCode(ResultCode.EXCEPTION);
		} finally {
			Tracer.clientReceive();
		}
		
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void hbasereinit() {
		HbaseRepository.drop();
		HbaseRepository.init();
	}
	
	public static void testmysql() {
		Span span = new Span();
		
		span.setAppName("appname");
		span.setAppIp("localhost");
		span.setTraceId(8053381312019065847L);
		span.setParentId(8053381312019065847L);
		span.setSpanId(8053381312019065847L);
		span.setSpanName("test");
		span.setSpanType("Method");
		span.setResultCode("OK");
		span.setStartTime(System.currentTimeMillis());
		span.setDuration(1000);
		span.setServerName("Service");
		span.setServerIp("localhost");
		
		TopReport report = new TopReport();

		CalendarUtil calendarUtil = new CalendarUtil();

		report.updateReportInit(calendarUtil, span);
		report.updateReportNext(span);
		
		MySQLFactory.TOP.empty();

		report.saveReport();
		
		System.out.println(MySQLFactory.TOP.find(new TopReportCondition()));
		MySQLFactory.TOP.empty();
	}
}

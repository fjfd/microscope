package com.vipshop.microscope.test.online;

import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.common.trace.Category;
import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.storage.StorageRepository;
import com.vipshop.microscope.storage.mysql.domain.TopReport;
import com.vipshop.microscope.storage.mysql.factory.RepositoryFactory;
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
		StorageRepository.getStorageRepository().initHbaseTable();
	}
	
	public static void mysql() {
		TopReport report = new TopReport();
		CalendarUtil calendarUtil = new CalendarUtil();
		Span span = new Span();

		report.updateReportInit(calendarUtil, span);
		report.updateReportNext(span);
		RepositoryFactory.getTopReportRepository().empty();

		report.saveReport();
		
		System.out.println(RepositoryFactory.getTopReportRepository().find(6));
		
		RepositoryFactory.getTopReportRepository().empty();
	}
	
}

package com.vipshop.microscope.collector.report;

import java.util.concurrent.ExecutorService;

import com.vipshop.microscope.common.util.ThreadPoolUtil;

public class ReportWriterExecutor {
	
	public static void startReportWriter() {
		ExecutorService executor = ThreadPoolUtil.newSingleDaemonThreadExecutor("report-wirter");
		executor.execute(new ReportWriter());
	}
}

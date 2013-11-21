package com.vipshop.microscope.collector.analyzer.report;

import com.vipshop.microscope.mysql.report.MsgReport;
import com.vipshop.microscope.mysql.report.OverTimeReport;
import com.vipshop.microscope.mysql.report.SourceReport;
import com.vipshop.microscope.mysql.report.TraceReport;
import com.vipshop.microscope.mysql.repository.MysqlRepository;

public class ReportRepository {
	
	private static final MysqlRepository repository = MysqlRepository.getRepository();
	
	public static void save(MsgReport report) {
		repository.save(report);
	}
	
	public static void save(TraceReport report) {
		repository.save(report);
	}
	
	public static void save(OverTimeReport report) {
		repository.save(report);
	}
	
	public static void save(SourceReport report) {
		repository.save(report);
	}
}

package com.vipshop.microscope.storage.mysql;

import java.util.ArrayList;
import java.util.List;

import com.vipshop.microscope.common.thrift.Span;
import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.storage.mysql.domain.AbstraceReport;
import com.vipshop.microscope.storage.mysql.domain.DepenReport;
import com.vipshop.microscope.storage.mysql.domain.MsgReport;
import com.vipshop.microscope.storage.mysql.domain.ProblemOverTimeReport;
import com.vipshop.microscope.storage.mysql.domain.ProblemReport;
import com.vipshop.microscope.storage.mysql.domain.SourceReport;
import com.vipshop.microscope.storage.mysql.domain.TopReport;
import com.vipshop.microscope.storage.mysql.domain.TraceOverTimeReport;
import com.vipshop.microscope.storage.mysql.domain.TraceReport;

public class MySQLStoreEngine {

	private final List<AbstraceReport> reports = new ArrayList<AbstraceReport>(8);
	
	public MySQLStoreEngine() {
		reports.add(new MsgReport());
		reports.add(new TopReport());
		reports.add(new DepenReport());
		reports.add(new SourceReport());
		reports.add(new TraceReport());
		reports.add(new TraceOverTimeReport());
		reports.add(new ProblemReport());
		reports.add(new ProblemOverTimeReport());
	}
	
	public void analyze(Span span) {
		CalendarUtil calendarUtil = new CalendarUtil();
		for (AbstraceReport report : reports) {
			report.analyze(calendarUtil, span);
		}
	}

}

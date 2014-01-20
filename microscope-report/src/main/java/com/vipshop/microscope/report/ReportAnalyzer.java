package com.vipshop.microscope.report;

import java.util.ArrayList;
import java.util.List;

import com.vipshop.micorscope.framework.thrift.Span;
import com.vipshop.micorscope.framework.util.CalendarUtil;
import com.vipshop.microscope.report.domain.AbstraceReport;
import com.vipshop.microscope.report.domain.DepenReport;
import com.vipshop.microscope.report.domain.MsgReport;
import com.vipshop.microscope.report.domain.ProblemOverTimeReport;
import com.vipshop.microscope.report.domain.ProblemReport;
import com.vipshop.microscope.report.domain.SourceReport;
import com.vipshop.microscope.report.domain.TopReport;
import com.vipshop.microscope.report.domain.TraceOverTimeReport;
import com.vipshop.microscope.report.domain.TraceReport;

public class ReportAnalyzer {
	
	private final List<AbstraceReport> reports = new ArrayList<AbstraceReport>(8);
	
	public ReportAnalyzer() {
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

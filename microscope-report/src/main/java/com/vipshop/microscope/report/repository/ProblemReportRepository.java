package com.vipshop.microscope.report.repository;

import com.vipshop.microscope.report.domain.ProblemReport;

public interface ProblemReportRepository {
	
	public void empty();
	
	public void saveProblemReport(ProblemReport report);
	
//	public void saveOverTimeReport(ProblemOverTimeReport report);

//	public void emptyOverTime();
}

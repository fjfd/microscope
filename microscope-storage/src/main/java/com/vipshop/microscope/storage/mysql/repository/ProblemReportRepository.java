package com.vipshop.microscope.storage.mysql.repository;

import com.vipshop.microscope.storage.mysql.domain.ProblemReport;

public interface ProblemReportRepository {
	
	public void empty();
	
	public void saveProblemReport(ProblemReport report);
	
//	public void saveOverTimeReport(ProblemOverTimeReport report);

//	public void emptyOverTime();
}

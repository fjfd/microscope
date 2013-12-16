package com.vipshop.microscope.report.repository;

import com.vipshop.microscope.report.domain.DepenReport;

public interface DepenReportRepository {
	
	public void empty();
	
	public void save(DepenReport report);
}

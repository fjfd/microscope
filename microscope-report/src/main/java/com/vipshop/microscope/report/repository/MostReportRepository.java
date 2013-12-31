package com.vipshop.microscope.report.repository;

import java.util.List;

import com.vipshop.microscope.report.domain.MostReport;

public interface MostReportRepository {
	
	public List<MostReport> findMostReport(int type);
}

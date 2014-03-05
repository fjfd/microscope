package com.vipshop.microscope.storage.mysql.repository;

import java.util.List;

import com.vipshop.microscope.storage.mysql.domain.MostReport;

public interface MostReportRepository {
	
	public List<MostReport> findMostReport(int type);
}

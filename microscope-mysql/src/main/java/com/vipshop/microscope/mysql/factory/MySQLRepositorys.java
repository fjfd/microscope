package com.vipshop.microscope.mysql.factory;

import com.vipshop.microscope.mysql.repository.MsgReportRepository;
import com.vipshop.microscope.mysql.repository.TraceReportRepository;

public class MySQLRepositorys {
	
	public static final TraceReportRepository TRACE_REPORT = new TraceReportRepository();
	public static final MsgReportRepository MSG_STAT_REPOSITORY = new MsgReportRepository();
	
	
}

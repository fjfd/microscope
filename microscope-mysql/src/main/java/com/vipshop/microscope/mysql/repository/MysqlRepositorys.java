package com.vipshop.microscope.mysql.repository;

public class MysqlRepositorys {
	
	public static final TraceStatRepository traceStatRepository = new TraceStatRepository();
	
	public static TraceStatRepository getTraceStatRepository() {
		return traceStatRepository;
	}
}

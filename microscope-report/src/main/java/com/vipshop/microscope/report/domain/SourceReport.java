package com.vipshop.microscope.report.domain;

import com.vipshop.micorscope.framework.util.CalendarUtil;
import com.vipshop.micorscope.framework.util.IPAddressUtil;
import com.vipshop.micorscope.framework.util.MathUtil;
import com.vipshop.micorscope.framework.util.TimeStampUtil;
import com.vipshop.microscope.report.factory.MySQLRepository;
import com.vipshop.microscope.thrift.gen.Span;

/**
 * DB invoke source dist report.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class SourceReport extends AbstraceReport {
	
	private String appName;
	private int appIp;
	
	private String serverName;
	private String sqlType;
	
	private long totalCount;
	private long failCount;
	private float failPercent;
	
	private float avg;
	private long sum;
	
	private long startTime;
	private long endTime;
	
	private float qps;
	
	@Override
	public void updateReportInit(CalendarUtil calendarUtil, Span span) {
		String name = span.getSpanName();
		this.setDateByHour(calendarUtil);
		this.setAppName(span.getAppName());
		this.setAppIp(IPAddressUtil.intIPAddress(span.getAppIp()));
		this.setServerName(span.getServerName());
		this.setSqlType(buildSQLType(name));
		this.setStartTime(System.currentTimeMillis());
	}
	
	@Override
	public void updateReportNext(Span span) {
		if (!span.getResultCode().equals("OK")) {
			this.setFailCount(1);
		}
		this.setSum(this.getSum() + span.getDuration());
		this.setTotalCount(this.getTotalCount() + 1);
		this.setEndTime(System.currentTimeMillis());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.vipshop.microscope.mysql.report.AbstraceReport#updateBeforeSave()
	 */
	@Override
	public void saveReport() {
		long count = this.getTotalCount();
		long sumDura = this.getSum();
		long time = this.getEndTime() - this.getStartTime();
		long fail = this.getFailCount();
		this.setAvg(MathUtil.calculateAvgDura(count, sumDura));
		this.setQps(MathUtil.calculateTPS(count, time));
		this.setFailPercent(MathUtil.calculateFailPre(count, fail));
		
		MySQLRepository.getRepository().save(this);
	}
	
	public static String getKey(CalendarUtil calendar, Span span) {
		String app = span.getAppName();
		String type = span.getSpanType();
		String name = span.getSpanName();
		String sqlType = buildSQLType(name);
		StringBuilder builder = new StringBuilder();
		builder.append(TimeStampUtil.timestampOfCurrentHour(calendar))
			   .append("-").append(app)
			   .append("-").append(type)
		   	   .append("-").append(span.getServerName())
		   	   .append("-").append(sqlType);
		return builder.toString();
	}
	
	public static String getPrevKey(CalendarUtil calendar, Span span) {
		String app = span.getAppName();
		String type = span.getSpanType();
		String name = span.getSpanName();
		String sqlType = buildSQLType(name);
		StringBuilder builder = new StringBuilder();
		builder.append(TimeStampUtil.timestampOfPrevHour(calendar))
			   .append("-").append(app)
			   .append("-").append(type)
		   	   .append("-").append(span.getServerName())
		   	   .append("-").append(sqlType);
		return builder.toString();
	}
	
	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getFailCount() {
		return failCount;
	}

	public void setFailCount(long fail) {
		this.failCount = fail;
	}

	public float getFailPercent() {
		return failPercent;
	}

	public void setFailPercent(float failpre) {
		this.failPercent = failpre;
	}

	public float getAvg() {
		return avg;
	}

	public void setAvg(float avgDuration) {
		this.avg = avgDuration;
	}

	public int getAppIp() {
		return appIp;
	}

	public void setAppIp(int app) {
		this.appIp = app;
	}

	public static String buildSQLType(String name) {
		name = name.toLowerCase();
		if (name.contains("select")) {
			return "read";
		}
		return "write";
	}
	
	public String getSqlType() {
		return sqlType;
	}

	public void setSqlType(String serviceName) {
		this.sqlType = serviceName;
	}
	
	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serviceType) {
		this.serverName = serviceType;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long count) {
		this.totalCount = count;
	}

	public float getQps() {
		return qps;
	}

	public void setQps(float tps) {
		this.qps = tps;
	}

	public long getSum() {
		return sum;
	}

	public void setSum(long sumDura) {
		this.sum = sumDura;
	}

	public String getAppName() {
		return appName;
	}
	
	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Override
	public String toString() {
		return super.toString() + " SourceReport content [app=" + appIp + ", serverType=" + serverName + ", sqlType=" + 
														  sqlType + ", count=" + totalCount + ", fail=" + failCount + ", failpre=" + failPercent + ", avgDura=" + 
														  avg + ", sumDura=" + sum + ", startTime=" + startTime + ", endTime=" + endTime + ", tps=" + qps + "]";
	}
	
}

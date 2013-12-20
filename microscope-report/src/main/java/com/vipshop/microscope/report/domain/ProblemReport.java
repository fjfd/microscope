package com.vipshop.microscope.report.domain;

import com.vipshop.micorscope.framework.span.Category;
import com.vipshop.micorscope.framework.util.CalendarUtil;
import com.vipshop.micorscope.framework.util.IPAddressUtil;
import com.vipshop.micorscope.framework.util.TimeStampUtil;
import com.vipshop.microscope.report.factory.MySQLRepository;
import com.vipshop.microscope.thrift.gen.Span;

/**
 * Problem Report.
 * 
 * <p>Currently we have these kinds of problem:
 * 
 * <pre>
 *  Long-URL problem 
 *  Long-Action problem 
 *  Long-Db problem
 *  Long-Service problem
 *  Long-Cache problem
 *  Long-Method problem
 *  Long-System problem
 * </pre>
 * 
 * This report are stated by 5 minute.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class ProblemReport extends AbstraceReport {
	
	private String appName;
	private int appIp;
	
	private int proType;
	private int proTime;
	private String proDesc;

	private int proCount;
	
	private long traceId;
	
	public static boolean hasProblme(Span span) {
		return Category.hasProblem(span);
	}
	
	public static int getTypeZone(Span span) {
		return Category.getIntValue(span);
	}
	
	public static int getTimeZone(Span span) {
		return Category.getTimeZone(span);
	}
	
	@Override
	public void updateReportInit(CalendarUtil calendarUtil, Span span) {
		this.setDateByHour(calendarUtil);
		this.setAppName(span.getAppName());
		this.setAppIp(IPAddressUtil.intIPAddress(span.getAppIp()));
		this.setProType(getTimeZone(span));
		this.setProTime(getTimeZone(span));
		this.setProDesc(span.getSpanName());
	}
	
	@Override
	public void updateReportNext(Span span) {
		this.setProCount(this.getProCount() + 1);
		this.setProDesc(span.getSpanName() + "#" + span.getTraceId());
	}
	
	@Override
	public void saveReport() {
		MySQLRepository.getRepository().save(this);
	}
	
	public static String getKey(CalendarUtil calendar, Span span) {
		String appName = span.getAppName();
		String appIp = span.getAppIp();
		int typeZone = ProblemReport.getTypeZone(span);
		int timeZone = ProblemReport.getTimeZone(span);
		String name = span.getSpanName();
		StringBuilder builder = new StringBuilder();
		builder.append(TimeStampUtil.timestampOfCurrentHour(calendar))
			   .append("-").append(appName)
			   .append("-").append(appIp)
			   .append("-").append(typeZone)
			   .append("-").append(timeZone)
			   .append("-").append(name);
		return builder.toString();
	}
	
	public static String getPrevKey(CalendarUtil calendar, Span span) {
		String appName = span.getAppName();
		String appIp = span.getAppIp();
		int typeZone = ProblemReport.getTypeZone(span);
		int timeZone = ProblemReport.getTimeZone(span);
		String name = span.getSpanName();
		StringBuilder builder = new StringBuilder();
		builder.append(TimeStampUtil.timestampOfPrevHour(calendar))
			   .append("-").append(appName)
			   .append("-").append(appIp)
			   .append("-").append(typeZone)
			   .append("-").append(timeZone)
			   .append("-").append(name);
		return builder.toString();
	}
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public int getAppIp() {
		return appIp;
	}

	public void setAppIp(int appIp) {
		this.appIp = appIp;
	}

	public int getProType() {
		return proType;
	}

	public void setProType(int problemType) {
		this.proType = problemType;
	}

	public int getProTime() {
		return proTime;
	}

	public void setProTime(int timeZone) {
		this.proTime = timeZone;
	}

	public int getProCount() {
		return proCount;
	}

	public void setProCount(int count) {
		this.proCount = count;
	}

	public String getProDesc() {
		return proDesc;
	}

	public void setProDesc(String desc) {
		this.proDesc = desc;
	}

	public long getTraceId() {
		return traceId;
	}

	public void setTraceId(long traceId) {
		this.traceId = traceId;
	}

	@Override
	public String toString() {
		return super.toString() + " ProblemReport content " +
				                  " [appName=" + appName + ", appIp=" + appIp + 
				                  ", problemType=" + proType + " ,timeZone=" + proTime + 
				                  ", count=" + proCount + ", desc=" + proDesc + ", year=" + year + ", " +
				                  "month=" + month + ", week=" + week + ", day=" + day + ", " +
				                  "hour=" + hour + ", minute=" + minute + "]";
	}
	
}

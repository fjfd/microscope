package com.vipshop.microscope.report.domain;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.micorscope.framework.span.Category;
import com.vipshop.micorscope.framework.thrift.Span;
import com.vipshop.micorscope.framework.util.CalendarUtil;
import com.vipshop.micorscope.framework.util.IPAddressUtil;
import com.vipshop.micorscope.framework.util.MathUtil;
import com.vipshop.micorscope.framework.util.TimeStampUtil;
import com.vipshop.microscope.report.factory.MySQLRepository;

/**
 * Stat spans in trace
 * 
 * stat: min duration
 *       max duration
 *       avg duration
 *       qps
 *       count
 *       fail count
 *       fail percentage
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class TraceReport extends AbstraceReport {
	
	private static final Logger logger = LoggerFactory.getLogger(TraceReport.class);
	
	private static final ConcurrentHashMap<String, TraceReport> traceContainer = new ConcurrentHashMap<String, TraceReport>();
	
	private String appName;
	private int appIp;
	private int type;
	private String name;
	
	private long totalCount;
	private long failCount;
	private float failPercent;
	
	private float min;
	private float max;
	private float avg;
	private float qps;
	
	private long sum;
	
	private long startTime;
	private long endTime;
	
	private int region_0;
	private int region_1;
	private int region_2;
	private int region_3;
	private int region_4;
	private int region_5;
	private int region_6;
	private int region_7;
	private int region_8;
	private int region_9;
	private int region_10;
	private int region_11;
	private int region_12;
	private int region_13;
	private int region_14;
	private int region_15;
	private int region_16;
	
	@Override
	public void analyze(CalendarUtil calendarUtil, Span span) {
		String key = this.getKey(calendarUtil, span);
		TraceReport report = traceContainer.get(key);
		if (report == null) {
			report = new TraceReport();
			report.updateReportInit(calendarUtil, span);
		}
		report.updateReportNext(span);
		traceContainer.put(key, report);
		
		Set<Entry<String, TraceReport>> entries = traceContainer.entrySet();
		for (Entry<String, TraceReport> entry : entries) {
			String prevKey = entry.getKey();
			if (!prevKey.equals(key)) {
				TraceReport prevReport = entry.getValue();
				try {
					prevReport.saveReport();
				} catch (Exception e) {
					logger.error("save trace report to mysql error ... " + e);
				} finally {
					traceContainer.remove(prevKey);
				}
			}
		}

	}
	
	@Override
	public void updateReportInit(CalendarUtil calendarUtil, Span span) {
		String app = span.getAppName();
		String ipAdress = span.getAppIp();
		String name = span.getSpanName();
		long startTime = span.getStartTime();
		int duration = span.getDuration();
		
		this.setDateByHour(calendarUtil);
		this.setAppName(app);
		this.setAppIp(IPAddressUtil.intIPAddress(ipAdress));
		this.setType(Category.getIntValue(span));
		this.setName(name);
		this.setStartTime(startTime);
		this.setMin(duration);
		this.setMax(duration);
		this.setAvg(duration);
	}
	
	@Override
	public void updateReportNext(Span span) {
		String resultCode = span.getResultCode();
		int duration = span.getDuration();
		
		if (duration < this.getMin()) {
			this.setMin(duration);
		}
		if (duration > this.getMax()) {
			this.setMax(duration);
		}
		if (!resultCode.equals("OK")) {
			this.setFailCount(this.getFailCount() + 1);
		}
		this.setTotalCount(this.getTotalCount() + 1);
		this.updateRegion(MathUtil.log2(duration));
		this.setSum(this.getSum() + duration);
		this.setEndTime(System.currentTimeMillis());
	}
	
	@Override
	public void saveReport() {
		long count = this.getTotalCount();
		long time = this.getEndTime() - this.getStartTime();
		long failCount = this.getFailCount();
		long sum = this.getSum();

		this.setFailPercent(MathUtil.calculateFailPre(count, failCount));
		this.setAvg(MathUtil.calculateAvgDura(count, sum));
		this.setQps(MathUtil.calculateQPS(count, time));
		
		MySQLRepository.getRepository().save(this);
	}
	
	/**
	 * Calculate key for current report.
	 * 
	 * @param calendar
	 * @param span
	 * @return
	 */
	public String getKey(CalendarUtil calendar, Span span) {
		String app = span.getAppName();
		String ipAdress = span.getAppIp();
		String type = span.getSpanType();
		String name = span.getSpanName();
		StringBuilder builder = new StringBuilder();
		builder.append(TimeStampUtil.timestampOfCurrentHour(calendar))
			   .append("-").append(app)
			   .append("-").append(ipAdress)
			   .append("-").append(type)
			   .append("-").append(name);
		return builder.toString();
	}
	
	/**
	 * Calculate key for previous report.
	 * 
	 * @param calendar
	 * @param span
	 * @return
	 */
	public String getPrevKey(CalendarUtil calendar, Span span) {
		String app = span.getAppName();
		String ipAdress = span.getAppIp();
		String type = span.getSpanType();
		String name = span.getSpanName();
		StringBuilder builder = new StringBuilder();
		builder.append(TimeStampUtil.timestampOfPrevHour(calendar))
			   .append("-").append(app)
			   .append("-").append(ipAdress)
			   .append("-").append(type)
			   .append("-").append(name);
		return builder.toString();
	}
	
	/**
	 * Update span duration dist.
	 * 
	 * @param duration
	 */
	private void updateRegion(int duration) {
		int logDura = MathUtil.log2(duration);
		switch (logDura) {
		case 0:
			region_0 += 1;
			break;
		case 1:
			region_1 += 1;
			break;
		case 2:
			region_2 += 1;
			break;
		case 3:
			region_3 += 1;
			break;
		case 4:
			region_4 += 1;
			break;
		case 5:
			region_5 += 1;
			break;
		case 6:
			region_6 += 1;
			break;
		case 7:
			region_7 += 1;
			break;
		case 8:
			region_8 += 1;
			break;
		case 9:
			region_9 += 1;
			break;
		case 10:
			region_10 += 1;
			break;
		case 11:
			region_11 += 1;
			break;
		case 12:
			region_0 += 1;
			break;
		case 13:
			region_13 += 1;
			break;
		case 14:
			region_14 += 1;
			break;
		case 15:
			region_15 += 1;
			break;
		case 16:
			region_16 += 1;
			break;
		default:
			break;
		}
	}
	
	public void setRegion_16(int region_16) {
		this.region_16 = region_16;
	}

	public int getType() {
		return type;
	}

	public void setType(int name) {
		this.type = name;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getFailCount() {
		return failCount;
	}

	public void setFailCount(long failureCount) {
		this.failCount = failureCount;
	}

	public float getFailPercent() {
		return failPercent;
	}

	public void setFailPercent(float failurePrecent) {
		this.failPercent = failurePrecent;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public float getAvg() {
		return avg;
	}

	public void setAvg(float avg) {
		this.avg = avg;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public float getQps() {
		return qps;
	}

	public void setQps(float tps) {
		this.qps = tps;
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

	public long getSum() {
		return sum;
	}

	public void setSum(long sum) {
		this.sum = sum;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String app) {
		this.appName = app;
	}

	public int getAppIp() {
		return appIp;
	}

	public void setAppIp(int ipAdress) {
		this.appIp = ipAdress;
	}

	public int getRegion_0() {
		return region_0;
	}

	public void setRegion_0(int region_0) {
		this.region_0 = region_0;
	}

	public int getRegion_1() {
		return region_1;
	}

	public void setRegion_1(int region_1) {
		this.region_1 = region_1;
	}

	public int getRegion_2() {
		return region_2;
	}

	public void setRegion_2(int region_2) {
		this.region_2 = region_2;
	}

	public int getRegion_3() {
		return region_3;
	}

	public void setRegion_3(int region_3) {
		this.region_3 = region_3;
	}

	public int getRegion_4() {
		return region_4;
	}

	public void setRegion_4(int region_4) {
		this.region_4 = region_4;
	}

	public int getRegion_5() {
		return region_5;
	}

	public void setRegion_5(int region_5) {
		this.region_5 = region_5;
	}

	public int getRegion_6() {
		return region_6;
	}

	public void setRegion_6(int region_6) {
		this.region_6 = region_6;
	}

	public int getRegion_7() {
		return region_7;
	}

	public void setRegion_7(int region_7) {
		this.region_7 = region_7;
	}

	public int getRegion_8() {
		return region_8;
	}

	public void setRegion_8(int region_8) {
		this.region_8 = region_8;
	}

	public int getRegion_9() {
		return region_9;
	}

	public void setRegion_9(int region_9) {
		this.region_9 = region_9;
	}

	public int getRegion_10() {
		return region_10;
	}

	public void setRegion_10(int region_10) {
		this.region_10 = region_10;
	}

	public int getRegion_11() {
		return region_11;
	}

	public void setRegion_11(int region_11) {
		this.region_11 = region_11;
	}

	public int getRegion_12() {
		return region_12;
	}

	public void setRegion_12(int region_12) {
		this.region_12 = region_12;
	}

	public int getRegion_13() {
		return region_13;
	}

	public void setRegion_13(int region_13) {
		this.region_13 = region_13;
	}

	public int getRegion_14() {
		return region_14;
	}

	public void setRegion_14(int region_14) {
		this.region_14 = region_14;
	}

	public int getRegion_15() {
		return region_15;
	}

	public void setRegion_15(int region_15) {
		this.region_15 = region_15;
	}

	public int getRegion_16() {
		return region_16;
	}
	
	@Override
	public String toString() {
		return super.toString() + " TraceReport content [appName=" + appName + ", appIPAd=" + appIp + ", type=" + type + ", name=" + name + ", totalCount=" + totalCount + ", failCount=" + failCount + ", failPrecent="
				+ failPercent + ", min=" + min + ", max=" + max + ", avg=" + avg + ", qps=" + qps + ", sum=" + sum + ", startTime=" + startTime + ", endTime=" + endTime + ", region_0=" + region_0
				+ ", region_1=" + region_1 + ", region_2=" + region_2 + ", region_3=" + region_3 + ", region_4=" + region_4 + ", region_5=" + region_5 + ", region_6=" + region_6 + ", region_7="
				+ region_7 + ", region_8=" + region_8 + ", region_9=" + region_9 + ", region_10=" + region_10 + ", region_11=" + region_11 + ", region_12=" + region_12 + ", region_13=" + region_13
				+ ", region_14=" + region_14 + ", region_15=" + region_15 + ", region_16=" + region_16 + "]";
	}
	
}

package com.vipshop.microscope.mysql.report;

import com.vipshop.microscope.common.util.MathUtil;

public class TraceReport extends AbstraceReport {
	
	private String app;
	private String ipAdress;
	private String type;
	private String name;
	private long totalCount;
	private long failureCount;
	private float failurePrecent;
	private float min;
	private float max;
	private float avg;
	private float tps;
	
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
	
	public void updateRegion(int duration) {
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

	public void setRegion_16(int region_16) {
		this.region_16 = region_16;
	}

	public String getType() {
		return type;
	}

	public void setType(String name) {
		this.type = name;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(long failureCount) {
		this.failureCount = failureCount;
	}

	public float getFailurePrecent() {
		return failurePrecent;
	}

	public void setFailurePrecent(float failurePrecent) {
		this.failurePrecent = failurePrecent;
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

	public float getTps() {
		return tps;
	}

	public void setTps(float tps) {
		this.tps = tps;
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

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getIpAdress() {
		return ipAdress;
	}

	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}

	@Override
	public String toString() {
		return "TraceReport [year=" + year + ", month=" + month + ", week=" + week + ", day=" + day + ", hour=" + hour + ", app=" + app + ", ipAdress=" + ipAdress + ", type=" + type + ", name="
				+ name + ", totalCount=" + totalCount + ", failureCount=" + failureCount + ", failurePrecent=" + failurePrecent + ", min=" + min + ", max=" + max + ", avg=" + avg + ", tps=" + tps
				+ ", sum=" + sum + ", startTime=" + startTime + ", endTime=" + endTime + ", region_0=" + region_0 + ", region_1=" + region_1 + ", region_2=" + region_2 + ", region_3=" + region_3
				+ ", region_4=" + region_4 + ", region_5=" + region_5 + ", region_6=" + region_6 + ", region_7=" + region_7 + ", region_8=" + region_8 + ", region_9=" + region_9 + ", region_10="
				+ region_10 + ", region_11=" + region_11 + ", region_12=" + region_12 + ", region_13=" + region_13 + ", region_14=" + region_14 + ", region_15=" + region_15 + ", region_16="
				+ region_16 + "]";
	}
	
	public String toIPAdress() {
		return ipAdress;
	}
	
	

}

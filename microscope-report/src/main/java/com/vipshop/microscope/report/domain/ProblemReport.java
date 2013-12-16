package com.vipshop.microscope.report.domain;

import com.vipshop.micorscope.framework.util.CalendarUtil;
import com.vipshop.micorscope.framework.util.IPAddressUtil;
import com.vipshop.micorscope.framework.util.TimeStampUtil;
import com.vipshop.microscope.report.factory.MySQLRepository;
import com.vipshop.microscope.thrift.gen.Span;

/**
 * Problem Report.
 * 
 * example:
 * 
 * long-url
 * long-db
 * long-service
 * long-cache
 * long-call
 * error
 * exception
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class ProblemReport extends AbstraceReport {
	
	/**
	 * Rules about to define problems.
	 * 
	 * @author Xu Fei
	 * @version 1.0
	 */
	private static class ProblemRule {
		
		enum Type {
			URL(1) {
				
				int[] zone = {500, 1000, 1500, 2000, 3000, 5000};
				
				@Override
				boolean hasProblem(int time) {
					return time > zone[0];
				}

				@Override
				int timeZone(int time) {
					if (time >= zone[0] && time < zone[1]) {
						return 1;
					} else if (time >= zone[1] && time < zone[2]) {
						return 2;
					} else if (time >= zone[2] && time < zone[3]) {
						return 3;
					} else if (time >= zone[3] && time < zone[4]) {
						return 4;
					} else if (time >= zone[4] && time < zone[5]) {
						return 5;
					} else {
						return 6;
					}
				}

			},
			Service(2) {
				int[] zone = {50, 100, 200, 300, 500, 1000};
				
				@Override
				boolean hasProblem(int time) {
					return time > zone[0];
				}

				@Override
				int timeZone(int time) {
					if (time >= zone[0] && time < zone[1]) {
						return 1;
					} else if (time >= zone[1] && time < zone[2]) {
						return 2;
					} else if (time >= zone[2] && time < zone[3]) {
						return 3;
					} else if (time >= zone[3] && time < zone[4]) {
						return 4;
					} else if (time >= zone[4] && time < zone[5]) {
						return 5;
					} else {
						return 6;
					}
				}
			},
			DB(3){
				int[] zone = {10, 20, 30, 40, 60, 100};
				
				@Override
				boolean hasProblem(int time) {
					return time > zone[0];
				}

				@Override
				int timeZone(int time) {
					if (time >= zone[0] && time < zone[1]) {
						return 1;
					} else if (time >= zone[1] && time < zone[2]) {
						return 2;
					} else if (time >= zone[2] && time < zone[3]) {
						return 3;
					} else if (time >= zone[3] && time < zone[4]) {
						return 4;
					} else if (time >= zone[4] && time < zone[5]) {
						return 5;
					} else {
						return 6;
					}
				}
			},
			Cache(4){
				int[] zone = {10, 20, 30, 40, 60, 100};
				
				@Override
				boolean hasProblem(int time) {
					return time > zone[0];
				}

				@Override
				int timeZone(int time) {
					if (time >= zone[0] && time < zone[1]) {
						return 1;
					} else if (time >= zone[1] && time < zone[2]) {
						return 2;
					} else if (time >= zone[2] && time < zone[3]) {
						return 3;
					} else if (time >= zone[3] && time < zone[4]) {
						return 4;
					} else if (time >= zone[4] && time < zone[5]) {
						return 5;
					} else {
						return 6;
					}
				}
			},
			Call(5){
				int[] zone = {50, 100, 150, 200, 300, 500};
				
				@Override
				boolean hasProblem(int time) {
					return time > zone[0];
				}

				@Override
				int timeZone(int time) {
					if (time >= zone[0] && time < zone[1]) {
						return 1;
					} else if (time >= zone[1] && time < zone[2]) {
						return 2;
					} else if (time >= zone[2] && time < zone[3]) {
						return 3;
					} else if (time >= zone[3] && time < zone[4]) {
						return 4;
					} else if (time >= zone[4] && time < zone[5]) {
						return 5;
					} else {
						return 6;
					}
				}
			};
			
			private int type;
			
			private Type(int type) {
				this.type = type;
			}
			
			public int getType() {
				return type;
			}
			
			abstract int timeZone(int time);
			abstract boolean hasProblem(int time);
		}
		
		private static int getTypeZone(String type) {
			if (type.equals("URL")) {
				return Type.URL.getType();
			} else if (type.equals("DB")) {
				return Type.DB.getType();
			} else if (type.equals("Cache")) {
				return Type.Cache.getType();
			} else if (type.equals("Service")) {
				return Type.Service.getType();
			} else {
				return Type.Call.getType();
			}
		}
		
		private static Type getTypeEnum(String type) {
			if (type.equals("URL")) {
				return Type.URL;
			} else if (type.equals("DB")) {
				return Type.DB;
			} else if (type.equals("Cache")) {
				return Type.Cache;
			} else if (type.equals("Service")) {
				return Type.Service;
			} else {
				return Type.Call;
			}
		}
		
		private static int getTimeZone(Span span) {
			Type typeEnum = getTypeEnum(span.getSpanType());
			return typeEnum.timeZone(span.getDuration());
		}
		
		private static boolean hasProblem(Span span) { 
			String type = span.getSpanType();
			Type typeEnum = getTypeEnum(type);
			return typeEnum.hasProblem(span.getDuration());
		}
	}
	
	public static boolean hasProblme(Span span) {
		return ProblemRule.hasProblem(span);
	}
	
	public static int getTypeZone(Span span) {
		return ProblemRule.getTypeZone(span.getSpanType());
	}
	
	public static int getTimeZone(Span span) {
		return ProblemRule.getTimeZone(span);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.vipshop.microscope.mysql.report.AbstraceReport#updateReportInit(com.vipshop.microscope.common.util.CalendarUtil, com.vipshop.microscope.thrift.Span)
	 */
	@Override
	public void updateReportInit(CalendarUtil calendarUtil, Span span) {
		this.setDateByHour(calendarUtil);
		this.setProType(getTimeZone(span));
		this.setProTime(getTimeZone(span));
		this.setProDesc(span.getSpanName());
		this.setAppName(span.getAppName());
		this.setAppIp(IPAddressUtil.intIPAddress(span.getAppIp()));
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.vipshop.microscope.mysql.report.AbstraceReport#updateReportNext(com.vipshop.microscope.thrift.Span)
	 */
	@Override
	public void updateReportNext(Span span) {
		this.setProCount(this.getProCount() + 1);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.vipshop.microscope.mysql.report.AbstraceReport#updateBeforeSave()
	 */
	@Override
	public void saveReport() {
		MySQLRepository.getRepository().save(this);
	}
	
	public static String getKey(CalendarUtil calendar, Span span) {
		String appName = span.getAppName();
		String appIp = span.getAppIp();
		int typeZone = ProblemReport.getTypeZone(span);
		int timeZone = ProblemReport.getTimeZone(span);
		StringBuilder builder = new StringBuilder();
		builder.append(TimeStampUtil.timestampOfCurrentHour(calendar))
			   .append("-").append(appName)
			   .append("-").append(appIp)
			   .append("-").append(typeZone)
			   .append("-").append(timeZone);
		return builder.toString();
	}
	
	public static String getPrevKey(CalendarUtil calendar, Span span) {
		String appName = span.getAppName();
		String appIp = span.getAppIp();
		int typeZone = ProblemReport.getTypeZone(span);
		int timeZone = ProblemReport.getTimeZone(span);
		StringBuilder builder = new StringBuilder();
		builder.append(TimeStampUtil.timestampOfPrevHour(calendar))
			   .append("-").append(appName)
			   .append("-").append(appIp)
			   .append("-").append(typeZone)
			   .append("-").append(timeZone);
		return builder.toString();
	}
	
	private String appName;
	private int appIp;
	
	private int proType;
	private int proTime;
	private int proCount;
	
	private String proDesc;

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

	@Override
	public String toString() {
		return super.toString() + " ProblemReport content [appName=" + appName + ", appIp=" + appIp + ", problemType=" + proType + ", " +
				                    "timeZone=" + proTime + ", count=" + proCount + ", desc=" + proDesc + ", year=" + year + ", " +
				                    "month=" + month + ", week=" + week + ", day=" + day + ", hour=" + hour + ", minute=" + minute + "]";
	}
	
}

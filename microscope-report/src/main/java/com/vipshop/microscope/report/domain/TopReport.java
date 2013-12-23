package com.vipshop.microscope.report.domain;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.vipshop.micorscope.framework.span.Category;
import com.vipshop.micorscope.framework.thrift.Span;
import com.vipshop.micorscope.framework.util.CalendarUtil;
import com.vipshop.micorscope.framework.util.TimeStampUtil;
import com.vipshop.microscope.report.factory.MySQLFactory;

/**
 * Top 10 slow report
 * 
 * 10 most slow URL 
 * 10 most slow Action 
 * 10 most slow Service 
 * 10 most slow SQL
 * 10 most slow Cache
 * 10 most slow Method
 * 10 most slow System
 * 
 * This report are stated by 1 minute.
 * 
 * @author Xu Fei
 * @version 1.0
 */

public class TopReport extends AbstraceReport {
	
	/**
	 * A sorted map store top 10 data<K,V>
	 * 
	 * key   --> span duration
	 * value --> app name
	 */
	private TreeMap<Integer, String> container = new TreeMap<Integer, String>(new Comparator<Integer>() {
		@Override
		public int compare(Integer o1, Integer o2) {
			if(o1.intValue() > o2.intValue()){
				return -1;
			}
			if(o1.intValue() > o2.intValue()){
				return 1;
			}
			return 0;
		}
	});
	
	private int topType;
	
	/*
	 * Top 10 name and value
	 */
	private String top_1_name;
	private int top_1_data;

	private String top_2_name;
	private int top_2_data;

	private String top_3_name;
	private int top_3_data;
	
	private String top_4_name;
	private int top_4_data;
	
	private String top_5_name;
	private int top_5_data;
	
	private String top_6_name;
	private int top_6_data;

	private String top_7_name;
	private int top_7_data;

	private String top_8_name;
	private int top_8_data;

	private String top_9_name;
	private int top_9_data;

	private String top_10_name;
	private int top_10_data;
	
	@Override
	public void updateReportInit(CalendarUtil calendarUtil, Span span) {
		this.setDateByMinute(calendarUtil);
		this.setTopType(Category.getIntValue(span));
	}
	
	@Override
	public void updateReportNext(Span span) {
		container.put(span.getDuration(), span.getAppName() + "#" + span.getTraceId());
		/**
		 * Remove the min one.
		 */
		if (container.size() > 10 ) {
			container.pollLastEntry();
		}
	}
	
	@Override
	public void saveReport() {
		Set<Map.Entry<Integer,String>> resultSet = container.entrySet();
		int num = 1;
		for (Entry<Integer, String> entry : resultSet) {
			switch (num) {
			case 1:
				setTop_1_data(entry.getKey());
				setTop_1_name(entry.getValue());
				num++;
				break;
			case 2:
				setTop_2_data(entry.getKey());
				setTop_2_name(entry.getValue());
				num++;
				break;
			case 3:
				setTop_3_data(entry.getKey());
				setTop_3_name(entry.getValue());
				num++;
				break;
			case 4:
				setTop_4_data(entry.getKey());
				setTop_4_name(entry.getValue());
				num++;
				break;
			case 5:
				setTop_5_data(entry.getKey());
				setTop_5_name(entry.getValue());
				num++;
				break;
			case 6:
				setTop_6_data(entry.getKey());
				setTop_6_name(entry.getValue());
				num++;
				break;
			case 7:
				setTop_7_data(entry.getKey());
				setTop_7_name(entry.getValue());
				num++;
				break;
			case 8:
				setTop_8_data(entry.getKey());
				setTop_8_name(entry.getValue());
				num++;
				break;
			case 9:
				setTop_9_data(entry.getKey());
				setTop_9_name(entry.getValue());
				num++;
				break;
			case 10:
				setTop_10_data(entry.getKey());
				setTop_10_name(entry.getValue());
				num++;
				break;
				
			default:
				break;
			}
		}
		
		MySQLFactory.TOP.save(this);
	}
	
	public static String getKey(CalendarUtil calendar, Span span) {
		int type = Category.getIntValue(span);
		StringBuilder builder = new StringBuilder();
		builder.append(TimeStampUtil.timestampOfCurrentMinute(calendar))
			   .append("-").append(type);
		return builder.toString();
	}

	public static String getPrevKey(CalendarUtil calendar, Span span) {
		int type = Category.getIntValue(span);
		StringBuilder builder = new StringBuilder();
		builder.append(TimeStampUtil.timestampOfPrevMinute(calendar))
			   .append("-").append(type);
		return builder.toString();
	}

	public int getTopType() {
		return topType;
	}

	public void setTopType(int topType) {
		this.topType = topType;
	}

	public String getTop_1_name() {
		return top_1_name;
	}

	public void setTop_1_name(String top_1_name) {
		this.top_1_name = top_1_name;
	}

	public int getTop_1_data() {
		return top_1_data;
	}

	public void setTop_1_data(int top_1_data) {
		this.top_1_data = top_1_data;
	}

	public String getTop_2_name() {
		return top_2_name;
	}

	public void setTop_2_name(String top_2_name) {
		this.top_2_name = top_2_name;
	}

	public int getTop_2_data() {
		return top_2_data;
	}

	public void setTop_2_data(int top_2_data) {
		this.top_2_data = top_2_data;
	}

	public String getTop_3_name() {
		return top_3_name;
	}

	public void setTop_3_name(String top_3_name) {
		this.top_3_name = top_3_name;
	}

	public int getTop_3_data() {
		return top_3_data;
	}

	public void setTop_3_data(int top_3_data) {
		this.top_3_data = top_3_data;
	}

	public String getTop_4_name() {
		return top_4_name;
	}

	public void setTop_4_name(String top_4_name) {
		this.top_4_name = top_4_name;
	}

	public int getTop_4_data() {
		return top_4_data;
	}

	public void setTop_4_data(int top_4_data) {
		this.top_4_data = top_4_data;
	}

	public String getTop_5_name() {
		return top_5_name;
	}

	public void setTop_5_name(String top_5_name) {
		this.top_5_name = top_5_name;
	}

	public int getTop_5_data() {
		return top_5_data;
	}

	public void setTop_5_data(int top_5_data) {
		this.top_5_data = top_5_data;
	}

	public String getTop_6_name() {
		return top_6_name;
	}

	public void setTop_6_name(String top_6_name) {
		this.top_6_name = top_6_name;
	}

	public int getTop_6_data() {
		return top_6_data;
	}

	public void setTop_6_data(int top_6_data) {
		this.top_6_data = top_6_data;
	}

	public String getTop_7_name() {
		return top_7_name;
	}

	public void setTop_7_name(String top_7_name) {
		this.top_7_name = top_7_name;
	}

	public int getTop_7_data() {
		return top_7_data;
	}

	public void setTop_7_data(int top_7_data) {
		this.top_7_data = top_7_data;
	}

	public String getTop_8_name() {
		return top_8_name;
	}

	public void setTop_8_name(String top_8_name) {
		this.top_8_name = top_8_name;
	}

	public int getTop_8_data() {
		return top_8_data;
	}

	public void setTop_8_data(int top_8_data) {
		this.top_8_data = top_8_data;
	}

	public String getTop_9_name() {
		return top_9_name;
	}

	public void setTop_9_name(String top_9_name) {
		this.top_9_name = top_9_name;
	}

	public int getTop_9_data() {
		return top_9_data;
	}

	public void setTop_9_data(int top_9_data) {
		this.top_9_data = top_9_data;
	}

	public String getTop_10_name() {
		return top_10_name;
	}

	public void setTop_10_name(String top_10_name) {
		this.top_10_name = top_10_name;
	}

	public int getTop_10_data() {
		return top_10_data;
	}

	public void setTop_10_data(int top_10_data) {
		this.top_10_data = top_10_data;
	}

	public TreeMap<Integer, String> getContainer() {
		return container;
	}

	@Override
	public String toString() {
		return super.toString() + " TopReport content [topType=" + topType
							    + ", top_1_name=" + top_1_name + ", top_1_data=" + top_1_data 
							    + ", top_2_name=" + top_2_name + ", top_2_data=" + top_2_data
							    + ", top_3_name=" + top_3_name + ", top_3_data=" + top_3_data 
							    + ", top_4_name=" + top_4_name + ", top_4_data=" + top_4_data 
							    + ", top_5_name=" + top_5_name + ", top_5_data=" + top_5_data 
							    + ", top_6_name=" + top_6_name + ", top_6_data=" + top_6_data 
							    + ", top_7_name=" + top_7_name + ", top_7_data=" + top_7_data 
							    + ", top_8_name=" + top_8_name + ", top_8_data=" + top_8_data 
							    + ", top_9_name=" + top_9_name + ", top_9_data=" + top_9_data 
							    + ", top_10_name=" + top_10_name + ", top_10_data=" + top_10_data
							    + "]";
	}
	
}

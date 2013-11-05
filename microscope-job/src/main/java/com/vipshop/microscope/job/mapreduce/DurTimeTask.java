package com.vipshop.microscope.job.mapreduce;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DurTimeTask {
	public static void main(String[] args) {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring/applicationContext-report.xml");
		MapReduce t = (MapReduce) context.getBean("task_range_1");
		t.action(DurMapper.class, DurReducer.class, TableTrace.Q_DURATION_BYTE);
	}
}
package com.vipshop.microscope.trace.aop;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;

import com.vipshop.microscope.trace.Trace;
import com.vipshop.microscope.trace.TraceFactory;

public class AopTest {

	@Test
	public void test() {
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("/META-INF/spring/applicationContext.xml");
		Student student = (Student) ctx.getBean("student");
		Trace trace = TraceFactory.getTrace();
		
		trace.clientSend("start");
		student.add();
		trace.clientReceive();
		ctx.close();
	}
}

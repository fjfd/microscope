package com.vipshop.microscope.trace;

import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.trace.span.Category;

public class CollectorTest {

	public static void main(String[] args) throws InterruptedException {
		
		Tracer.clientSend("order", Category.ACTION);
		new UserService().login();
		
		Tracer.record("url", "http://zipkin.web:8080/trace/queryCondition?callback=%22alfjdsfadfdk%22");
		Tracer.record("responsecode", "200");
		Tracer.clientReceive();
		
		TimeUnit.SECONDS.sleep(60);
		
		System.out.println(" get all trace info from hbase");
		
		System.exit(0);
	}

}

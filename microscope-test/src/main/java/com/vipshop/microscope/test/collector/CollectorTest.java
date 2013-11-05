package com.vipshop.microscope.test.collector;

import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.test.app.UserService;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.Category;

public class CollectorTest {

	public static void main(String[] args) throws InterruptedException {
		
		Tracer.clientSend("order", Category.ACTION);
		new UserService().login();
		
		Tracer.record("url", "http://zipkin.web:8080/trace/queryCondition?callback=%22alfjdsfadfdk%22");
		Tracer.record("responsecode", "200");
		Tracer.clientReceive();
		
		TimeUnit.SECONDS.sleep(15);
		
	}

}

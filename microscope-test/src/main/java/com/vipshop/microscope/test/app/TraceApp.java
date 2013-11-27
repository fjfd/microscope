package com.vipshop.microscope.test.app;

import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.Category;
import com.vipshop.microscope.trace.span.ResultCode;

public class TraceApp {

	public static void execute() throws InterruptedException {
		Tracer.clientSend("example", Category.METHOD);
		
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
			System.out.println("example method invoke");
		} catch (Exception e) {
			Tracer.setResultCode(ResultCode.EXCEPTION);
		} finally {
			Tracer.clientReceive();
		}
		
		TimeUnit.SECONDS.sleep(10);

	}
}

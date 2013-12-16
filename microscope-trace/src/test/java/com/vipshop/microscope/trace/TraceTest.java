package com.vipshop.microscope.trace;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vipshop.micorscope.framework.span.Category;
import com.vipshop.microscope.trace.span.ResultCode;

public class TraceTest {
	
	@BeforeMethod
	public void testBeforeMethod() {
		Tracer.cleanContext();
	}
	
	@Test
	public void traceUseExample1() throws InterruptedException {
		Tracer.clientSend("example1", Category.METHOD);
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (Exception e) {
			Tracer.setResultCode(ResultCode.EXCEPTION);
		} finally {
			Tracer.clientReceive();
		}
		TimeUnit.SECONDS.sleep(10);
	}
	
	@Test
	public void traceUseExample2() throws InterruptedException {
		Tracer.clientSend("http://www.huohu123.com", Category.URL);
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
			Tracer.clientSend("getNew@newService", Category.SERVICE);
			TimeUnit.MILLISECONDS.sleep(400);
			Tracer.clientSend("get@DB", Category.DAO);
			TimeUnit.MILLISECONDS.sleep(100);
			Tracer.clientReceive();
			Tracer.clientReceive();
			
			Tracer.clientSend("buyNew@buyService", Category.SERVICE);
			TimeUnit.MILLISECONDS.sleep(200);
			Tracer.clientSend("buy@Cache", Category.CACHE);
			TimeUnit.MILLISECONDS.sleep(10);
			Tracer.clientReceive();
			Tracer.clientReceive();
		} catch (Exception e) {
			Tracer.setResultCode(ResultCode.EXCEPTION);
		} finally {
			Tracer.clientReceive();
		}
		TimeUnit.SECONDS.sleep(10);
	}
	
	@Test
	public void traceUseExample3() throws InterruptedException {
		while (true) {
			Tracer.cleanContext();
			Tracer.clientSend("example3", Category.METHOD);
			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (Exception e) {
				Tracer.setResultCode(ResultCode.EXCEPTION);
			} finally {
				Tracer.clientReceive();
			}
		}
	}
	
	@Test
	public void traceUseExample4() throws InterruptedException {
		while (true) {
			Tracer.cleanContext();
			Tracer.clientSend("http://www.huohu123.com", Category.URL);
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
				Tracer.clientSend("getNew@newService", Category.SERVICE);
				TimeUnit.MILLISECONDS.sleep(400);
				Tracer.clientSend("get@DB", Category.DAO);
				TimeUnit.MILLISECONDS.sleep(100);
				Tracer.clientReceive();
				Tracer.clientReceive();
				
				Tracer.clientSend("buyNew@buyService", Category.SERVICE);
				TimeUnit.MILLISECONDS.sleep(200);
				Tracer.clientSend("buy@Cache", Category.CACHE);
				TimeUnit.MILLISECONDS.sleep(10);
				Tracer.clientReceive();
				Tracer.clientReceive();
			} catch (Exception e) {
				Tracer.setResultCode(ResultCode.EXCEPTION);
			} finally {
				Tracer.clientReceive();
			}
		}
	}
	
}

package com.vipshop.microscope.trace;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.vipshop.micorscope.framework.span.Category;

public class TracerTest {
	
	@Test
	public void traceUseExample1() throws InterruptedException {
		Tracer.clientSend("example1", Category.Method);
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (Exception e) {
			Tracer.setResultCode("EXCEPTION");
		} finally {
			Tracer.clientReceive();
		}
		TimeUnit.SECONDS.sleep(1);
	}
	
	@Test
	public void traceUseExample2() throws InterruptedException {
		Tracer.clientSend("http://www.huohu123.com", Category.URL);
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
			Tracer.clientSend("getNew@newService", Category.Service);
			TimeUnit.MILLISECONDS.sleep(400);
			Tracer.clientSend("get@DB", Category.DB);
			TimeUnit.MILLISECONDS.sleep(100);
			Tracer.clientReceive();
			Tracer.clientReceive();
			
			Tracer.clientSend("buyNew@buyService", Category.Service);
			TimeUnit.MILLISECONDS.sleep(200);
			Tracer.clientSend("buy@Cache", Category.Cache);
			TimeUnit.MILLISECONDS.sleep(10);
			Tracer.clientReceive();
			Tracer.clientReceive();
		} catch (Exception e) {
			Tracer.setResultCode("EXCEPTION");
		} finally {
			Tracer.clientReceive();
		}
		TimeUnit.SECONDS.sleep(1);
	}
	
	@Test
	public void traceUseExample3() throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			Tracer.cleanContext();
			Tracer.clientSend("example3", Category.Method);
			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (Exception e) {
				Tracer.setResultCode("EXCEPTION");
			} finally {
				Tracer.clientReceive();
			}
		}
		TimeUnit.SECONDS.sleep(1);
	}
	
	@Test
	public void traceUseExample4() throws InterruptedException {
		for (int i = 0; i < 10; i++)  {
			Tracer.cleanContext();
			Tracer.clientSend("http://www.huohu123.com", Category.URL);
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
				Tracer.clientSend("getNew@newService", Category.Service);
				TimeUnit.MILLISECONDS.sleep(400);
				Tracer.clientSend("get@DB", Category.DB);
				TimeUnit.MILLISECONDS.sleep(100);
				Tracer.clientReceive();
				Tracer.clientReceive();
				
				Tracer.clientSend("buyNew@buyService", Category.Service);
				TimeUnit.MILLISECONDS.sleep(200);
				Tracer.clientSend("buy@Cache", Category.Cache);
				TimeUnit.MILLISECONDS.sleep(10);
				Tracer.clientReceive();
				Tracer.clientReceive();
			} catch (Exception e) {
				Tracer.setResultCode("EXCEPTION");
			} finally {
				Tracer.clientReceive();
			}
		}
		TimeUnit.SECONDS.sleep(1);
	}
	
}

package com.vipshop.microscope.trace.excption;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.vipshop.microscope.common.trace.Category;
import com.vipshop.microscope.trace.Tracer;

public class ExceptionTest {
	
	@Test
	public void testRecordException() throws InterruptedException {
		Tracer.cleanContext();
		Tracer.clientSend("testRecordException", Category.URL);
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
			throw new RuntimeException("testRecordException exception");
		} catch (Exception e) {
			Tracer.record(e);
		} finally {
			Tracer.clientReceive();
		}
		TimeUnit.SECONDS.sleep(1);
	}
	
	@Test
	public void testRecordExceptionWithInfo() throws InterruptedException {
		Tracer.cleanContext();
		Tracer.clientSend("testRecordExceptionWithInfo", Category.URL);
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
			throw new RuntimeException("testRecordException exception");
		} catch (Exception e) {
			Tracer.record(e, "programmer debug info fortestRecordException exception");
		} finally {
			Tracer.clientReceive();
		}
		TimeUnit.SECONDS.sleep(1);

	}

}

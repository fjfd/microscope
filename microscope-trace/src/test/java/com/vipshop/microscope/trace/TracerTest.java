package com.vipshop.microscope.trace;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.vipshop.microscope.common.trace.Category;

public class TracerTest {
	
	@Test
	public void testIsTraceEnable() {
		Assert.assertEquals(true, Tracer.isTraceEnable());
	}
	
	@Test
	public void traceUseExample1() throws InterruptedException {
		Tracer.cleanContext();
		Tracer.clientSend("example-one", Category.Method);
		try {
			Tracer.record("this is a example");
			Tracer.record("queue size", "100");
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (Exception e) {
			Tracer.setResultCode(e);
		} finally {
			Tracer.clientReceive();
		}
		TimeUnit.SECONDS.sleep(1);
	}
	
	@Test
	public void traceUseExampleWithException() throws InterruptedException {
		Tracer.cleanContext();
		Tracer.clientSend("example-with-exception", Category.Method);
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
			throw new RuntimeException();
		} catch (Exception e) {
			Tracer.setResultCode(e);
		} finally {
			Tracer.clientReceive();
		}
		TimeUnit.SECONDS.sleep(10);
	}

	@Test
	public void traceUseExample2() throws InterruptedException {
		Tracer.cleanContext();
		Tracer.clientSend("http://www.huohu.com", Category.URL);
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
			Tracer.setResultCode(e);
		} finally {
			Tracer.clientReceive();
		}
		TimeUnit.SECONDS.sleep(1);
	}
	
	@Test
	public void traceUseExample3() throws InterruptedException {
		Tracer.cleanContext();
		for (int i = 0; i < 10; i++) {
			Tracer.cleanContext();
			Tracer.clientSend("example-three", Category.Method);
			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (Exception e) {
				Tracer.setResultCode(e);
			} finally {
				Tracer.clientReceive();
			}
		}
		TimeUnit.SECONDS.sleep(1);
	}
	
	@Test
	public void traceUseExample4() throws InterruptedException {
		Tracer.cleanContext();
		for (int i = 0; i < 10; i++)  {
			Tracer.cleanContext();
			Tracer.clientSend("http://www.huohu.com", Category.URL);
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
				Tracer.setResultCode(e);
			} finally {
				Tracer.clientReceive();
			}
		}
		TimeUnit.SECONDS.sleep(1);
	}
	
	@Test
	public void traceUserInfoExample() throws InterruptedException {
		Tracer.cleanContext();
		for (int i = 0; i < 10; i++)  {
			Tracer.cleanContext();
			Tracer.clientSend("users/2432424/info/addition/bankcard@resteasy", Category.URL);
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
				Tracer.setResultCode(e);
			} finally {
				Tracer.clientReceive();
			}
		}
		TimeUnit.SECONDS.sleep(1);
	}
	
	@Test
	public void traceWMS20Example() throws InterruptedException {
		Tracer.cleanContext();
		for (int i = 0; i < 10; i++)  {
			Tracer.cleanContext();
			Tracer.clientSend("/vipshop_wms_inb/dwr/call/plaincall/BasItemService.loadByItemCode.dwr;jsessionid=678DD5EFB1DCBFFE6074A67C65C0A5EA-n2@Controller", Category.URL);
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
				Tracer.setResultCode(e);
			} finally {
				Tracer.clientReceive();
			}
		}
		TimeUnit.SECONDS.sleep(1);
	}
	
	@Test
	public void traceUserInfoExample1() throws InterruptedException {
		Tracer.cleanContext();
		for (int i = 0; i < 10; i++)  {
			Tracer.cleanContext();
			Tracer.clientSend("users/2432424/info/addition/callback@resteasy", Category.URL);
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
				Tracer.setResultCode(e);
			} finally {
				Tracer.clientReceive();
			}
		}
		TimeUnit.SECONDS.sleep(1);
	}
	
	@Test
	public void traceDataExample() throws InterruptedException {
		Tracer.cleanContext();
		for (;;)  {
			Tracer.cleanContext();
			Tracer.clientSend("users/2432424/info/addition/callback@resteasy", Category.URL);
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
				Tracer.setResultCode(e);
			} finally {
				Tracer.clientReceive();
			}
		}
	}

	
	@Test
	public void testJVM() throws InterruptedException {
		Tracer.cleanContext();
		for (int i = 0; i < 10; i++)  {
			Tracer.cleanContext();
			Tracer.clientSend("users/2432424/info/addition/jvm@resteasy", Category.Method);
			
			String traceId = Tracer.getTraceId();
			String spanId = Tracer.getSpanId();
//			System.out.println("Trace id ---> " + traceId);
//			System.out.println("Span  id ---> " + spanId);
			
//			TimeUnit.SECONDS.sleep(1);
//			// mock a remote request
			TimeUnit.SECONDS.sleep(1);
			Tracer.clientSend(traceId, spanId, "sendtwo", Category.Method);
			
//			String traceId1 = Tracer.getTraceId();
//			String spanId1 = Tracer.getSpanId();
//			System.out.println("Trace id 1 ---> " + traceId1);
//			System.out.println("Span  id 1 ---> " + spanId1);
			TimeUnit.SECONDS.sleep(1);
			
			Tracer.clientReceive();
			TimeUnit.SECONDS.sleep(1);
			Tracer.clientReceive();
			
		}
		
		TimeUnit.SECONDS.sleep(3);
	}
	
}

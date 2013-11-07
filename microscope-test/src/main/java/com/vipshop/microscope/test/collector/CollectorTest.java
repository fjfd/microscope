package com.vipshop.microscope.test.collector;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.ClientProtocolException;

import com.vipshop.microscope.test.app.UserController;
import com.vipshop.microscope.trace.ResultCode;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.Category;

public class CollectorTest {

	public static void main(String[] args) throws InterruptedException, ClientProtocolException, IOException {
		
		// send data
		Tracer.clientSend("example", Category.METHOD);
		try {
			new UserController().login();
		} catch (Exception e) {
			Tracer.setResultCode(ResultCode.EXCEPTION);
		} finally {
			Tracer.clientReceive();
		}
		TimeUnit.SECONDS.sleep(5);
		
	}

}

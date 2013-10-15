package com.vipshop.microscope.test.collector;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.vipshop.microscope.collector.server.CollectorServer;
import com.vipshop.microscope.hbase.repository.Repositorys;
import com.vipshop.microscope.test.app.UserService;
import com.vipshop.microscope.trace.Trace;
import com.vipshop.microscope.trace.TraceFactory;

public class CollectorTest {

	@Test
	public void testSendAndStore() throws InterruptedException {
		
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(new CollectorServer());
		
		int count = Repositorys.TRAC.findAll().size();
		
		Trace trace = TraceFactory.getTrace();
		trace.clientSend("buy");
		new UserService().login();
		trace.clientReceive();
		
		TimeUnit.SECONDS.sleep(3);
		
		int newcount = Repositorys.TRAC.findAll().size();
		
		Assert.assertEquals(count + 1, newcount);
		
	}

}

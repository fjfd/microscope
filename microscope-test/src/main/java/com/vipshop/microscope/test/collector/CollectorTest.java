package com.vipshop.microscope.test.collector;

import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.hbase.repository.Repositorys;
import com.vipshop.microscope.test.app.UserService;
import com.vipshop.microscope.trace.Trace;
import com.vipshop.microscope.trace.TraceFactory;
import com.vipshop.microscope.trace.span.Category;

public class CollectorTest {

	public static void main(String[] args) throws InterruptedException {
		Trace trace = TraceFactory.getTrace();
		trace.clientSend("test", Category.ACTION);
		new UserService().login();
		trace.clientReceive();
		
		TimeUnit.SECONDS.sleep(60);
		
		System.out.println(" get all trace info from hbase");
		System.out.println(Repositorys.TRAC.findAll());
	}

}

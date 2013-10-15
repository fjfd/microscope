package com.vipshop.microscope.test.app;

import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.trace.Trace;
import com.vipshop.microscope.trace.TraceFactory;

public class UserService {
	
	static UserDao dao = new UserDao();
	
	public void login() {
		Trace trace = TraceFactory.getTrace();

		// time for handler logic
		try {
			TimeUnit.MILLISECONDS.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		trace.clientSend("cache");
		dao.cache();
		trace.clientReceive();
		
		trace.clientSend("dao");
		dao.login();
		trace.clientReceive();
		
	}
}

package com.vipshop.microscope.trace;

import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.trace.Trace;
import com.vipshop.microscope.trace.TraceFactory;
import com.vipshop.microscope.trace.span.Category;

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
		
		trace.clientSend("cache", Category.SERVICE);
		dao.cache();
		trace.clientReceive();
		
		trace.clientSend("dao", Category.SERVICE);
		dao.login();
		trace.clientReceive();
		
	}
}

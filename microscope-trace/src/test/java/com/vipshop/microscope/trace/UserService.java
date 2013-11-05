package com.vipshop.microscope.trace;

import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.trace.span.Category;

public class UserService {
	
	static UserDao dao = new UserDao();
	
	public void login() {
		// time for handler logic
		try {
			TimeUnit.MILLISECONDS.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Tracer.clientSend("cache", Category.SERVICE);
		try {
			dao.cache();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			Tracer.clientReceive();
		}
			
		try {
			Tracer.clientSend("dao", Category.SERVICE);
			dao.login();
			Tracer.clientReceive();
		} catch (Exception e) {
			
		}
		
	}
}

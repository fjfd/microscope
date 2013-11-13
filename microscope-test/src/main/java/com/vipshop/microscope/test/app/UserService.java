package com.vipshop.microscope.test.app;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.Category;

public class UserService {
	
	
	static UserDao dao = new UserDao();
	
	public void login() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(new Random(10).nextInt());
		Tracer.clientSend("cache", Category.CACHE);
		try {
			TimeUnit.MILLISECONDS.sleep(new Random(1000).nextInt());
			dao.cache();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Tracer.clientReceive();
		}
		
		TimeUnit.MILLISECONDS.sleep(new Random(10).nextInt());
		Tracer.clientSend("db", Category.DAO);
		try {
			TimeUnit.MILLISECONDS.sleep(new Random(500).nextInt());
			dao.login();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Tracer.clientReceive();
		}
		
	}
}

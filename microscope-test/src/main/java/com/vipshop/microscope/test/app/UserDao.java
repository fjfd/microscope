package com.vipshop.microscope.test.app;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.span.Category;

public class UserDao {
	
	public void cache() throws InterruptedException {
		Tracer.clientSend("login-cache", Category.CACHE);
		TimeUnit.MILLISECONDS.sleep(new Random(100).nextInt());
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Tracer.clientReceive();
		}
		
	}

	public void login() throws InterruptedException {
		Tracer.clientSend("login-check", Category.DAO);
		TimeUnit.MILLISECONDS.sleep(new Random(100).nextInt());
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Tracer.clientReceive();
		}
	}
	

}

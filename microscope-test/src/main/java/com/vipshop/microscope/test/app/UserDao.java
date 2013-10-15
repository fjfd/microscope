package com.vipshop.microscope.test.app;

import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.trace.Trace;
import com.vipshop.microscope.trace.TraceFactory;

public class UserDao {
	
	Trace trace = TraceFactory.getTrace();
	
	public void login() {
		
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void cache() {
		
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		trace.clientSend("ehcache");
		ehcache();
		trace.clientReceive();
		
		
	}
	
	public void ehcache() {
		
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

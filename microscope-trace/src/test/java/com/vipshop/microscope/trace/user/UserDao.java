package com.vipshop.microscope.trace.user;

import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.trace.Trace;
import com.vipshop.microscope.trace.TraceFactory;
import com.vipshop.microscope.trace.span.Category;

public class UserDao {
	
	public void login() {
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void cache() {
		Trace trace = TraceFactory.getTrace();
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		trace.clientSend("ehcache", Category.DAO);
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

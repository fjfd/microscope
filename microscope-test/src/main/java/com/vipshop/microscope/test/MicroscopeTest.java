package com.vipshop.microscope.test;

import com.vipshop.microscope.test.app.HbaseReinitApp;
import com.vipshop.microscope.test.app.MockDataApp;
import com.vipshop.microscope.test.app.TraceApp;

public class MicroscopeTest {
	
	public static final String TRACE = "trace";
	public static final String MOCKDATA = "mockdata";
	public static final String HBASEREINIT = "hbasereinit";
	
	public static void main(String[] args) throws Exception {
		String app = System.getProperty("app");
		
		if (app.equals(TRACE)) {
			TraceApp.execute();
		}
		
		if (app.equals(MOCKDATA)) {
			MockDataApp.execute();
		}
		
		if (app.equals(HBASEREINIT)) {
			HbaseReinitApp.execute();
		}
	}
}

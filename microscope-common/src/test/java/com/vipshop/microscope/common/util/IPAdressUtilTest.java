package com.vipshop.microscope.common.util;

import java.net.UnknownHostException;

import org.testng.annotations.Test;

public class IPAdressUtilTest {
	
	@Test
	public void testGetIPAdress1() {
		for (int i = 0; i < 100000; i++) {
			IPAddressUtil.IPAddress();
		}
	}
	
	@Test
	public void testGetIPAdress2() throws UnknownHostException {
		for (int i = 0; i < 100000; i++) {
			IPAddressUtil.getLocalHost().getHostAddress();
		}
	}
	
}

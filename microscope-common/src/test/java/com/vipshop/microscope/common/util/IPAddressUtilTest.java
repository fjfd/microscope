
package com.vipshop.microscope.common.util;

import org.testng.Assert;
import org.testng.annotations.Test;

public class IPAddressUtilTest {
	
	@Test
	public void testLocalIP() {
		Assert.assertEquals("10.101.0.216", IPAddressUtil.IPAddress());
	}
}

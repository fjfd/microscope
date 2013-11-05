package com.vipshop.microscope.common.cfg;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ConfigurationTest {
	
	@Test
	public void testRead(){
		Configuration configuration = new Configuration("test.properties");
		Assert.assertEquals("picket", configuration.getString("app_name"));
	}
}

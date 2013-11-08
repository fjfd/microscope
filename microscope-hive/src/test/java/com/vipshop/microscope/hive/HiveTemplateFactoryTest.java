package com.vipshop.microscope.hive;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HiveTemplateFactoryTest {
	
	@Test
	public void testInit() {
		List<String> result = HiveTemplateFactory.HIVE_TEMPLATE.query("show databases");
		Assert.assertTrue(result.contains("default"));
	}
}

package com.vipshop.microscope.common.span;

import org.testng.annotations.Test;

import com.vipshop.microscope.common.trace.Category;

public class CategoryTest {
	
	@Test
	public void test() {
		Category.getIntValue("DB");
	}
}

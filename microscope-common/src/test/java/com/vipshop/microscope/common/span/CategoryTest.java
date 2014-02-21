package com.vipshop.microscope.common.span;

import org.testng.annotations.Test;

import com.vipshop.microscope.common.span.Category;

public class CategoryTest {
	
	@Test
	public void test() {
		Category.getIntValue("DB");
	}
}

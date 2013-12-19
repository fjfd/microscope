package com.vipshop.microscope.framework.span;

import org.testng.annotations.Test;

import com.vipshop.micorscope.framework.span.Category;

public class CategoryTest {
	
	@Test
	public void test() {
		Category.getIntValue("DB");
	}
}

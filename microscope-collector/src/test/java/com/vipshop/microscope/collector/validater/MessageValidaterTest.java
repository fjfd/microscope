package com.vipshop.microscope.collector.validater;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.vipshop.microscope.common.trace.Span;

public class MessageValidaterTest {
	
	@Test
	public void testUserInfoSpan() {
		Span span = new Span();
		span.setAppName("user_info");
		span.setSpanName("/users/1234546/info/addtion/bankcard/status/@resteasy");
		
		span = MessageValidater.getMessageValidater().validateMessage(span);
		
		String traceName = "/users//info/addtion/bankcard/status/@resteasy";
		Assert.assertEquals(span.getSpanName(), traceName);
		
	}
	
	@Test
	public void testWMS20Span() {
		Span span = new Span();
		span.setAppName("wms2.0");
		span.setSpanName("/vipshop_wms_inb/dwr/call/plaincall/BasItemService.loadByItemCode.dwr;jsessionid=678DD5EFB1DCBFFE6074A67C65C0A5EA-n2@Controller");
		
		span = MessageValidater.getMessageValidater().validateMessage(span);
		
		String traceName = "/vipshop_wms_inb/dwr/call/plaincall/BasItemService.loadByItemCode.dwr@Controller";
		Assert.assertEquals(span.getSpanName(), traceName);
		
	}
	
}

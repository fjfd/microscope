package com.vipshop.microscope.validater;

import com.vipshop.microscope.trace.gen.Span;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ValidateEngineTest {

    @Test
    public void testUserInfoSpan() {
        Span span = new Span();
        span.setAppName("user_info");
        span.setSpanName("/users/1234546/info/addtion/bankcard/status/@resteasy");


        String traceName = "/users//info/addtion/bankcard/status/@resteasy";
        Assert.assertEquals(span.getSpanName(), traceName);

    }

    @Test
    public void testWMS20Span() {
        Span span = new Span();
        span.setAppName("wms2.0");
        span.setSpanName("/vipshop_wms_inb/dwr/call/plaincall/BasItemService.loadByItemCode.dwr;jsessionid=678DD5EFB1DCBFFE6074A67C65C0A5EA-n2@Controller");

        String traceName = "/vipshop_wms_inb/dwr/call/plaincall/BasItemService.loadByItemCode.dwr@Controller";
        Assert.assertEquals(span.getSpanName(), traceName);

    }

}

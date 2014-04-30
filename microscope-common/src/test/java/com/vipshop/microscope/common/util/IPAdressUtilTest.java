package com.vipshop.microscope.common.util;

import org.testng.Assert;
import org.testng.annotations.Test;

public class IPAdressUtilTest {

    @Test
    public void testGetIPAdress() {
        for (int i = 0; i < 100000; i++) {
            IPAddressUtil.IPAddress();
        }
    }

    @Test
    public void testGetIntIPAddress() {
        for (int i = 0; i < 100000; i++) {
            IPAddressUtil.intIPAddress();
        }
    }

    @Test
    public void testStringToInt() {
        Assert.assertEquals(2130706433, IPAddressUtil.stringToInt("localhost"));
    }

    @Test
    public void testIntToString() {
        Assert.assertEquals("127.0.0.1", IPAddressUtil.intToString(2130706433));
    }
}

package com.vipshop.microscope.client.codec;

import com.vipshop.microscope.thrift.LogEntry;
import com.vipshop.microscope.thrift.Span;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CodecTest {

    @Test
    public void testSpanToLogEntry(){
        Span span = new Span();
        span.setAppName("test");

        LogEntry logEntry = Codec.toLogEntry(span);

        Span newspan = Codec.toSpan(logEntry.getMessage());

        Assert.assertEquals("test", newspan.getAppName());

    }


}
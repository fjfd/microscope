package com.vipshop.microscope.client.exception;

import com.vipshop.microscope.client.Tracer;
import com.vipshop.microscope.client.trace.SpanCategory;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class ExceptionTest {

    @Test
    public void testRecordException() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Tracer.cleanContext();
            Tracer.clientSend("testRecordException", SpanCategory.URL);
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
                Tracer.clientSend("getNew@newService", SpanCategory.Service);
                TimeUnit.MILLISECONDS.sleep(400);
                Tracer.clientSend("get@DB", SpanCategory.DB);
                TimeUnit.MILLISECONDS.sleep(100);
                Tracer.clientReceive();
                Tracer.clientReceive();
                Tracer.clientSend("buyNew@buyService", SpanCategory.Service);
                TimeUnit.MILLISECONDS.sleep(200);
                Tracer.clientSend("buy@Cache", SpanCategory.Cache);
                TimeUnit.MILLISECONDS.sleep(10);
                Tracer.clientReceive();
                Tracer.clientReceive();
                throw new RuntimeException("testRecordException exception");
            } catch (Exception e) {
                Tracer.recordException(e);
            } finally {
                Tracer.clientReceive();
            }
        }
        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    public void testRecordExceptionWithInfo() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Tracer.cleanContext();
            Tracer.clientSend("testRecordExceptionWithInfo", SpanCategory.URL);
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
                Tracer.clientSend("getNew@newService", SpanCategory.Service);
                TimeUnit.MILLISECONDS.sleep(400);
                Tracer.clientSend("get@DB", SpanCategory.DB);
                TimeUnit.MILLISECONDS.sleep(100);
                Tracer.clientReceive();
                Tracer.clientReceive();

                Tracer.clientSend("buyNew@buyService", SpanCategory.Service);
                TimeUnit.MILLISECONDS.sleep(200);
                Tracer.clientSend("buy@Cache", SpanCategory.Cache);
                TimeUnit.MILLISECONDS.sleep(10);
                Tracer.clientReceive();
                Tracer.clientReceive();
                throw new RuntimeException("testRecordException exception");
            } catch (Exception e) {
                Tracer.recordException(e, "programmer debug info fortestRecordException exception");
            } finally {
                Tracer.clientReceive();
            }
        }
        TimeUnit.SECONDS.sleep(3);
    }

}

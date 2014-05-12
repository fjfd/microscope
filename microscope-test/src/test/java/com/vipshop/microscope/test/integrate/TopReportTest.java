package com.vipshop.microscope.test.integrate;

import com.vipshop.microscope.client.Tracer;
import com.vipshop.microscope.client.trace.SpanCategory;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TopReportTest {

    @Test
    public void testTopReport() {
        while (true) {
            Tracer.cleanContext();
            Tracer.clientSend("http://www.huohu.com", SpanCategory.URL);
            try {
                TimeUnit.MILLISECONDS.sleep(new Random(1000).nextInt(1000));
                Tracer.clientSend("getNew@newService", SpanCategory.Service);
                TimeUnit.MILLISECONDS.sleep(new Random(1000).nextInt(1000));
                Tracer.clientSend("get@DB", SpanCategory.DB);
                TimeUnit.MILLISECONDS.sleep(new Random(1000).nextInt(1000));
                Tracer.clientReceive();
                Tracer.clientReceive();

                Tracer.clientSend("buyNew@buyService", SpanCategory.Service);
                TimeUnit.MILLISECONDS.sleep(new Random(1000).nextInt(1000));
                Tracer.clientSend("buy@Cache", SpanCategory.Cache);
                TimeUnit.MILLISECONDS.sleep(new Random(1000).nextInt(1000));
                Tracer.clientReceive();
                Tracer.clientReceive();
            } catch (Exception e) {
                Tracer.setResultCode(e);
            } finally {
                Tracer.clientReceive();
            }
        }
    }


}

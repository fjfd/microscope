package com.vipshop.microscope.query.action;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.vipshop.microscope.common.util.HttpClientUtil;
import com.vipshop.microscope.query.QueryServer;

public class ReportControllerTest {
	
	QueryServer webServer = new QueryServer(8080);
	
	@BeforeClass
	public void setUpBeforeClass() throws Exception {
//		webServer.start();
	}
	
	@AfterClass
	public void tearDownAfterClass() throws Exception {
//		webServer.stop();
	}

    @Test
    public void testMetricsIndex() throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("http://localhost:8080/report/metricsIndex")
               .append("?")
               .append("callback=jQuery11020021555292898187584");

        String result = HttpClientUtil.request(builder.toString());

        System.out.print(result);
    }

    @Test
    public void testMetrics() throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("http://localhost:8080/report/metrics")
               .append("?")
               .append("metrics=jvm_monitor.FreeSwapSpace").append("&")
               .append("APP=trace").append("&")
               .append("IP=10.101.3.111").append("&")
               .append("startTime=").append(System.currentTimeMillis()).append("&")
               .append("endTime=").append(System.currentTimeMillis() + 1000 * 60 * 60 * 1).append("&")
               .append("callback=jQuery11020021555292898187584");

        String result = HttpClientUtil.request(builder.toString());

        System.out.print(result);

    }

    @Test
    public void testTopReport() throws ClientProtocolException, IOException, InterruptedException {
        StringBuilder builder = new StringBuilder();
        builder.append("http://localhost:8080/report/topReport")
               .append("?")
               .append("callback=jQuery11020021555292898187584");
        String result = HttpClientUtil.request(builder.toString());

        System.out.print(result);
    }

}

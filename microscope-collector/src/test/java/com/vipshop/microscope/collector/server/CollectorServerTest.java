package com.vipshop.microscope.collector.server;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.thrift.TException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.vipshop.micorscope.framework.span.Codec;
import com.vipshop.micorscope.framework.thrift.LogEntry;
import com.vipshop.micorscope.framework.thrift.Span;
import com.vipshop.micorscope.framework.thrift.ThriftCategory;
import com.vipshop.micorscope.framework.thrift.ThriftClient;
import com.vipshop.micorscope.framework.util.SpanMockUtil;
import com.vipshop.microscope.storage.domain.TraceTable;
import com.vipshop.microscope.storage.hbase.HbaseRepository;

public class CollectorServerTest {

	@BeforeClass
	public void setUp() {
		new Thread(new CollectorServer()).start();
		HbaseRepository.reinit();
	}

	@Test
	public void testCollectorServer() throws TException, InterruptedException {
		LogEntry logEntry = new Codec().encodeToLogEntry(SpanMockUtil.mockSpan());

		new ThriftClient("localhost", 9410, 3000, ThriftCategory.THREAD_SELECTOR).send(Arrays.asList(logEntry));

		TimeUnit.SECONDS.sleep(1);

		List<Map<String, Object>> apps = HbaseRepository.findAll();
		for (Map<String, Object> map : apps) {
			Set<Entry<String, Object>> entry = map.entrySet();
			int size = 0;
			for (Entry<String, Object> entry2 : entry) {
				size++;
				if (size == 1) {
					Assert.assertEquals("app", entry2.getKey());
				}
				if (size == 2) {
					Assert.assertEquals("trace", entry2.getKey());
				}
			}
		}

		List<Span> spans = HbaseRepository.findSpanByTraceId("8053381312019065847");
		for (Span tmpspan : spans) {
			Assert.assertEquals("localhost", tmpspan.getAppIp());
		}

		List<TraceTable> tables = HbaseRepository.findByTraceId("8053381312019065847");
		for (TraceTable traceTable : tables) {
			Assert.assertEquals("appname", traceTable.getAppName());
		}
		
	}
}

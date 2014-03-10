package com.vipshop.microscope.collector.server;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.thrift.TException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.vipshop.microscope.common.logentry.Codec;
import com.vipshop.microscope.common.logentry.LogEntry;
import com.vipshop.microscope.common.thrift.ThriftCategory;
import com.vipshop.microscope.common.thrift.ThriftClient;
import com.vipshop.microscope.common.trace.Span;
import com.vipshop.microscope.common.trace.SpanMockUtil;
import com.vipshop.microscope.storage.QueryRepository;
import com.vipshop.microscope.storage.StorageRepository;

public class CollectorServerTest {

	StorageRepository storageRepository = StorageRepository.getStorageRepository();
	
	QueryRepository queryRepository = QueryRepository.getQueryRepository();
	
	@BeforeClass
	public void setUp() {
		storageRepository.reInitalizeHbaseTable();
	}

	@Test
	public void testCollectorServer() throws TException, InterruptedException {
		LogEntry logEntry = Codec.encodeToLogEntry(SpanMockUtil.mockSpan());

		new ThriftClient("localhost", 9410, 3000, ThriftCategory.THREAD_SELECTOR).send(Arrays.asList(logEntry));

		TimeUnit.SECONDS.sleep(1);

		List<Map<String, Object>> apps = queryRepository.findAppIPTrace();
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

		List<Span> spans = queryRepository.find("8053381312019065847");
		for (Span tmpspan : spans) {
			Assert.assertEquals("localhost", tmpspan.getAppIp());
		}

	}
	

}

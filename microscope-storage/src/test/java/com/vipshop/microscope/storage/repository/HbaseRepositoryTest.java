package com.vipshop.microscope.storage.repository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.vipshop.microscope.common.thrift.Span;
import com.vipshop.microscope.common.util.SpanMockUtil;
import com.vipshop.microscope.storage.domain.AppTable;
import com.vipshop.microscope.storage.domain.TraceTable;
import com.vipshop.microscope.storage.hbase.HbaseRepository;

public class HbaseRepositoryTest {

	public static final Logger logger = LoggerFactory.getLogger(HbaseRepositoryTest.class);
	
	@Test(priority = 1)
	public void reinit() {
		HbaseRepository.drop();
		HbaseRepository.init();
	}
	
	@Test(priority = 2)
	public void save() {
		Span span = SpanMockUtil.mockSpan();
		HbaseRepository.save(AppTable.build(span));
		HbaseRepository.save(TraceTable.build(span));
		HbaseRepository.save(span);
		
		List<Map<String, Object>> apps = HbaseRepository.findAppIPTrace();
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
		
		List<Span> spans = HbaseRepository.find("8053381312019065847");
		for (Span tmpspan : spans) {
			Assert.assertEquals("localhost", tmpspan.getAppIp());
		}
		
	}
	
	@Test
	public void testFindByScan1() {
		Scan scan = new Scan();
		int limit = 10;
		PageFilter pageFilter = new PageFilter(limit);
		scan.setFilter(pageFilter);
		
		HbaseRepository.find(scan);
	}
	
	@Test
	public void testFindByScan2() throws IOException {
		Scan scan = new Scan();
		int limit = 20;
		PageFilter pageFilter = new PageFilter(limit);
		byte[] CF = Bytes.toBytes("cf");
		byte[] CF_APP_NAME = Bytes.toBytes("app_name");
		SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter(CF, CF_APP_NAME, CompareFilter.CompareOp.EQUAL, Bytes.toBytes("trace"));
		scan.setFilter(pageFilter);
		scan.setFilter(singleColumnValueFilter);
		scan.setTimeRange(System.currentTimeMillis() - 60 * 1000, System.currentTimeMillis());
		HbaseRepository.find(scan);
	}
	
	@Test
	public void testFindByScan3() throws IOException {
		Scan scan = new Scan();
		int limit = 20;
		PageFilter pageFilter = new PageFilter(limit);
		scan.setFilter(pageFilter);
//		scan.setFilter(traceFilter);
//		scan.setTimeRange(System.currentTimeMillis() - 60 * 1000, System.currentTimeMillis());
		long startKey = Long.MAX_VALUE - System.currentTimeMillis();
		long endKey = Long.MAX_VALUE - (System.currentTimeMillis() - 60 * 60 * 1000);
		scan.setStartRow(Bytes.toBytes("trace-http://www.huohu123.com-" + startKey));
		scan.setStopRow(Bytes.toBytes("trace-http://www.huohu123.com-" + endKey));
		System.out.println(HbaseRepository.find(scan));
		
	}
	
	@Test
	public void testFindByScan4() throws IOException {
		Scan scan = new Scan();
		int limit = 20;
		PageFilter pageFilter = new PageFilter(limit);
		RowFilter traceFilter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator("example3-trace"));
		scan.setFilter(pageFilter);
		scan.setFilter(traceFilter);
		String startKey = String.valueOf(Long.MAX_VALUE - 1393404390834l);
		String endKey = String.valueOf(Long.MAX_VALUE - 1393404392544l);
		scan.setStartRow(Bytes.toBytes(endKey));
		scan.setStopRow(Bytes.toBytes(startKey));
		
		System.out.println(HbaseRepository.find(scan));
		
	}

	
}

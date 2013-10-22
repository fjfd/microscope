package com.vipshop.microscope.hbase.repository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.vipshop.microscope.hbase.domain.TraceTable;
import com.vipshop.microscope.hbase.repository.Repositorys;

public class TraceTableRepositoryTest {

	private static final Log log = LogFactory.getLog(TraceTableRepositoryTest.class);
	
	@BeforeClass
	public void setUp() {
		Repositorys.TRACE.initialize();
	}
	
	@AfterClass
	public void tearDown() {
//		Repositorys.TRAC_REPOSITORY.drop();
	}
	
	@Test
	public void testFindAll() throws IOException {
		log.info("HBase Application Running");
		
		TraceTable trace1 = new TraceTable("xufei-1", "andy19881006@gmail.com-1");
		TraceTable trace2 = new TraceTable("xufei-2", "andy19881006@gmail.com-2");
		TraceTable trace3 = new TraceTable("xufei-3", "andy19881006@gmail.com-3");

		Repositorys.TRACE.save(Arrays.asList(trace1, trace2, trace3));
		Repositorys.TRACE.delete(trace1);
		List<TraceTable> traces = Repositorys.TRACE.findAll();

		Assert.assertEquals(2, traces.size());
	}
	
	@Test
	public void testFindScan() throws IOException {
		log.info("HBase Application Running");
		
		TraceTable trace1 = new TraceTable("xufei-1", "andy19881006@gmail.com-1");
		TraceTable trace2 = new TraceTable("xufei-2", "andy19881006@gmail.com-2");
		TraceTable trace3 = new TraceTable("xufei-3", "andy19881006@gmail.com-3");
		TraceTable trace4 = new TraceTable("xufei-4", "andy19881006@gmail.com-4");
		TraceTable trace5 = new TraceTable("xufei-5", "andy19881006@gmail.com-5");
		TraceTable trace6 = new TraceTable("xufei-6", "andy19881006@gmail.com-6");

		Repositorys.TRACE.save(Arrays.asList(trace1, trace2, trace3, trace4, trace5, trace6));

		Scan scan = new Scan(Bytes.toBytes("xufei-1"), Bytes.toBytes("xufei-5"));
		List<TraceTable> traces = Repositorys.TRACE.findWithScan(scan);
		Assert.assertEquals(4, traces.size());
		
		scan = new Scan(Bytes.toBytes("xufei-1"));
		traces = Repositorys.TRACE.findWithScan(scan);
		Assert.assertEquals(6, traces.size());

	}
}

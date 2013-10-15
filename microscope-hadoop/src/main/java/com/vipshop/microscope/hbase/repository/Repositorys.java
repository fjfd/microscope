package com.vipshop.microscope.hbase.repository;

import java.util.NavigableMap;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Repositorys {
	
	public static AppRepository APP_INDEX;
	public static TraceRepository TRAC;
	public static TraceIndexRepository TRAC_INDEX;
	public static SpanRepository SPAN;
	public static TraceSpanRepository TRACE_SPAN;
	
	static {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring/applicationContext-hbase.xml", Repositorys.class);
		
		APP_INDEX = context.getBean(AppRepository.class);
		TRAC = context.getBean(TraceRepository.class);
		TRAC_INDEX = context.getBean(TraceIndexRepository.class);
		SPAN = context.getBean(SpanRepository.class);
		TRACE_SPAN = context.getBean(TraceSpanRepository.class);
		
		synchronized (Repositorys.class) {
			APP_INDEX.initialize();
			TRAC.initialize();
			TRAC_INDEX.initialize();
			SPAN.initialize();
			TRACE_SPAN.initialize();
		}
		
		context.close();
	}
	
	public static void drop() {
		APP_INDEX.drop();
		TRAC.drop();
		TRAC_INDEX.drop();
		SPAN.drop();
		TRACE_SPAN.drop();
	}
	
	public static String[] getColumnsInColumnFamily(Result r, String ColumnFamily) {
		NavigableMap<byte[], byte[]> familyMap = r.getFamilyMap(Bytes.toBytes(ColumnFamily));
		String[] Quantifers = new String[familyMap.size()];

		int counter = 0;
		for (byte[] bQunitifer : familyMap.keySet()) {
			Quantifers[counter++] = Bytes.toString(bQunitifer);

		}
		return Quantifers;
	}

	
}

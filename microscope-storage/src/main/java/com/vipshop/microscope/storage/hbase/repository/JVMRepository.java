package com.vipshop.microscope.storage.hbase.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.SerializationUtils;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import com.vipshop.microscope.common.metrics.MetricsCategory;
import com.vipshop.microscope.storage.hbase.table.JVMReportTable;

@Repository
public class JVMRepository extends AbstraceRepository {

	public void initialize() {
		super.initialize(JVMReportTable.INDEX_TABLE_NAME, new String[]{JVMReportTable.CF_APP, JVMReportTable.CF_IP});
		super.initialize(JVMReportTable.TABLE_NAME, new String[]{JVMReportTable.CF_JVM});
	}

	public void drop() {
		super.drop(JVMReportTable.TABLE_NAME);
		super.drop(JVMReportTable.INDEX_TABLE_NAME);
	}
	
	private String rowKey(Map<String, Object> map) {
		return map.get("app") + "-" +
	           map.get("ip") + "-" +
			   (Long.MAX_VALUE - Long.valueOf(map.get("date").toString()));
//	           UUID.randomUUID().getLeastSignificantBits();
	}

	public void save(final Map<String, Object> jvm) {
		final HashMap<String, Object> overview = new HashMap<String, Object>();
		final HashMap<String, Object> monitor = new HashMap<String, Object>();
		final HashMap<String, Object> thread = new HashMap<String, Object>();
		final HashMap<String, Object> memory = new HashMap<String, Object>();
		final HashMap<String, Object> gc = new HashMap<String, Object>();
		
		for (Entry<String, Object> entry : jvm.entrySet()) {
			if (entry.getKey().startsWith(MetricsCategory.JVM_Overview)) {
				overview.put(entry.getKey(), entry.getValue());
			}
			if (entry.getKey().startsWith(MetricsCategory.JVM_Monitor)) {
				monitor.put(entry.getKey(), entry.getValue());
			}
			if (entry.getKey().startsWith(MetricsCategory.JVM_Thread)) {
				thread.put(entry.getKey(), entry.getValue());
			}
			if (entry.getKey().startsWith(MetricsCategory.JVM_Memory)) {
				memory.put(entry.getKey(), entry.getValue());
			}
			if (entry.getKey().startsWith(MetricsCategory.JVM_GC)) {
				gc.put(entry.getKey(), entry.getValue());
			}
		}
		
		overview.put("date", jvm.get("date"));
		monitor.put("date", jvm.get("date"));
		thread.put("date", jvm.get("date"));
		memory.put("date", jvm.get("date"));
		gc.put("date", jvm.get("date"));
		
		hbaseTemplate.execute(JVMReportTable.INDEX_TABLE_NAME, new TableCallback<Map<String, Object>>() {
			@Override
			public Map<String, Object> doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes((String)jvm.get("app")));
				p.add(JVMReportTable.BYTE_CF_APP, Bytes.toBytes((String)jvm.get("app")), Bytes.toBytes((String)jvm.get("app")));
				p.add(JVMReportTable.BYTE_CF_IP, Bytes.toBytes((String)jvm.get("ip")), Bytes.toBytes((String)jvm.get("ip")));
				table.put(p);
				return jvm;
			}
		});
		
		hbaseTemplate.execute(JVMReportTable.TABLE_NAME, new TableCallback<Map<String, Object>>() {
			@Override
			public Map<String, Object> doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(rowKey(jvm)));
				p.add(JVMReportTable.BYTE_CF_JVM, JVMReportTable.BYTE_C_OVERVIEW, SerializationUtils.serialize((Serializable) overview));
				p.add(JVMReportTable.BYTE_CF_JVM, JVMReportTable.BYTE_C_MONITOR, SerializationUtils.serialize((Serializable) monitor));
				p.add(JVMReportTable.BYTE_CF_JVM, JVMReportTable.BYTE_C_THREAD, SerializationUtils.serialize((Serializable) thread));
				p.add(JVMReportTable.BYTE_CF_JVM, JVMReportTable.BYTE_C_MEMORY, SerializationUtils.serialize((Serializable) memory));
				p.add(JVMReportTable.BYTE_CF_JVM, JVMReportTable.BYTE_C_GC, SerializationUtils.serialize((Serializable) gc));
				table.put(p);
				return jvm;
			}
		});
	}
	
	/**
	 * Returns appName, IPAdress in follow format:
	 * 
	 * [
	 * "app"   :   app name,
	 * "ip"    :   ["ip adress 1", "ip adress 2", ...], 
	 * ]
	 * 
	 * @return
	 */
	public List<Map<String, Object>> findAppIP() {
		final List<String> appList = new ArrayList<String>();
		final List<Map<String, Object>> appIPList = new ArrayList<Map<String,Object>>();
		
		hbaseTemplate.find(JVMReportTable.INDEX_TABLE_NAME, JVMReportTable.CF_APP, new RowMapper<List<String>>() {
			@Override
			public List<String> mapRow(Result result, int rowNum) throws Exception {
				String[] appQunitifer = getColumnsInColumnFamily(result, JVMReportTable.CF_APP);
				for (int i = 0; i < appQunitifer.length; i++) {
					appList.add(appQunitifer[i]);
				}
				return appList;
			}
		});
		
		for (Iterator<String> iterator = appList.iterator(); iterator.hasNext();) {
			final String row = iterator.next();
			
			hbaseTemplate.get(JVMReportTable.INDEX_TABLE_NAME, row, new RowMapper<Map<String, Object>>() {
				@Override
				public Map<String, Object> mapRow(Result result, int rowNum) throws Exception {
					Map<String, Object> appTrace = new HashMap<String, Object>();
					String[] ipQunitifer = getColumnsInColumnFamily(result, JVMReportTable.CF_IP);
					appTrace.put("app", row);
					appTrace.put("ip", Arrays.asList(ipQunitifer));
					appIPList.add(appTrace);
					return appTrace;
				}
			});
		}
		
		return appIPList;
	}
	
	/**
	 * Get data in 1 hour when load html.
	 * 
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> findInitLoad(Map<String, String> query) {
		Scan scan = new Scan();
		
//		/**
//		 * limit the size of result in [10, 1000]
//		 */
//		long limit = Long.valueOf(query.get("limit"));
//		
//		if (limit > 1000) {
//			limit = 1000;
//		}
//		
//		if (limit < 1) {
//			limit = 10;
//		}
		
//		PageFilter pageFilter = new PageFilter(1);
//		scan.setFilter(pageFilter);
		
		/**
		 * Query by rowKey : appName-ipAddress-timestamp
		 */
		String appName = query.get("appName");
		String ipAddress = query.get("ipAddress");
		
		long startTime = System.currentTimeMillis() - 10 * 60 * 1000;
		long endTime = System.currentTimeMillis();

//		long startTime = Long.valueOf(query.get("startTime"));
//		long endTime = Long.valueOf(query.get("endTime"));
		
		String startKey = appName + "-" + ipAddress + "-" + (Long.MAX_VALUE - endTime);
		String endKey = appName + "-" + ipAddress + "-" + (Long.MAX_VALUE - startTime);
		
		scan.setStartRow(Bytes.toBytes(startKey));
		scan.setStopRow(Bytes.toBytes(endKey));
		
		List<Map<String, Object>> JVMMetrics = hbaseTemplate.find(JVMReportTable.TABLE_NAME, scan, new RowMapper<Map<String, Object>>() {
			@SuppressWarnings("unchecked")
			@Override
			public Map<String, Object> mapRow(Result result, int rowNum) throws Exception {
				Map<String, Object> jvmMap = new HashMap<String, Object>();
				jvmMap.put("overview", (Map<String, Object>) SerializationUtils.deserialize(result.getValue(JVMReportTable.BYTE_CF_JVM, JVMReportTable.BYTE_C_OVERVIEW)));
				jvmMap.put("monitor", (Map<String, Object>) SerializationUtils.deserialize(result.getValue(JVMReportTable.BYTE_CF_JVM, JVMReportTable.BYTE_C_MONITOR)));
				jvmMap.put("thread", (Map<String, Object>) SerializationUtils.deserialize(result.getValue(JVMReportTable.BYTE_CF_JVM, JVMReportTable.BYTE_C_THREAD)));
				jvmMap.put("gc", (Map<String, Object>) SerializationUtils.deserialize(result.getValue(JVMReportTable.BYTE_CF_JVM, JVMReportTable.BYTE_C_GC)));
				jvmMap.put("memory", (Map<String, Object>) SerializationUtils.deserialize(result.getValue(JVMReportTable.BYTE_CF_JVM, JVMReportTable.BYTE_C_MEMORY)));
				return jvmMap;
			}
		});
		
		return JVMMetrics;
	}
	
	public List<Map<String, Object>> find(Map<String, String> query) {
		Scan scan = new Scan();
		
		PageFilter pageFilter = new PageFilter(1);
		scan.setFilter(pageFilter);
		
		/**
		 * Query by rowKey : appName-ipAddress-timestamp
		 */
		String appName = query.get("appName");
		String ipAddress = query.get("ipAddress");
		
		long startTime = System.currentTimeMillis() - 60 * 1000;
		long endTime = System.currentTimeMillis();

//		long startTime = Long.valueOf(query.get("startTime"));
//		long endTime = Long.valueOf(query.get("endTime"));
		
		String startKey = appName + "-" + ipAddress + "-" + (Long.MAX_VALUE - endTime);
		String endKey = appName + "-" + ipAddress + "-" + (Long.MAX_VALUE - startTime);
		
		scan.setStartRow(Bytes.toBytes(startKey));
		scan.setStopRow(Bytes.toBytes(endKey));
		
		List<Map<String, Object>> JVMMetrics = hbaseTemplate.find(JVMReportTable.TABLE_NAME, scan, new RowMapper<Map<String, Object>>() {
			@SuppressWarnings("unchecked")
			@Override
			public Map<String, Object> mapRow(Result result, int rowNum) throws Exception {
				Map<String, Object> jvmMap = new HashMap<String, Object>();
				jvmMap.put("overview", (Map<String, Object>) SerializationUtils.deserialize(result.getValue(JVMReportTable.BYTE_CF_JVM, JVMReportTable.BYTE_C_OVERVIEW)));
				jvmMap.put("monitor", (Map<String, Object>) SerializationUtils.deserialize(result.getValue(JVMReportTable.BYTE_CF_JVM, JVMReportTable.BYTE_C_MONITOR)));
				jvmMap.put("thread", (Map<String, Object>) SerializationUtils.deserialize(result.getValue(JVMReportTable.BYTE_CF_JVM, JVMReportTable.BYTE_C_THREAD)));
				jvmMap.put("gc", (Map<String, Object>) SerializationUtils.deserialize(result.getValue(JVMReportTable.BYTE_CF_JVM, JVMReportTable.BYTE_C_GC)));
				jvmMap.put("memory", (Map<String, Object>) SerializationUtils.deserialize(result.getValue(JVMReportTable.BYTE_CF_JVM, JVMReportTable.BYTE_C_MEMORY)));
				return jvmMap;
			}
		});
		
		return JVMMetrics;
	}
	
	public List<Map<String, Object>> findByTime(Map<String, String> query) {
		Scan scan = new Scan();
		
		PageFilter pageFilter = new PageFilter(1);
		scan.setFilter(pageFilter);
		
		/**
		 * Query by rowKey : appName-ipAddress-timestamp
		 */
		String appName = query.get("appName");
		String ipAddress = query.get("ipAddress");
		
//		long startTime = System.currentTimeMillis() - 60 * 1000;
//		long endTime = System.currentTimeMillis();

		long startTime = Long.valueOf(query.get("startTime"));
		long endTime = Long.valueOf(query.get("endTime"));
		
		String startKey = appName + "-" + ipAddress + "-" + (Long.MAX_VALUE - endTime);
		String endKey = appName + "-" + ipAddress + "-" + (Long.MAX_VALUE - startTime);
		
		scan.setStartRow(Bytes.toBytes(startKey));
		scan.setStopRow(Bytes.toBytes(endKey));
		
		List<Map<String, Object>> JVMMetrics = hbaseTemplate.find(JVMReportTable.TABLE_NAME, scan, new RowMapper<Map<String, Object>>() {
			@SuppressWarnings("unchecked")
			@Override
			public Map<String, Object> mapRow(Result result, int rowNum) throws Exception {
				Map<String, Object> jvmMap = new HashMap<String, Object>();
				jvmMap.put("overview", (Map<String, Object>) SerializationUtils.deserialize(result.getValue(JVMReportTable.BYTE_CF_JVM, JVMReportTable.BYTE_C_OVERVIEW)));
				jvmMap.put("monitor", (Map<String, Object>) SerializationUtils.deserialize(result.getValue(JVMReportTable.BYTE_CF_JVM, JVMReportTable.BYTE_C_MONITOR)));
				jvmMap.put("thread", (Map<String, Object>) SerializationUtils.deserialize(result.getValue(JVMReportTable.BYTE_CF_JVM, JVMReportTable.BYTE_C_THREAD)));
				jvmMap.put("gc", (Map<String, Object>) SerializationUtils.deserialize(result.getValue(JVMReportTable.BYTE_CF_JVM, JVMReportTable.BYTE_C_GC)));
				jvmMap.put("memory", (Map<String, Object>) SerializationUtils.deserialize(result.getValue(JVMReportTable.BYTE_CF_JVM, JVMReportTable.BYTE_C_MEMORY)));
				return jvmMap;
			}
		});
		
		return JVMMetrics;
	}
}

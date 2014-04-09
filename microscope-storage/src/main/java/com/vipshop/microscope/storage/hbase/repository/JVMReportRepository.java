package com.vipshop.microscope.storage.hbase.repository;

import java.io.Serializable;
import java.util.HashMap;
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

import com.vipshop.microscope.common.logentry.Constants;
import com.vipshop.microscope.storage.hbase.table.JVMReportTable;

@Repository
public class JVMReportRepository extends AbstraceRepository {

	public void initialize() {
		super.initialize(JVMReportTable.TABLE_NAME, new String[]{JVMReportTable.CF_JVM});
	}

	public void drop() {
		super.drop(JVMReportTable.TABLE_NAME);
	}
	
	public void save(final Map<String, Object> jvm) {
		final HashMap<String, Object> overview = new HashMap<String, Object>();
		final HashMap<String, Object> monitor = new HashMap<String, Object>();
		final HashMap<String, Object> thread = new HashMap<String, Object>();
		final HashMap<String, Object> memory = new HashMap<String, Object>();
		final HashMap<String, Object> gc = new HashMap<String, Object>();
		
		for (Entry<String, Object> entry : jvm.entrySet()) {
			if (entry.getKey().startsWith(Constants.JVM_Overview)) {
				overview.put(entry.getKey(), entry.getValue());
			}
			if (entry.getKey().startsWith(Constants.JVM_Monitor)) {
				monitor.put(entry.getKey(), entry.getValue());
			}
			if (entry.getKey().startsWith(Constants.JVM_Thread)) {
				thread.put(entry.getKey(), entry.getValue());
			}
			if (entry.getKey().startsWith(Constants.JVM_Memory)) {
				memory.put(entry.getKey(), entry.getValue());
			}
			if (entry.getKey().startsWith(Constants.JVM_GC)) {
				gc.put(entry.getKey(), entry.getValue());
			}
		}
		
		overview.put("date", jvm.get("date"));
		monitor.put("date", jvm.get("date"));
		thread.put("date", jvm.get("date"));
		memory.put("date", jvm.get("date"));
		gc.put("date", jvm.get("date"));
		
		hbaseTemplate.execute(JVMReportTable.TABLE_NAME, new TableCallback<Map<String, Object>>() {
			@Override
			public Map<String, Object> doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(JVMReportTable.rowKey(jvm)));
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
	 * Get data in 1 hour when load html.
	 * 
	 * @param query
	 * @return
	 */
	public List<Map<String, Object>> findInitLoad(Map<String, String> query) {
		Scan scan = new Scan();
		
		/**
		 * Query by rowKey : appName-ipAddress-timestamp
		 */
		String appName = query.get("appName");
		String ipAddress = query.get("ipAddress");
		
		long startTime = System.currentTimeMillis() - 10 * 60 * 1000;
		long endTime = System.currentTimeMillis();

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

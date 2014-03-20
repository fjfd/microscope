package com.vipshop.microscope.storage.hbase.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import com.vipshop.microscope.storage.hbase.domain.JVMTable;

@Repository
public class JVMTableRepository extends AbstraceTableRepository {

	public void initialize() {
		super.initialize(JVMTable.TABLE_NAME, JVMTable.CF);
		super.initialize(JVMTable.INDEX_TABLE_NAME, new String[]{JVMTable.CF_APP, JVMTable.CF_IP});
	}

	public void drop() {
		super.drop(JVMTable.TABLE_NAME);
		super.drop(JVMTable.INDEX_TABLE_NAME);
	}
	
	private String rowKey(Map<String, Object> map) {
		return map.get("app") + "-" +
	           map.get("ip") + "-" +
			   (Long.MAX_VALUE - Long.valueOf(map.get("date").toString())) + "-" +
	           UUID.randomUUID().getLeastSignificantBits();
	}

	public void save(final Map<String, Object> jvm) {
		
		hbaseTemplate.execute(JVMTable.INDEX_TABLE_NAME, new TableCallback<Map<String, Object>>() {
			@Override
			public Map<String, Object> doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes((String)jvm.get("app")));
				p.add(JVMTable.BYTE_CF_APP, Bytes.toBytes((String)jvm.get("app")), Bytes.toBytes((String)jvm.get("app")));
				p.add(JVMTable.BYTE_CF_IP, Bytes.toBytes((String)jvm.get("ip")), Bytes.toBytes((String)jvm.get("ip")));
				table.put(p);
				return jvm;
			}
		});
		
		hbaseTemplate.execute(JVMTable.TABLE_NAME, new TableCallback<Map<String, Object>>() {
			@Override
			public Map<String, Object> doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(rowKey(jvm)));
				p.add(JVMTable.BYTE_CF, JVMTable.BYTE_C_METRICS, SerializationUtils.serialize((Serializable) jvm));
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
		
		hbaseTemplate.find(JVMTable.INDEX_TABLE_NAME, JVMTable.CF_APP, new RowMapper<List<String>>() {
			@Override
			public List<String> mapRow(Result result, int rowNum) throws Exception {
				String[] appQunitifer = getColumnsInColumnFamily(result, JVMTable.CF_APP);
				for (int i = 0; i < appQunitifer.length; i++) {
					appList.add(appQunitifer[i]);
				}
				return appList;
			}
		});
		
		for (Iterator<String> iterator = appList.iterator(); iterator.hasNext();) {
			final String row = iterator.next();
			
			hbaseTemplate.get(JVMTable.INDEX_TABLE_NAME, row, new RowMapper<Map<String, Object>>() {
				@Override
				public Map<String, Object> mapRow(Result result, int rowNum) throws Exception {
					Map<String, Object> appTrace = new HashMap<String, Object>();
					String[] ipQunitifer = getColumnsInColumnFamily(result, JVMTable.CF_IP);
					appTrace.put("app", row);
					appTrace.put("ip", Arrays.asList(ipQunitifer));
					appIPList.add(appTrace);
					return appTrace;
				}
			});
		}
		
		return appIPList;
	}
	
	public List<Map<String, Object>> find(Map<String, String> query) {
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
		
		List<Map<String, Object>> JVMMetrics = hbaseTemplate.find(JVMTable.TABLE_NAME, scan, new RowMapper<Map<String, Object>>() {
			@SuppressWarnings("unchecked")
			@Override
			public Map<String, Object> mapRow(Result result, int rowNum) throws Exception {
				return (Map<String, Object>) SerializationUtils.deserialize(result.getValue(JVMTable.BYTE_CF, JVMTable.BYTE_C_METRICS));
			}
		});
		
		return JVMMetrics;
	}
}

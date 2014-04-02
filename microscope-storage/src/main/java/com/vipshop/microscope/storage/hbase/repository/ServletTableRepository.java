package com.vipshop.microscope.storage.hbase.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import com.vipshop.microscope.storage.hbase.domain.ServletTable;

@Repository
public class ServletTableRepository extends AbstraceTableRepository {

	public void initialize() {
		super.initialize(ServletTable.INDEX_TABLE_NAME, new String[]{ServletTable.CF_APP, ServletTable.CF_IP});
		super.initialize(ServletTable.TABLE_NAME, new String[]{ServletTable.CF_SERVLET});
	}

	public void drop() {
		super.drop(ServletTable.TABLE_NAME);
		super.drop(ServletTable.INDEX_TABLE_NAME);
	}
	
	private String rowKey(Map<String, Object> map) {
		return map.get("app") + "-" +
	           map.get("ip") + "-" +
			   (Long.MAX_VALUE - Long.valueOf(map.get("date").toString()));
//	           UUID.randomUUID().getLeastSignificantBits();
	}

	public void saveActiveRequest(final Map<String, Object> counter) {
		hbaseTemplate.execute(ServletTable.INDEX_TABLE_NAME, new TableCallback<Map<String, Object>>() {
			@Override
			public Map<String, Object> doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes((String)counter.get("app")));
				p.add(ServletTable.BYTE_CF_APP, Bytes.toBytes((String)counter.get("app")), Bytes.toBytes((String)counter.get("app")));
				p.add(ServletTable.BYTE_CF_IP, Bytes.toBytes((String)counter.get("ip")), Bytes.toBytes((String)counter.get("ip")));
				table.put(p);
				return counter;
			}
		});
		
		hbaseTemplate.execute(ServletTable.TABLE_NAME, new TableCallback<Map<String, Object>>() {
			@Override
			public Map<String, Object> doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(rowKey(counter)));
				p.add(ServletTable.BYTE_CF_SERVLET, ServletTable.BYTE_C_ACTIVE_REQUEST, SerializationUtils.serialize((Serializable) counter));
				table.put(p);
				return counter;
			}
		});
	}
	
	public void saveResponseCode(final Map<String, Object> response) {
		hbaseTemplate.execute(ServletTable.TABLE_NAME, new TableCallback<Map<String, Object>>() {
			@Override
			public Map<String, Object> doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(rowKey(response)));
				p.add(ServletTable.BYTE_CF_SERVLET, ServletTable.BYTE_C_RESPONSE_CODE, SerializationUtils.serialize((Serializable) response));
				table.put(p);
				return response;
			}
		});
	}
	
	public void saveRequest(final Map<String, Object> request) {
		hbaseTemplate.execute(ServletTable.TABLE_NAME, new TableCallback<Map<String, Object>>() {
			@Override
			public Map<String, Object> doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(rowKey(request)));
				p.add(ServletTable.BYTE_CF_SERVLET, ServletTable.BYTE_C_REQUEST, SerializationUtils.serialize((Serializable) request));
				table.put(p);
				return request;
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
		
		hbaseTemplate.find(ServletTable.INDEX_TABLE_NAME, ServletTable.CF_APP, new RowMapper<List<String>>() {
			@Override
			public List<String> mapRow(Result result, int rowNum) throws Exception {
				String[] appQunitifer = getColumnsInColumnFamily(result, ServletTable.CF_APP);
				for (int i = 0; i < appQunitifer.length; i++) {
					appList.add(appQunitifer[i]);
				}
				return appList;
			}
		});
		
		for (Iterator<String> iterator = appList.iterator(); iterator.hasNext();) {
			final String row = iterator.next();
			
			hbaseTemplate.get(ServletTable.INDEX_TABLE_NAME, row, new RowMapper<Map<String, Object>>() {
				@Override
				public Map<String, Object> mapRow(Result result, int rowNum) throws Exception {
					Map<String, Object> appTrace = new HashMap<String, Object>();
					String[] ipQunitifer = getColumnsInColumnFamily(result, ServletTable.CF_IP);
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
		
		List<Map<String, Object>> servletMetrics = hbaseTemplate.find(ServletTable.TABLE_NAME, scan, new RowMapper<Map<String, Object>>() {
			@SuppressWarnings("unchecked")
			@Override
			public Map<String, Object> mapRow(Result result, int rowNum) throws Exception {
				Map<String, Object> servletMap = new HashMap<String, Object>();
				servletMap.put("active_request", (Map<String, Object>) SerializationUtils.deserialize(result.getValue(ServletTable.BYTE_CF_SERVLET, ServletTable.BYTE_C_ACTIVE_REQUEST)));
				servletMap.put("response_code", (Map<String, Object>) SerializationUtils.deserialize(result.getValue(ServletTable.BYTE_CF_SERVLET, ServletTable.BYTE_C_RESPONSE_CODE)));
				servletMap.put("request", (Map<String, Object>) SerializationUtils.deserialize(result.getValue(ServletTable.BYTE_CF_SERVLET, ServletTable.BYTE_C_REQUEST)));
				return servletMap;
			}
		});
		
		return servletMetrics;
	}
}

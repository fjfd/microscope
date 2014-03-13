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

@Repository
public class ExceptionTableRepository extends AbstraceTableRepository {

	private String tableName = "exception";
	private String cf = "cf";
	private String cf_map = "cf_map";

	private byte[] CF = Bytes.toBytes(cf);
	private byte[] CF_MAP = Bytes.toBytes(cf_map);

	public void initialize() {
		super.initialize(tableName, cf);
	}

	public void drop() {
		super.drop(tableName);
	}
	
	private String rowKey(Map<String, Object> map) {
		return map.get("APP") + "-" +
	           map.get("IP") + "-" +
			   (Long.MAX_VALUE - Long.valueOf(map.get("Date").toString())) + "-" +
	           UUID.randomUUID().getLeastSignificantBits();
	}

	public void save(final Map<String, Object> map) {
		hbaseTemplate.execute(tableName, new TableCallback<Map<String, Object>>() {
			@Override
			public Map<String, Object> doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(rowKey(map)));
				p.add(CF, CF_MAP, SerializationUtils.serialize((Serializable) map));
				table.put(p);
				return map;
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
		
		String tableName = "app";
		final String cf_app = "cf_app";
		final String cf_ip = "cf_ip";

		final List<String> appList = new ArrayList<String>();
		final List<Map<String, Object>> appTraceList = new ArrayList<Map<String,Object>>();
		
		hbaseTemplate.find(tableName, cf_app, new RowMapper<List<String>>() {
			@Override
			public List<String> mapRow(Result result, int rowNum) throws Exception {
				String[] appQunitifer = getColumnsInColumnFamily(result, cf_app);
				for (int i = 0; i < appQunitifer.length; i++) {
					appList.add(appQunitifer[i]);
				}
				return appList;
			}
		});
		
		for (Iterator<String> iterator = appList.iterator(); iterator.hasNext();) {
			final String row = iterator.next();
			
			hbaseTemplate.get(tableName, row, new RowMapper<Map<String, Object>>() {
				@Override
				public Map<String, Object> mapRow(Result result, int rowNum) throws Exception {
					Map<String, Object> appTrace = new HashMap<String, Object>();
					String[] ipQunitifer = getColumnsInColumnFamily(result, cf_ip);
					appTrace.put("app", row);
					appTrace.put("ip", Arrays.asList(ipQunitifer));
					appTraceList.add(appTrace);
					return appTrace;
				}
			});
		}
		
		return appTraceList;
	}
	
	public List<Map<String, Object>> find(Map<String, String> query) {
		Scan scan = new Scan();
		
		/**
		 * limit the size of result in [10, 100]
		 */
		long limit = Long.valueOf(query.get("limit"));
		
		if (limit > 1000) {
			limit = 1000;
		}
		
		if (limit < 1) {
			limit = 10;
		}
		
		PageFilter pageFilter = new PageFilter(limit);
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
		
		List<Map<String, Object>> excepList = hbaseTemplate.find(tableName, scan, new RowMapper<Map<String, Object>>() {
			@SuppressWarnings("unchecked")
			@Override
			public Map<String, Object> mapRow(Result result, int rowNum) throws Exception {
				return (Map<String, Object>) SerializationUtils.deserialize(result.getValue(CF, CF_MAP));
			}
		});
		
		return excepList;
	}
}

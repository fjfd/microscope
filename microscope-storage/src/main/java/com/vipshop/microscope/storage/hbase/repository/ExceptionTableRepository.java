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

import com.vipshop.microscope.storage.hbase.domain.ExceptionTable;

@Repository
public class ExceptionTableRepository extends AbstraceTableRepository {

	public void initialize() {
		super.initialize(ExceptionTable.TABLE_NAME, ExceptionTable.CF);
		super.initialize(ExceptionTable.INDEX_TABLE_NAME, new String[]{ExceptionTable.CF_APP, ExceptionTable.CF_IP, ExceptionTable.CF_NAME});
	}

	public void drop() {
		super.drop(ExceptionTable.TABLE_NAME);
		super.drop(ExceptionTable.INDEX_TABLE_NAME);
	}
	
	private String rowKey(Map<String, Object> map) {
		return map.get("APP") + "-" +
	           map.get("IP") + "-" +
	           map.get("Name") + "-" +
			   (Long.MAX_VALUE - Long.valueOf(map.get("Date").toString())) + "-" +
	           UUID.randomUUID().getLeastSignificantBits();
	}

	public void save(final Map<String, Object> exception) {
		
		hbaseTemplate.execute(ExceptionTable.INDEX_TABLE_NAME, new TableCallback<Map<String, Object>>() {
			@Override
			public Map<String, Object> doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes((String)exception.get("APP")));
				p.add(ExceptionTable.BYTE_CF_APP, Bytes.toBytes((String)exception.get("APP")), Bytes.toBytes((String)exception.get("APP")));
				p.add(ExceptionTable.BYTE_CF_IP, Bytes.toBytes((String)exception.get("IP")), Bytes.toBytes((String)exception.get("IP")));
				p.add(ExceptionTable.BYTE_CF_NAME, Bytes.toBytes((String)exception.get("Name")), Bytes.toBytes((String)exception.get("Name")));
				table.put(p);
				return exception;
			}
		});
		
		hbaseTemplate.execute(ExceptionTable.TABLE_NAME, new TableCallback<Map<String, Object>>() {
			@Override
			public Map<String, Object> doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(rowKey(exception)));
				p.add(ExceptionTable.BYTE_CF, ExceptionTable.BYTE_C_STACK, SerializationUtils.serialize((Serializable) exception));
				table.put(p);
				return exception;
			}
		});
	}
	
	/**
	 * Returns appName, IPAdress in follow format:
	 * 
	 * [
	 * "app"   :   app name,
	 * "ip"    :   ["ip adress 1", "ip adress 2", ...], 
	 * "name"  :   ["name 1", "name 2", ...], 
	 * ]
	 * 
	 * @return
	 */
	public List<Map<String, Object>> findAppIPName() {
		final List<String> appList = new ArrayList<String>();
		final List<Map<String, Object>> appIPNameList = new ArrayList<Map<String,Object>>();
		
		hbaseTemplate.find(ExceptionTable.INDEX_TABLE_NAME, ExceptionTable.CF_APP, new RowMapper<List<String>>() {
			@Override
			public List<String> mapRow(Result result, int rowNum) throws Exception {
				String[] appQunitifer = getColumnsInColumnFamily(result, ExceptionTable.CF_APP);
				for (int i = 0; i < appQunitifer.length; i++) {
					appList.add(appQunitifer[i]);
				}
				return appList;
			}
		});
		
		for (Iterator<String> iterator = appList.iterator(); iterator.hasNext();) {
			final String row = iterator.next();
			
			hbaseTemplate.get(ExceptionTable.INDEX_TABLE_NAME, row, new RowMapper<Map<String, Object>>() {
				@Override
				public Map<String, Object> mapRow(Result result, int rowNum) throws Exception {
					Map<String, Object> appTrace = new HashMap<String, Object>();
					String[] ipQunitifer = getColumnsInColumnFamily(result, ExceptionTable.CF_IP);
					String[] nameQunitifer = getColumnsInColumnFamily(result, ExceptionTable.CF_NAME);
					appTrace.put("app", row);
					appTrace.put("ip", Arrays.asList(ipQunitifer));
					appTrace.put("name", Arrays.asList(nameQunitifer));
					appIPNameList.add(appTrace);
					return appTrace;
				}
			});
		}
		
		return appIPNameList;
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
		String name = query.get("name");
		
		long startTime = Long.valueOf(query.get("startTime"));
		long endTime = Long.valueOf(query.get("endTime"));
		
		String startKey = appName + "-" + ipAddress + "-" + name + "-" + (Long.MAX_VALUE - endTime);
		String endKey = appName + "-" + ipAddress + "-" + name + "-" + (Long.MAX_VALUE - startTime);
		
		scan.setStartRow(Bytes.toBytes(startKey));
		scan.setStopRow(Bytes.toBytes(endKey));
		
		List<Map<String, Object>> exceptionList = hbaseTemplate.find(ExceptionTable.TABLE_NAME, scan, new RowMapper<Map<String, Object>>() {
			@SuppressWarnings("unchecked")
			@Override
			public Map<String, Object> mapRow(Result result, int rowNum) throws Exception {
				return (Map<String, Object>) SerializationUtils.deserialize(result.getValue(ExceptionTable.BYTE_CF, ExceptionTable.BYTE_C_STACK));
			}
		});
		
		return exceptionList;
	}
}

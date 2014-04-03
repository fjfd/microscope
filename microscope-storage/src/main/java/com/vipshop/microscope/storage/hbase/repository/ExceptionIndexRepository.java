package com.vipshop.microscope.storage.hbase.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import com.vipshop.microscope.common.logentry.Constants;
import com.vipshop.microscope.storage.hbase.table.ExceptionIndexTable;

@Repository
public class ExceptionIndexRepository extends AbstraceRepository {

	/**
	 * Create table
	 */
	public void initialize() {
		super.initialize(ExceptionIndexTable.TABLE_NAME, new String[]{ExceptionIndexTable.CF_APP, 
																	  ExceptionIndexTable.CF_IP, 
																	  ExceptionIndexTable.CF_EXCEPTION});
	}

	/**
	 * Drop table
	 */
	public void drop() {
		super.drop(ExceptionIndexTable.TABLE_NAME);
	}
	
	/**
	 * Save exception index
	 * 
	 * @param exception
	 */
	public void save(final Map<String, Object> exception) {
		hbaseTemplate.execute(ExceptionIndexTable.TABLE_NAME, new TableCallback<Map<String, Object>>() {
			@Override
			public Map<String, Object> doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(ExceptionIndexTable.rowKey(exception));
				p.add(ExceptionIndexTable.BYTE_CF_APP, Bytes.toBytes((String)exception.get(Constants.APP)), Bytes.toBytes((String)exception.get(Constants.APP)));
				p.add(ExceptionIndexTable.BYTE_CF_IP, Bytes.toBytes((String)exception.get(Constants.IP)), Bytes.toBytes((String)exception.get(Constants.IP)));
				p.add(ExceptionIndexTable.BYTE_CF_EXCEPTION, Bytes.toBytes((String)exception.get(Constants.EXCEPTION_NAME)), Bytes.toBytes((String)exception.get(Constants.EXCEPTION_NAME)));
				table.put(p);
				return exception;
			}
		});
	}
	
	/**
	 * Returns App, IP, ExceptionName in follow format:
	 * 
	 * [
	 * "App"   :   app name a,
	 * "IP"    :   ["ip adress 1", "ip adress 2", ...], 
	 * "Name"  :   ["name 1",      "name 2",      ...], 
	 * ]
	 * 
	 * [
	 * "App"   :   app name b,
	 * "IP"    :   ["ip adress 1", "ip adress 2", ...], 
	 * "Name"  :   ["name 1",      "name 2",      ...], 
	 * ]
	 * 
	 * ...
	 * 
	 * @return
	 */
	public List<Map<String, Object>> find() {
		final List<String> appList = new ArrayList<String>();
		hbaseTemplate.find(ExceptionIndexTable.TABLE_NAME, ExceptionIndexTable.CF_APP, new RowMapper<List<String>>() {
			@Override
			public List<String> mapRow(Result result, int rowNum) throws Exception {
				String[] appQunitifer = getColumnsInColumnFamily(result, ExceptionIndexTable.CF_APP);
				for (int i = 0; i < appQunitifer.length; i++) {
					appList.add(appQunitifer[i]);
				}
				return appList;
			}
		});
		
		final List<Map<String, Object>> appIPNameList = new ArrayList<Map<String,Object>>();
		for (final String row : appList) {
			hbaseTemplate.get(ExceptionIndexTable.TABLE_NAME, row, new RowMapper<Map<String, Object>>() {
				@Override
				public Map<String, Object> mapRow(Result result, int rowNum) throws Exception {
					Map<String, Object> appTrace = new HashMap<String, Object>();
					String[] ipQunitifer = getColumnsInColumnFamily(result, ExceptionIndexTable.CF_IP);
					String[] nameQunitifer = getColumnsInColumnFamily(result, ExceptionIndexTable.CF_EXCEPTION);
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
	
}

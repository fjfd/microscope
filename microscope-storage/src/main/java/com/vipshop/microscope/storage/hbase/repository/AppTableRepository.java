package com.vipshop.microscope.storage.hbase.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import com.vipshop.microscope.storage.hbase.domain.AppTable;

@Repository
public class AppTableRepository extends AbstraceTableRepository {
	
	private String tableName = "app";
	private String cf_app = "cf_app";
	private String cf_ip = "cf_ip";
	private String cf_trace = "cf_trace";

	private byte[] BYTE_CF_APP = Bytes.toBytes(cf_app);
	private byte[] BYTE_CF_IP = Bytes.toBytes(cf_ip);
	private byte[] BYTE_CF_TRACE = Bytes.toBytes(cf_trace);
	
	public void initialize() {
		super.initialize(tableName, new String[]{cf_app, cf_ip, cf_trace});
	}
	
	public void drop() {
		super.drop(tableName);
	}
	
	/**
	 * Save appName, IPAdress, traceName(URL) to AppTable.
	 * 
	 * for example
	 * 
	 * ROW                COLUMN+CELL                                                                                                                                                                                              
     * user_info          column=cf_app   :user_info,        timestamp=1393830202990,   value=user_info                                                                                                                                        
     * user_info          column=cf_ip    :12456789,         timestamp=1393829977044,   value=12456789                                                                                                                                                    
     * user_info          column=cf_trace :www.huohu.com,    timestamp=1393830202990,   value=www.huohu.com   
	 * 
	 * @param app
	 */
	public void save(final AppTable app) {
		hbaseTemplate.execute(tableName, new TableCallback<AppTable>() {
			@Override
			public AppTable doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(app.getAppName()));
				p.add(BYTE_CF_APP, Bytes.toBytes(app.getAppName()), Bytes.toBytes(app.getAppName()));
				p.add(BYTE_CF_IP, Bytes.toBytes(app.getIpAdress()), Bytes.toBytes(app.getIpAdress()));
				p.add(BYTE_CF_TRACE, Bytes.toBytes(app.getTraceName()), Bytes.toBytes(app.getTraceName()));
				table.put(p);
				return app;
			}
		});
	}
	
	/**
	 * Save appName, IPAdress, traceName(URL) to AppTable.
	 * 
	 * for example
	 * 
	 * ROW                COLUMN+CELL                                                                                                                                                                                              
     * user_info          column=cf_app   :user_info,        timestamp=1393830202990,   value=user_info                                                                                                                                        
     * user_info          column=cf_ip    :12456789,         timestamp=1393829977044,   value=12456789                                                                                                                                                    
     * user_info          column=cf_trace :www.huohu.com,    timestamp=1393830202990,   value=www.huohu.com   
	 * 
	 * @param app
	 */
	public void save(final List<AppTable> appTables) {
		hbaseTemplate.execute(tableName, new TableCallback<List<AppTable>>() {
			@Override
			public List<AppTable> doInTable(HTableInterface table) throws Throwable {
				List<Put> puts = new ArrayList<Put>();
				for (AppTable appTable : appTables) {
					Put p = new Put(Bytes.toBytes(appTable.getAppName()));
					p.add(BYTE_CF_APP, Bytes.toBytes(appTable.getAppName()), Bytes.toBytes(appTable.getAppName()));
					p.add(BYTE_CF_IP, Bytes.toBytes(appTable.getIpAdress()), Bytes.toBytes(appTable.getIpAdress()));
					p.add(BYTE_CF_TRACE, Bytes.toBytes(appTable.getTraceName()), Bytes.toBytes(appTable.getTraceName()));
					puts.add(p);
				}
				table.put(puts);
				return appTables;
			}
		});
	}

	
	/**
	 * Returns appName, IPAdress, traceName in follow format:
	 * 
	 * [
	 * "app"   :   app name,
	 * "ip"    :   ["ip adress 1", "ip adress 2", ...], 
	 * "trace" :   ["trace name 1", "trace name 2", ...],
	 * ]
	 * 
	 * @return
	 */
	public List<Map<String, Object>> findAppIPTrace() {
		
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
					String[] traceQunitifer = getColumnsInColumnFamily(result, cf_trace);
					appTrace.put("app", row);
					appTrace.put("ip", Arrays.asList(ipQunitifer));
					appTrace.put("trace", Arrays.asList(traceQunitifer));
					appTraceList.add(appTrace);
					return appTrace;
				}
			});
		}
		
		return appTraceList;
	}


}

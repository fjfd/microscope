package com.vipshop.microscope.hbase.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import com.vipshop.microscope.hbase.domain.AppTrace;

@Repository
public class AppTraceRepository extends AbstraceHbaseRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(AppTraceRepository.class);
	
	private String tableName = "app";
	private String cf_app = "cf_app";
	private String cf_trace = "cf_trace";

	private byte[] BYTE_CF_APP = Bytes.toBytes(cf_app);
	private byte[] BYTE_CF_TRACE = Bytes.toBytes(cf_trace);
	
	public void initialize() {
		try {
			if (!admin.tableExists(tableName)) {
				HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
				HColumnDescriptor columnDescriptor = new HColumnDescriptor(cf_app);
				HColumnDescriptor traceDescriptor = new HColumnDescriptor(cf_trace);
				tableDescriptor.addFamily(columnDescriptor);
				tableDescriptor.addFamily(traceDescriptor);
				admin.createTable(tableDescriptor);
			}
		} catch (IOException e) {
			throw new RuntimeException("initialize " + tableName, e);
		}
	}
	
	public void drop() {
		super.drop(tableName);
	}
	
	public void save(final AppTrace app) {
		
		logger.info("insert app to hbase " + app);
		
		hbaseTemplate.execute(tableName, new TableCallback<AppTrace>() {
			@Override
			public AppTrace doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(app.getAppName()));
				p.add(BYTE_CF_APP, Bytes.toBytes(app.getAppName()), Bytes.toBytes(app.getAppName()));
				p.add(BYTE_CF_TRACE, Bytes.toBytes(app.getTraceName()), Bytes.toBytes(app.getTraceName()));
				table.put(p);
				return app;
			}
		});
	}
	
	public List<Map<String, Object>> findAll() {
		
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
					String[] traceQunitifer = getColumnsInColumnFamily(result, cf_trace);
					appTrace.put("app", row);
					appTrace.put("trace", Arrays.asList(traceQunitifer));
					appTraceList.add(appTrace);
					return appTrace;
				}
			});
		}
		
		return appTraceList;
	}


}

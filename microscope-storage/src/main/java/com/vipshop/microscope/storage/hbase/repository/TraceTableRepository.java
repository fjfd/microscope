package com.vipshop.microscope.storage.hbase.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import com.vipshop.microscope.storage.hbase.domain.TraceTable;

@Repository
public class TraceTableRepository extends AbstraceTableRepository {
	
	public void initialize() {
		super.initialize(TraceTable.TABLE_NAME, TraceTable.CF);
	}
	
	public void drop() {
		super.drop(TraceTable.TABLE_NAME);
	}
	
	public void save(final TraceTable tableTrace) {
		hbaseTemplate.execute(TraceTable.TABLE_NAME, new TableCallback<TraceTable>() {
			@Override
			public TraceTable doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(tableTrace.rowKey()));
				p.add(TraceTable.BYTE_CF, TraceTable.BYTE_C_APP_NAME, Bytes.toBytes(tableTrace.getAppName()));
				p.add(TraceTable.BYTE_CF, TraceTable.BYTE_C_IP_ADDRESS, Bytes.toBytes(tableTrace.getIPAdress()));
				p.add(TraceTable.BYTE_CF, TraceTable.BYTE_C_TRACE_NAME, Bytes.toBytes(tableTrace.getTraceName()));
				p.add(TraceTable.BYTE_CF, TraceTable.BYTE_C_TRACE_ID, Bytes.toBytes(tableTrace.getTraceId()));
				p.add(TraceTable.BYTE_CF, TraceTable.BYTE_C_START_TIMESTAMP, Bytes.toBytes(tableTrace.getStartTimestamp()));
				p.add(TraceTable.BYTE_CF, TraceTable.BYTE_C_END_TIMESTAMP, Bytes.toBytes(tableTrace.getEndTimestamp()));
				p.add(TraceTable.BYTE_CF, TraceTable.BYTE_C_DURATION, Bytes.toBytes(tableTrace.getDuration()));
				p.add(TraceTable.BYTE_CF, TraceTable.BYTE_C_RESULT_CODE, Bytes.toBytes(tableTrace.getResultCode()));
				p.add(TraceTable.BYTE_CF, TraceTable.BYTE_C_TYPE, Bytes.toBytes(tableTrace.getType()));
				table.put(p);
				return tableTrace;
			}
		});
	}
	
	public void save(final List<TraceTable> tableTraces) {
		hbaseTemplate.execute(TraceTable.TABLE_NAME, new TableCallback<List<TraceTable>>() {
			@Override
			public List<TraceTable> doInTable(HTableInterface table) throws Throwable {
				List<Put> puts = new ArrayList<Put>();
				for (TraceTable tableTrace : tableTraces) {
					Put p = new Put(Bytes.toBytes(tableTrace.getTraceId()));
					p.add(TraceTable.BYTE_CF, TraceTable.BYTE_C_APP_NAME, Bytes.toBytes(tableTrace.getAppName()));
					p.add(TraceTable.BYTE_CF, TraceTable.BYTE_C_IP_ADDRESS, Bytes.toBytes(tableTrace.getIPAdress()));
					p.add(TraceTable.BYTE_CF, TraceTable.BYTE_C_TRACE_NAME, Bytes.toBytes(tableTrace.getTraceName()));
					p.add(TraceTable.BYTE_CF, TraceTable.BYTE_C_TRACE_ID, Bytes.toBytes(tableTrace.getTraceId()));
					p.add(TraceTable.BYTE_CF, TraceTable.BYTE_C_START_TIMESTAMP, Bytes.toBytes(tableTrace.getStartTimestamp()));
					p.add(TraceTable.BYTE_CF, TraceTable.BYTE_C_END_TIMESTAMP, Bytes.toBytes(tableTrace.getEndTimestamp()));
					p.add(TraceTable.BYTE_CF, TraceTable.BYTE_C_DURATION, Bytes.toBytes(tableTrace.getDuration()));
					p.add(TraceTable.BYTE_CF, TraceTable.BYTE_C_RESULT_CODE, Bytes.toBytes(tableTrace.getResultCode()));
					p.add(TraceTable.BYTE_CF, TraceTable.BYTE_C_TYPE, Bytes.toBytes(tableTrace.getType()));
					puts.add(p);
				}
				table.put(puts);
				return tableTraces;
			}
		});
	}
	
	public List<TraceTable> find(Map<String, String> query) {
		Scan scan = new Scan();
		
		/**
		 * limit the size of result in [10, 1000]
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
		
		String appName = query.get("appName");
		String ipAddress = query.get("ipAddress");
		String traceName = query.get("traceName");
		
		long startTime = Long.valueOf(query.get("startTime"));
		long endTime = Long.valueOf(query.get("endTime"));
		
		/*
		 * Query by rowKey : appName-traceName-timestamp-ipAddress-******
		 */
		String startKey = appName + "-" + traceName + "-" + ipAddress + "-" + (Long.MAX_VALUE - endTime);
		String endKey = appName + "-" + traceName + "-" + ipAddress + "-" + (Long.MAX_VALUE - startTime);
		
		/*
		 * if ipAdress equals "All", remove ipAddress from query condition.
		 */
		if (ipAddress.equals("All")) {
			/*
			 * Query by rowKey : appName-traceName-timestamp-******
			 */
			startKey = appName + "-" + traceName + "-" + (Long.MAX_VALUE - endTime);
			endKey = appName + "-" + traceName + "-" + (Long.MAX_VALUE - startTime);
		}
		
		scan.setStartRow(Bytes.toBytes(startKey));
		scan.setStopRow(Bytes.toBytes(endKey));
					
		List<TraceTable> tableTraces = hbaseTemplate.find(TraceTable.TABLE_NAME, scan, new RowMapper<TraceTable>() {
			@Override
			public TraceTable mapRow(Result result, int rowNum) throws Exception {
				return new TraceTable(
				  Bytes.toString(result.getValue(TraceTable.BYTE_CF, TraceTable.BYTE_C_APP_NAME)), 
				  Bytes.toString(result.getValue(TraceTable.BYTE_CF, TraceTable.BYTE_C_IP_ADDRESS)),
				  Bytes.toString(result.getValue(TraceTable.BYTE_CF, TraceTable.BYTE_C_TRACE_NAME)),
				  Bytes.toString(result.getValue(TraceTable.BYTE_CF, TraceTable.BYTE_C_TRACE_ID)), 
				  Bytes.toString(result.getValue(TraceTable.BYTE_CF, TraceTable.BYTE_C_START_TIMESTAMP)),
				  Bytes.toString(result.getValue(TraceTable.BYTE_CF, TraceTable.BYTE_C_END_TIMESTAMP)),
				  Bytes.toString(result.getValue(TraceTable.BYTE_CF, TraceTable.BYTE_C_DURATION)),
				  Bytes.toString(result.getValue(TraceTable.BYTE_CF, TraceTable.BYTE_C_RESULT_CODE)),
				  Bytes.toString(result.getValue(TraceTable.BYTE_CF, TraceTable.BYTE_C_TYPE))
			  );
			}
		});
		
		Collections.sort(tableTraces);
		return tableTraces;
	}
	
	public List<TraceTable> find(Scan scan) { 
		List<TraceTable> tableTraces = hbaseTemplate.find(TraceTable.TABLE_NAME, scan, new RowMapper<TraceTable>() {
			@Override
			public TraceTable mapRow(Result result, int rowNum) throws Exception {
				return new TraceTable(
						  Bytes.toString(result.getValue(TraceTable.BYTE_CF, TraceTable.BYTE_C_APP_NAME)), 
						  Bytes.toString(result.getValue(TraceTable.BYTE_CF, TraceTable.BYTE_C_IP_ADDRESS)),
						  Bytes.toString(result.getValue(TraceTable.BYTE_CF, TraceTable.BYTE_C_TRACE_NAME)),
						  Bytes.toString(result.getValue(TraceTable.BYTE_CF, TraceTable.BYTE_C_TRACE_ID)), 
						  Bytes.toString(result.getValue(TraceTable.BYTE_CF, TraceTable.BYTE_C_START_TIMESTAMP)),
						  Bytes.toString(result.getValue(TraceTable.BYTE_CF, TraceTable.BYTE_C_END_TIMESTAMP)),
						  Bytes.toString(result.getValue(TraceTable.BYTE_CF, TraceTable.BYTE_C_DURATION)),
						  Bytes.toString(result.getValue(TraceTable.BYTE_CF, TraceTable.BYTE_C_RESULT_CODE)),
						  Bytes.toString(result.getValue(TraceTable.BYTE_CF, TraceTable.BYTE_C_TYPE)));
			}
		});
		
		Collections.sort(tableTraces);
		return tableTraces;
	}
	
}

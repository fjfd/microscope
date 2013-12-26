package com.vipshop.microscope.storage.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import com.vipshop.microscope.storage.domain.TraceTable;

@Repository
public class TraceTableRepository extends AbstraceHbaseRepository {
	
	private String tableName = "trace";
	private String cf = "cf";

	private byte[] CF = Bytes.toBytes(cf);
	private byte[] CF_APP_NAME = Bytes.toBytes("app_name");
	private byte[] CF_TYPE = Bytes.toBytes("type");
	private byte[] CF_TRACE_ID = Bytes.toBytes("trace_id");
	private byte[] CF_TRACE_NAME = Bytes.toBytes("trace_name");
	private byte[] CF_START_TIMESTAMP = Bytes.toBytes("start_timestamp");
	private byte[] CF_END_TIMESTAMP = Bytes.toBytes("end_timestamp");
	private byte[] CF_DURATION = Bytes.toBytes("duration");
	private byte[] CF_IP_ADDRESS = Bytes.toBytes("ip_address");
	private byte[] CF_RESULT_CODE = Bytes.toBytes("result_code");
	
	public void initialize() {
		super.initialize(tableName, cf);
	}
	
	public void drop() {
		super.drop(tableName);
	}
	
	public void save(final TraceTable tableTrace) {
		hbaseTemplate.execute(tableName, new TableCallback<TraceTable>() {
			@Override
			public TraceTable doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(tableTrace.rowKey()));
				p.add(CF, CF_APP_NAME, Bytes.toBytes(tableTrace.getAppName()));
				p.add(CF, CF_TYPE, Bytes.toBytes(tableTrace.getType()));
				p.add(CF, CF_TRACE_ID, Bytes.toBytes(tableTrace.getTraceId()));
				p.add(CF, CF_TRACE_NAME, Bytes.toBytes(tableTrace.getTraceName()));
				p.add(CF, CF_START_TIMESTAMP, Bytes.toBytes(tableTrace.getStartTimestamp()));
				p.add(CF, CF_END_TIMESTAMP, Bytes.toBytes(tableTrace.getEndTimestamp()));
				p.add(CF, CF_DURATION, Bytes.toBytes(tableTrace.getDuration()));
				p.add(CF, CF_IP_ADDRESS, Bytes.toBytes(tableTrace.getIPAdress()));
				p.add(CF, CF_RESULT_CODE, Bytes.toBytes(tableTrace.getResultCode()));
				table.put(p);
				return tableTrace;
			}
		});
	}
	
	public void save(final List<TraceTable> tableTraces) {
		hbaseTemplate.execute(tableName, new TableCallback<List<TraceTable>>() {
			@Override
			public List<TraceTable> doInTable(HTableInterface table) throws Throwable {
				List<Put> puts = new ArrayList<Put>();
				for (TraceTable tableTrace : tableTraces) {
					Put p = new Put(Bytes.toBytes(tableTrace.getTraceId()));
					p.add(CF, CF_APP_NAME, Bytes.toBytes(tableTrace.getAppName()));
					p.add(CF, CF_TYPE, Bytes.toBytes(tableTrace.getType()));
					p.add(CF, CF_TRACE_ID, Bytes.toBytes(tableTrace.getTraceId()));
					p.add(CF, CF_TRACE_NAME, Bytes.toBytes(tableTrace.getTraceName()));
					p.add(CF, CF_START_TIMESTAMP, Bytes.toBytes(tableTrace.getStartTimestamp()));
					p.add(CF, CF_END_TIMESTAMP, Bytes.toBytes(tableTrace.getEndTimestamp()));
					p.add(CF, CF_DURATION, Bytes.toBytes(tableTrace.getDuration()));
					p.add(CF, CF_RESULT_CODE, Bytes.toBytes(tableTrace.getResultCode()));
					p.add(CF, CF_IP_ADDRESS, Bytes.toBytes(tableTrace.getIPAdress()));
					table.put(p);
					puts.add(p);
				}
				table.put(puts);
				return tableTraces;
			}
		});
	}
	
	public List<TraceTable> findAll() {
		return hbaseTemplate.find(tableName, cf, new RowMapper<TraceTable>() {
			@Override
			public TraceTable mapRow(Result result, int rowNum) throws Exception {
				return new TraceTable(Bytes.toString(result.getValue(CF, CF_APP_NAME)), 
						  Bytes.toString(result.getValue(CF, CF_TYPE)),
						  Bytes.toString(result.getValue(CF, CF_TRACE_ID)), 
						  Bytes.toString(result.getValue(CF, CF_TRACE_NAME)),
						  Bytes.toString(result.getValue(CF, CF_START_TIMESTAMP)),
						  Bytes.toString(result.getValue(CF, CF_END_TIMESTAMP)),
						  Bytes.toString(result.getValue(CF, CF_DURATION)),
						  Bytes.toString(result.getValue(CF, CF_RESULT_CODE)),
						  Bytes.toString(result.getValue(CF, CF_IP_ADDRESS)));
				}
		});
	}
	
	public List<TraceTable> findByTraceId(String traceId) {
		Scan scan = new Scan();
		RowFilter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator(traceId + ".*"));
		scan.setFilter(filter);
		return hbaseTemplate.find(tableName, scan, new RowMapper<TraceTable>() {
			@Override
			public TraceTable mapRow(Result result, int rowNum) throws Exception {
				return new TraceTable(Bytes.toString(result.getValue(CF, CF_APP_NAME)), 
									  Bytes.toString(result.getValue(CF, CF_TYPE)),
									  Bytes.toString(result.getValue(CF, CF_TRACE_ID)), 
									  Bytes.toString(result.getValue(CF, CF_TRACE_NAME)),
									  Bytes.toString(result.getValue(CF, CF_START_TIMESTAMP)),
									  Bytes.toString(result.getValue(CF, CF_END_TIMESTAMP)),
									  Bytes.toString(result.getValue(CF, CF_DURATION)),
									  Bytes.toString(result.getValue(CF, CF_RESULT_CODE)),
									  Bytes.toString(result.getValue(CF, CF_IP_ADDRESS))); 
			}
		});
	}
	
	public List<TraceTable> findByQuery() {
		Scan scan = new Scan();
		PageFilter pageFilter = new PageFilter(10);
		scan.setFilter(pageFilter);
		
		return hbaseTemplate.find(tableName, scan, new RowMapper<TraceTable>() {
			@Override
			public TraceTable mapRow(Result result, int rowNum) throws Exception {
				return new TraceTable(Bytes.toString(result.getValue(CF, CF_APP_NAME)), 
						  Bytes.toString(result.getValue(CF, CF_TYPE)),
						  Bytes.toString(result.getValue(CF, CF_TRACE_ID)), 
						  Bytes.toString(result.getValue(CF, CF_TRACE_NAME)),
						  Bytes.toString(result.getValue(CF, CF_START_TIMESTAMP)),
						  Bytes.toString(result.getValue(CF, CF_END_TIMESTAMP)),
						  Bytes.toString(result.getValue(CF, CF_DURATION)),
						  Bytes.toString(result.getValue(CF, CF_RESULT_CODE)),
						  Bytes.toString(result.getValue(CF, CF_IP_ADDRESS)));
			}
		});
	}
	
	public List<TraceTable> findByQuery(Map<String, String> query) {
		Scan scan = new Scan();
		RowFilter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator(".*" + query.get("traceName") + ".*"));
		
		long limit = Long.valueOf(query.get("limit"));
		if (limit > 100) {
			limit = 100;
		}
		PageFilter pageFilter = new PageFilter(limit);
		FilterList filterList = new FilterList(pageFilter, filter);
		scan.setFilter(filterList);
		try {
			scan.setTimeRange(Long.valueOf(query.get("startTime")), Long.valueOf(query.get("endTime")));
		} catch (IOException e) {
			throw new RuntimeException("set time range exception", e);
		}
		return hbaseTemplate.find(tableName, scan, new RowMapper<TraceTable>() {
			@Override
			public TraceTable mapRow(Result result, int rowNum) throws Exception {
				return new TraceTable(Bytes.toString(result.getValue(CF, CF_APP_NAME)), 
						  Bytes.toString(result.getValue(CF, CF_TYPE)),
						  Bytes.toString(result.getValue(CF, CF_TRACE_ID)), 
						  Bytes.toString(result.getValue(CF, CF_TRACE_NAME)),
						  Bytes.toString(result.getValue(CF, CF_START_TIMESTAMP)),
						  Bytes.toString(result.getValue(CF, CF_END_TIMESTAMP)),
						  Bytes.toString(result.getValue(CF, CF_DURATION)),
						  Bytes.toString(result.getValue(CF, CF_RESULT_CODE)),
						  Bytes.toString(result.getValue(CF, CF_IP_ADDRESS)));
			}
		});
	}
	
	public List<TraceTable> findWithScan(Scan scan) {
		return hbaseTemplate.find(tableName, scan, new RowMapper<TraceTable>() {
			@Override
			public TraceTable mapRow(Result result, int rowNum) throws Exception {
				return new TraceTable(Bytes.toString(result.getValue(CF, CF_APP_NAME)), 
						  Bytes.toString(result.getValue(CF, CF_TYPE)),
						  Bytes.toString(result.getValue(CF, CF_TRACE_ID)), 
						  Bytes.toString(result.getValue(CF, CF_TRACE_NAME)),
						  Bytes.toString(result.getValue(CF, CF_START_TIMESTAMP)),
						  Bytes.toString(result.getValue(CF, CF_END_TIMESTAMP)),
						  Bytes.toString(result.getValue(CF, CF_DURATION)),
						  Bytes.toString(result.getValue(CF, CF_RESULT_CODE)),
						  Bytes.toString(result.getValue(CF, CF_IP_ADDRESS)));
			}
		});
	}
	
}

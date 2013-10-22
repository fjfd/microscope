package com.vipshop.microscope.hbase.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import com.vipshop.microscope.hbase.domain.TraceTable;

@Repository
public class TraceTableRepository extends AbstraceHbaseRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(TraceTableRepository.class);
	
	private String tableName = "trace";
	private String cf = "cf";

	private byte[] CF = Bytes.toBytes(cf);

	private byte[] CF_TRACE_ID = Bytes.toBytes("trace_id");
	private byte[] CF_TRACE_NAME = Bytes.toBytes("trace_name");
	private byte[] CF_START_TIMESTAMP = Bytes.toBytes("start_timestamp");
	private byte[] CF_END_TIMESTAMP = Bytes.toBytes("end_timestamp");
	private byte[] CF_DURATION = Bytes.toBytes("duration");
	
	public void initialize() {
		super.initialize(tableName, cf);
	}
	
	public void drop() {
		super.drop(tableName);
	}
	
	public void save(final TraceTable tableTrace) {
		logger.info("insert traceTable to hbase " + tableTrace);
		
		hbaseTemplate.execute(tableName, new TableCallback<TraceTable>() {
			@Override
			public TraceTable doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(tableTrace.getTraceName() + tableTrace.getTraceId()));
				p.add(CF, CF_TRACE_ID, Bytes.toBytes(tableTrace.getTraceId()));
				p.add(CF, CF_TRACE_NAME, Bytes.toBytes(tableTrace.getTraceName()));
				p.add(CF, CF_START_TIMESTAMP, Bytes.toBytes(tableTrace.getStartTimestamp()));
				p.add(CF, CF_END_TIMESTAMP, Bytes.toBytes(tableTrace.getEndTimestamp()));
				p.add(CF, CF_DURATION, Bytes.toBytes(tableTrace.getDuration()));
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
					p.add(CF, CF_TRACE_ID, Bytes.toBytes(tableTrace.getTraceId()));
					p.add(CF, CF_TRACE_NAME, Bytes.toBytes(tableTrace.getTraceName()));
					p.add(CF, CF_START_TIMESTAMP, Bytes.toBytes(tableTrace.getStartTimestamp()));
					p.add(CF, CF_END_TIMESTAMP, Bytes.toBytes(tableTrace.getEndTimestamp()));
					p.add(CF, CF_DURATION, Bytes.toBytes(tableTrace.getDuration()));
					table.put(p);
					puts.add(p);
				}
				table.put(puts);
				return tableTraces;
			}
		});
	}
	
	public void delete(final TraceTable tableTrace) {
		hbaseTemplate.execute(tableName, new TableCallback<TraceTable>() {
			@Override
			public TraceTable doInTable(HTableInterface table) throws Throwable {
				Delete d = new Delete(Bytes.toBytes(tableTrace.getTraceId()));
				table.delete(d);
				return tableTrace;
			}
		});
	}
	
	public List<TraceTable> findAll() {
		return hbaseTemplate.find(tableName, cf, new RowMapper<TraceTable>() {
			@Override
			public TraceTable mapRow(Result result, int rowNum) throws Exception {
				return new TraceTable(Bytes.toString(result.getValue(CF, CF_TRACE_ID)), 
									  Bytes.toString(result.getValue(CF, CF_TRACE_NAME)),
									  Bytes.toString(result.getValue(CF, CF_START_TIMESTAMP)),
									  Bytes.toString(result.getValue(CF, CF_END_TIMESTAMP)),
									  Bytes.toString(result.getValue(CF, CF_DURATION))); 
			}
		});
	}
	
	public TraceTable findByTraceId(String traceId) {
		return hbaseTemplate.get(tableName, traceId, new RowMapper<TraceTable>() {
			@Override
			public TraceTable mapRow(Result result, int rowNum) throws Exception {
				return new TraceTable(Bytes.toString(result.getValue(CF, CF_TRACE_ID)), 
									  Bytes.toString(result.getValue(CF, CF_TRACE_NAME)),
									  Bytes.toString(result.getValue(CF, CF_START_TIMESTAMP)),
									  Bytes.toString(result.getValue(CF, CF_END_TIMESTAMP)),
									  Bytes.toString(result.getValue(CF, CF_DURATION))); 
			}
		});
	}
	
	public List<TraceTable> findByName(Map<String, String> query) {
		Scan scan = new Scan();
		RowFilter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator(query.get("traceName") + ".*"));
		FilterList filterList = new FilterList(filter);
		scan.setFilter(filterList);
		return hbaseTemplate.find(tableName, scan, new RowMapper<TraceTable>() {
			@Override
			public TraceTable mapRow(Result result, int rowNum) throws Exception {
				return new TraceTable(Bytes.toString(result.getValue(CF, CF_TRACE_ID)), 
									  Bytes.toString(result.getValue(CF, CF_TRACE_NAME)),
									  Bytes.toString(result.getValue(CF, CF_START_TIMESTAMP)),
									  Bytes.toString(result.getValue(CF, CF_END_TIMESTAMP)),
									  Bytes.toString(result.getValue(CF, CF_DURATION))); 
			}
		});
	}
	
	public List<TraceTable> findWithScan(Scan scan) {
		return hbaseTemplate.find(tableName, scan, new RowMapper<TraceTable>() {
			@Override
			public TraceTable mapRow(Result result, int rowNum) throws Exception {
				return new TraceTable(Bytes.toString(result.getValue(CF, CF_TRACE_ID)), 
						  			  Bytes.toString(result.getValue(CF, CF_TRACE_NAME)),
						  			  Bytes.toString(result.getValue(CF, CF_START_TIMESTAMP)),
						  			  Bytes.toString(result.getValue(CF, CF_END_TIMESTAMP)),
						  			  Bytes.toString(result.getValue(CF, CF_DURATION))); 
			}
		});
	}
	
}

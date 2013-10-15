package com.vipshop.microscope.hbase.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.SerializationUtils;
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
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import com.vipshop.microscope.hbase.core.HbaseRepository;
import com.vipshop.microscope.hbase.domain.TraceTable;

@Repository
public class TraceRepository extends HbaseRepository {

	private String tableName = TraceTable.TABLE_NAME;
	private String cfInfo = TraceTable.CF_INFO;

	private byte[] CF_INFO = Bytes.toBytes(TraceTable.CF_INFO);

	private byte[] cTraceId = Bytes.toBytes(TraceTable.CF_INFO_TRACE_ID);
	private byte[] cTraceName = Bytes.toBytes(TraceTable.CF_INFO_TRACE_NAME);
	private byte[] cStmpName = Bytes.toBytes(TraceTable.CF_INFO_TRACE_STMP);
	private byte[] cEtmpName = Bytes.toBytes(TraceTable.CF_INFO_TRACE_ETMP);
	private byte[] cDuraName = Bytes.toBytes(TraceTable.CF_INFO_TRACE_DURA);
	
	public void initialize() {
		super.initialize(tableName, cfInfo);
	}
	
	public void drop() {
		super.drop(TraceTable.TABLE_NAME);
	}
	
	public void save(final String traceId, final String traceName) {
		hbaseTemplate.execute(tableName, new TableCallback<TraceTable>() {
			@Override
			public TraceTable doInTable(HTableInterface table) throws Throwable {
				TraceTable tableTrace = new TraceTable(traceId, traceName);
				Put p = new Put(Bytes.toBytes(tableTrace.getTraceId()));
				p.add(CF_INFO, cTraceId, Bytes.toBytes(tableTrace.getTraceId()));
				p.add(CF_INFO, cTraceName, Bytes.toBytes(tableTrace.getTraceName()));
				table.put(p);
				return tableTrace;
			}
		});
	}
	
//	public void save(final String traceId, final String traceName, final String spanName) {
//		hbaseTemplate.execute(tableName, new TableCallback<String>() {
//			@Override
//			public String doInTable(HTableInterface table) throws Throwable {
//				Put p = new Put(Bytes.toBytes(tableTrace.getTraceId()));
//				p.add(CF_INFO, cTraceId, Bytes.toBytes(tableTrace.getTraceId()));
//				p.add(CF_INFO, cTraceName, Bytes.toBytes(tableTrace.getTraceName()));
//				table.put(p);
//				return tableTrace;
//			}
//		});
//	}
	
	public void save(final TraceTable tableTrace) {
		if (tableTrace == null) {
			return;
		}
		
		hbaseTemplate.execute(tableName, new TableCallback<TraceTable>() {
			@Override
			public TraceTable doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(tableTrace.getTraceName() + tableTrace.getTraceId()));
				p.add(CF_INFO, cTraceId, Bytes.toBytes(tableTrace.getTraceId()));
				p.add(CF_INFO, cTraceName, Bytes.toBytes(tableTrace.getTraceName()));
				p.add(CF_INFO, cStmpName, Bytes.toBytes(tableTrace.getStartTimestamp()));
				p.add(CF_INFO, cEtmpName, Bytes.toBytes(tableTrace.getEndTimestamp()));
				p.add(CF_INFO, cDuraName, Bytes.toBytes(tableTrace.getDuration()));
				table.put(p);
				return tableTrace;
			}
		});
	}
	
	public void saveTrace(final TraceTable tableTrace) {
		if (tableTrace == null) {
			return;
		}
		
		hbaseTemplate.execute(tableName, new TableCallback<TraceTable>() {
			@Override
			public TraceTable doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(tableTrace.getTraceName() + tableTrace.getTraceId()));
				p.add(CF_INFO, Bytes.toBytes("trace"), SerializationUtils.serialize(tableTrace));
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
					p.add(CF_INFO, cTraceId, Bytes.toBytes(tableTrace.getTraceId()));
					p.add(CF_INFO, cTraceName, Bytes.toBytes(tableTrace.getTraceName()));
					p.add(CF_INFO, cStmpName, Bytes.toBytes(tableTrace.getStartTimestamp()));
					p.add(CF_INFO, cEtmpName, Bytes.toBytes(tableTrace.getEndTimestamp()));
					p.add(CF_INFO, cDuraName, Bytes.toBytes(tableTrace.getDuration()));
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
		return hbaseTemplate.find(tableName, cfInfo, new RowMapper<TraceTable>() {
			@Override
			public TraceTable mapRow(Result result, int rowNum) throws Exception {
				return new TraceTable(Bytes.toString(result.getValue(CF_INFO, cTraceId)), 
									  Bytes.toString(result.getValue(CF_INFO, cTraceName)),
									  Bytes.toString(result.getValue(CF_INFO, cStmpName)),
									  Bytes.toString(result.getValue(CF_INFO, cEtmpName)),
									  Bytes.toString(result.getValue(CF_INFO, cDuraName))); 
			}
		});
	}
	
	public TraceTable findByTraceId(String traceId) {
		return hbaseTemplate.get(tableName, traceId, new RowMapper<TraceTable>() {
			@Override
			public TraceTable mapRow(Result result, int rowNum) throws Exception {
				return new TraceTable(Bytes.toString(result.getValue(CF_INFO, cTraceId)), 
									  Bytes.toString(result.getValue(CF_INFO, cTraceName)),
									  Bytes.toString(result.getValue(CF_INFO, cStmpName)),
									  Bytes.toString(result.getValue(CF_INFO, cEtmpName)),
									  Bytes.toString(result.getValue(CF_INFO, cDuraName))); 
			}
		});
	}
	
	public List<TraceTable> findByName(Map<String, String> query) {
		Scan scan = new Scan();
//		SingleColumnValueFilter a = new SingleColumnValueFilter(Bytes.toBytes("cfInfo"),
//		        												Bytes.toBytes("trace_name"), 
//		        												CompareOp.EQUAL, 
//		        												new BinaryComparator(Bytes.toBytes(query.get("traceName"))));
		RowFilter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator(query.get("traceName") + ".*"));
		FilterList filterList = new FilterList(filter);
		scan.setFilter(filterList);
		return hbaseTemplate.find(tableName, scan, new RowMapper<TraceTable>() {
			@Override
			public TraceTable mapRow(Result result, int rowNum) throws Exception {
				return new TraceTable(Bytes.toString(result.getValue(CF_INFO, cTraceId)), 
									  Bytes.toString(result.getValue(CF_INFO, cTraceName)),
									  Bytes.toString(result.getValue(CF_INFO, cStmpName)),
									  Bytes.toString(result.getValue(CF_INFO, cEtmpName)),
									  Bytes.toString(result.getValue(CF_INFO, cDuraName))); 
			}
		});
	}
	
	public List<TraceTable> findWithScan(Scan scan) {
		return hbaseTemplate.find(tableName, scan, new RowMapper<TraceTable>() {
			@Override
			public TraceTable mapRow(Result result, int rowNum) throws Exception {
				return new TraceTable(Bytes.toString(result.getValue(CF_INFO, cTraceId)), 
						  			  Bytes.toString(result.getValue(CF_INFO, cTraceName)),
						  			  Bytes.toString(result.getValue(CF_INFO, cStmpName)),
						  			  Bytes.toString(result.getValue(CF_INFO, cEtmpName)),
						  			  Bytes.toString(result.getValue(CF_INFO, cDuraName))); 
			}
		});
	}
	
}

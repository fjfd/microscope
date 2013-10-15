package com.vipshop.microscope.hbase.repository;

import java.util.List;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import com.vipshop.microscope.hbase.core.HbaseRepository;
import com.vipshop.microscope.hbase.domain.TraceIndex;

@Repository
public class TraceIndexRepository extends HbaseRepository {

	private String tableName = TraceIndex.TABLE_NAME;
	private String cfInfo = TraceIndex.CF_INFO;

	private byte[] CF_INFO = Bytes.toBytes(TraceIndex.CF_INFO);
	private byte[] cAppName = Bytes.toBytes(TraceIndex.CF_INFO_APP_NAME);
	private byte[] cTraceName = Bytes.toBytes(TraceIndex.CF_INFO_TRACE_NAME);
	
	public void initialize() {
		super.initialize(tableName, cfInfo);
	}
	
	public void drop() {
		super.drop(tableName);
	}
	
	public void save(final TraceIndex traceIndex) {
		if (traceIndex == null) {
			return;
		}
		hbaseTemplate.execute(tableName, new TableCallback<TraceIndex>() {
			@Override
			public TraceIndex doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(traceIndex.getAppName() + traceIndex.getTraceName()));
				p.add(CF_INFO, cAppName, Bytes.toBytes(traceIndex.getAppName()));
				p.add(CF_INFO, cTraceName, Bytes.toBytes(traceIndex.getTraceName()));
				table.put(p);
				return traceIndex;
			}
		});
	}
	
	public boolean exist(final TraceIndex traceIndex) {
		return hbaseTemplate.get(tableName, traceIndex.getAppName() + traceIndex.getTraceName(), new RowMapper<Boolean>() {
			@Override
			public Boolean mapRow(Result result, int rowNum) throws Exception {
				byte[] name = result.getValue(CF_INFO, cTraceName);
				if (name == null) {
					return false;
				}
				return true;
			}
		});
	}
	
	public List<TraceIndex> findByAppName(String appName) {
		Scan scan = new Scan();
		SingleColumnValueFilter a = new SingleColumnValueFilter(CF_INFO,
		        												cAppName, 
		        												CompareOp.EQUAL, 
		        												new BinaryComparator(Bytes.toBytes(appName)));
		FilterList filterList = new FilterList(a);
		scan.setFilter(filterList);
		return hbaseTemplate.find(tableName, scan, new RowMapper<TraceIndex>() {
			@Override
			public TraceIndex mapRow(Result result, int rowNum) throws Exception {
				return new TraceIndex(Bytes.toString(result.getValue(CF_INFO, cAppName)), 
									  Bytes.toString(result.getValue(CF_INFO, cTraceName)));
			}
		});
	}
	
	public List<String> findTraceNameByAppName(String appName) {
		Scan scan = new Scan();
		RowFilter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator(appName + ".*"));
		scan.setFilter(filter);
		return hbaseTemplate.find(tableName, scan, new RowMapper<String>() {
			@Override
			public String mapRow(Result result, int rowNum) throws Exception {
				return Bytes.toString(result.getValue(CF_INFO, cTraceName));
			}
		});
	}


}

package com.vipshop.microscope.hbase.repository;

import java.util.ArrayList;
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

import com.vipshop.microscope.hbase.core.HbaseRepository;

@Repository
public class TraceSpanRepository extends HbaseRepository {

	private String tableName = "trace.span";
	private String cfInfo = "cfInfo";

	private byte[] CF_INFO = Bytes.toBytes(cfInfo);
	
	public void initialize() {
		super.initialize(tableName, cfInfo);
	}
	
	public void drop() {
		super.drop(tableName);
	}
	
	public void save(final String traceId, final String spanName) {
		hbaseTemplate.execute(tableName, new TableCallback<String>() {
			@Override
			public String doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(traceId));
				p.add(CF_INFO, Bytes.toBytes(spanName), Bytes.toBytes(spanName));
				table.put(p);
				return spanName;
			}
		});
	}
	
	public List<String> findAll(String traceId) {
		return hbaseTemplate.get(tableName, traceId, new RowMapper<List<String>>() {
			@Override
			public List<String> mapRow(Result result, int rowNum) throws Exception {
				List<String> spans = new ArrayList<String>();
				String[] qunitifer = Repositorys.getColumnsInColumnFamily(result, cfInfo);
				for (int i = 0; i < qunitifer.length; i++) {
					byte[] data = result.getValue(CF_INFO, Bytes.toBytes(qunitifer[i]));
					spans.add(Bytes.toString(data));
				}
				return spans;
			}
		});
	}
	
	public Map<String, String> find(String traceId) {
		return hbaseTemplate.get(tableName, traceId, new RowMapper<Map<String, String>>() {
			@Override
			public Map<String, String> mapRow(Result result, int rowNum) throws Exception {
				Map<String, String> spans = new HashMap<String, String>();
				String[] qunitifer = Repositorys.getColumnsInColumnFamily(result, cfInfo);
				for (int i = 0; i < qunitifer.length; i++) {
					byte[] data = result.getValue(CF_INFO, Bytes.toBytes(qunitifer[i]));
					spans.put(Bytes.toString(data), "1");
				}
				return spans;
			}
		});
	}

}

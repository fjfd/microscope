package com.vipshop.microscope.hbase.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.SerializationUtils;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import com.vipshop.microscope.hbase.domain.SpanTable;
import com.vipshop.microscope.thrift.Span;

@Repository
public class SpanTableRepository extends AbstraceHbaseRepository {

	private String tableName = "span";
	private String cf = "cf";

	private byte[] CF = Bytes.toBytes(cf);

	public void initialize() {
		super.initialize(tableName, cf);
	}

	public void drop() {
		super.drop(tableName);
	}

	public void save(final Span span) {
		hbaseTemplate.execute(tableName, new TableCallback<Span>() {
			@Override
			public Span doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(String.valueOf(span.getTrace_id())));
				p.add(CF, Bytes.toBytes(span.getName()), SerializationUtils.serialize(span));
				table.put(p);
				return span;
			}
		});
	}

	public List<Span> findSpanByTraceId(String traceId) {
		final List<Span> spans = new ArrayList<Span>();
		return hbaseTemplate.get(tableName, traceId, new RowMapper<List<Span>>() {
			@Override
			public List<Span> mapRow(Result result, int rowNum) throws Exception {
				String[] qunitifer = getColumnsInColumnFamily(result, cf);
				for (int i = 0; i < qunitifer.length; i++) {
					byte[] data = result.getValue(CF, Bytes.toBytes(qunitifer[i]));
					Span span = (Span) SerializationUtils.deserialize(data);
					spans.add(span);
				}
				Collections.sort(spans, SpanTable.newSpanComparator());
				return spans;
			}
		});
	}
	
	public Map<String, String> findSpanNameByTraceId(String traceId) {
		final Map<String, String> span = new HashMap<String, String>();
		return hbaseTemplate.get(tableName, traceId, new RowMapper<Map<String, String>>() {
			@Override
			public Map<String, String> mapRow(Result result, int rowNum) throws Exception {
				String[] qunitifer = getColumnsInColumnFamily(result, cf);
				for (int i = 0; i < qunitifer.length; i++) {
					span.put(qunitifer[i], "1");
				}
				return span;
			}
		});
	}
	
}

package com.vipshop.microscope.storage.hbase;

import com.vipshop.microscope.thrift.Span;
import org.apache.commons.lang.SerializationUtils;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TraceRepository extends AbstraceRepository {

    public void initialize() {
        super.initialize(TraceTable.TABLE_NAME, TraceTable.CF);
    }

    public void drop() {
        super.drop(TraceTable.TABLE_NAME);
    }

    public void save(final Span span) {
        hbaseTemplate.execute(TraceTable.TABLE_NAME, new TableCallback<Span>() {
            @Override
            public Span doInTable(HTableInterface table) throws Throwable {
                Put p = new Put(Bytes.toBytes(String.valueOf(span.getTraceId())));
                p.add(TraceTable.BYTE_CF, Bytes.toBytes(span.getSpanName() + "#" + span.getSpanId()), SerializationUtils.serialize(span));
                table.put(p);
                return span;
            }
        });
    }

    public void save(final List<Span> spans) {
        hbaseTemplate.execute(TraceTable.TABLE_NAME, new TableCallback<List<Span>>() {
            @Override
            public List<Span> doInTable(HTableInterface table) throws Throwable {
                List<Put> puts = new ArrayList<Put>();
                for (Span span : spans) {
                    Put p = new Put(Bytes.toBytes(String.valueOf(span.getTraceId())));
                    p.add(TraceTable.BYTE_CF, Bytes.toBytes(span.getSpanName() + "#" + span.getSpanId()), SerializationUtils.serialize(span));
                    puts.add(p);
                }
                table.put(puts);
                return spans;
            }
        });
    }

    public List<Span> find(String traceId) {
        final List<Span> spans = new ArrayList<Span>();
        return hbaseTemplate.get(TraceTable.TABLE_NAME, traceId, new RowMapper<List<Span>>() {
            @Override
            public List<Span> mapRow(Result result, int rowNum) throws Exception {
                String[] qunitifer = getColumnsInColumnFamily(result, TraceTable.CF);
                for (int i = 0; i < qunitifer.length; i++) {
                    byte[] data = result.getValue(TraceTable.BYTE_CF, Bytes.toBytes(qunitifer[i]));
                    Span span = (Span) SerializationUtils.deserialize(data);
                    spans.add(span);
                }
                return TraceTable.doSort(spans);
            }
        });
    }

    public Map<String, Integer> findSpanName(String traceId) {
        final Map<String, Integer> span = new HashMap<String, Integer>();
        return hbaseTemplate.get(TraceTable.TABLE_NAME, traceId, new RowMapper<Map<String, Integer>>() {
            @Override
            public Map<String, Integer> mapRow(Result result, int rowNum) throws Exception {
                String[] qunitifer = getColumnsInColumnFamily(result, TraceTable.CF);
                for (int i = 0; i < qunitifer.length; i++) {
                    String key = qunitifer[i].split("#")[0];
                    if (span.containsKey(key)) {
                        span.put(key, span.get(key) + 1);
                    } else {
                        span.put(key, 1);
                    }
                }
                return span;
            }
        });
    }

}

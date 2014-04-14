package com.vipshop.microscope.storage.hbase.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.vipshop.microscope.storage.hbase.table.ReportTopTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

@Repository
public class ReportTopRepository extends AbstraceRepository {

	public void initialize() {
		super.initialize(ReportTopTable.TABLE_NAME, ReportTopTable.CF);
	}

	public void drop() {
		super.drop(ReportTopTable.TABLE_NAME);
	}
	
	private String rowKey() {
//		return map.get("APP") + "-" +
//	           map.get("IP") + "-" +
//	           map.get("Name") + "-" +
//			   (Long.MAX_VALUE - Long.valueOf(map.get("Date").toString())) + "-" +
//	           UUID.randomUUID().getLeastSignificantBits();
		return "top-rowkey";
	}

	public void save(final Map<String, Object> top) {
		hbaseTemplate.execute(ReportTopTable.TABLE_NAME, new TableCallback<Map<String, Object>>() {
			@Override
			public Map<String, Object> doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(rowKey()));
				for (Entry<String, Object> entry : top.entrySet()) {
					p.add(ReportTopTable.BYTE_CF, Bytes.toBytes(entry.getKey()), Bytes.toBytes((String)entry.getValue()));
				}
				table.put(p);
				return top;
			}
		});
	}
	
	public Map<String, Object> find() {
		final Map<String, Object> top = new HashMap<String, Object>();
		return hbaseTemplate.get(ReportTopTable.TABLE_NAME, rowKey(), new RowMapper<Map<String, Object>>() {
			@Override
			public Map<String, Object> mapRow(Result result, int rowNum) throws Exception {
				String[] topQunitifer = getColumnsInColumnFamily(result, ReportTopTable.CF);
				for (int i = 0; i < topQunitifer.length; i++) {
					byte[] data = result.getValue(ReportTopTable.BYTE_CF, Bytes.toBytes(topQunitifer[i]));
					top.put(topQunitifer[i], Bytes.toString(data));
				}
				return top;
			}
		});
	}

}

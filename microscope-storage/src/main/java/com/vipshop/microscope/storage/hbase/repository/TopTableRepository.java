package com.vipshop.microscope.storage.hbase.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import com.vipshop.microscope.storage.hbase.domain.TopTable;

@Repository
public class TopTableRepository extends AbstraceTableRepository {

	public void initialize() {
		super.initialize(TopTable.TABLE_NAME, TopTable.CF);
	}

	public void drop() {
		super.drop(TopTable.TABLE_NAME);
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
		hbaseTemplate.execute(TopTable.TABLE_NAME, new TableCallback<Map<String, Object>>() {
			@Override
			public Map<String, Object> doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(rowKey()));
				for (Entry<String, Object> entry : top.entrySet()) {
					p.add(TopTable.BYTE_CF, Bytes.toBytes(entry.getKey()), Bytes.toBytes((String)entry.getValue()));
				}
				table.put(p);
				return top;
			}
		});
	}
	
	public Map<String, Object> findTopReport() {
		final Map<String, Object> top = new HashMap<String, Object>();
		return hbaseTemplate.get(TopTable.TABLE_NAME, rowKey(), new RowMapper<Map<String, Object>>() {
			@Override
			public Map<String, Object> mapRow(Result result, int rowNum) throws Exception {
				String[] topQunitifer = getColumnsInColumnFamily(result, TopTable.CF);
				for (int i = 0; i < topQunitifer.length; i++) {
					byte[] data = result.getValue(TopTable.BYTE_CF, Bytes.toBytes(topQunitifer[i]));
					top.put(topQunitifer[i], Bytes.toString(data));
				}
				return top;
			}
		});
	}

}

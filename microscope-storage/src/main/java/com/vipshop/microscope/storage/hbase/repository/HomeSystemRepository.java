package com.vipshop.microscope.storage.hbase.repository;

import com.vipshop.microscope.common.logentry.Constants;
import com.vipshop.microscope.common.system.SystemInfo;
import com.vipshop.microscope.storage.hbase.table.HomeSystemTable;
import org.apache.commons.lang.SerializationUtils;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Map;

@Repository
public class HomeSystemRepository extends AbstraceRepository {

	public void initialize() {
		super.initialize(HomeSystemTable.TABLE_NAME, HomeSystemTable.CF);
	}

	public void drop() {
		super.drop(HomeSystemTable.TABLE_NAME);
	}
	
	/**
	 * Store system info.
	 * 
	 * @param info
	 */
	public void save(final SystemInfo info) {
		hbaseTemplate.execute(HomeSystemTable.TABLE_NAME, new TableCallback<SystemInfo>() {
			@Override
			public SystemInfo doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(HomeSystemTable.rowKey(info)));
				p.add(HomeSystemTable.BYTE_CF, HomeSystemTable.BYTE_C_SYSTEM, SerializationUtils.serialize((Serializable) info));
				table.put(p);
				return info;
			}
		});
	}

    /**
     * find system info.
     *
     * @param query
     * @return
     */
	public SystemInfo find(Map<String, String> query) {

        String rowName = query.get(Constants.APP) + query.get(Constants.IP);

		return hbaseTemplate.get(HomeSystemTable.TABLE_NAME, rowName, new RowMapper<SystemInfo>() {
			@SuppressWarnings("unchecked")
			@Override
			public SystemInfo mapRow(Result result, int rowNum) throws Exception {
				return (SystemInfo) SerializationUtils.deserialize(result.getValue(HomeSystemTable.BYTE_CF, HomeSystemTable.BYTE_C_SYSTEM));
			}
		});
	}

}

package com.vipshop.microscope.storage.hbase.repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.SerializationUtils;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import com.vipshop.microscope.storage.hbase.table.ServletReportTable;

@Repository
public class ServletReportRepository extends AbstraceRepository {

	public void initialize() {
		super.initialize(ServletReportTable.TABLE_NAME, new String[]{ServletReportTable.CF_SERVLET});
	}

	public void drop() {
		super.drop(ServletReportTable.TABLE_NAME);
	}
	
	public void saveActiveRequest(final Map<String, Object> counter) {
		hbaseTemplate.execute(ServletReportTable.TABLE_NAME, new TableCallback<Map<String, Object>>() {
			@Override
			public Map<String, Object> doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(ServletReportTable.rowKey(counter)));
				p.add(ServletReportTable.BYTE_CF_SERVLET, ServletReportTable.BYTE_C_ACTIVE_REQUEST, SerializationUtils.serialize((Serializable) counter));
				table.put(p);
				return counter;
			}
		});
	}
	
	public void saveResponseCode(final Map<String, Object> response) {
		hbaseTemplate.execute(ServletReportTable.TABLE_NAME, new TableCallback<Map<String, Object>>() {
			@Override
			public Map<String, Object> doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(ServletReportTable.rowKey(response)));
				p.add(ServletReportTable.BYTE_CF_SERVLET, ServletReportTable.BYTE_C_RESPONSE_CODE, SerializationUtils.serialize((Serializable) response));
				table.put(p);
				return response;
			}
		});
	}
	
	public void saveRequest(final Map<String, Object> request) {
		hbaseTemplate.execute(ServletReportTable.TABLE_NAME, new TableCallback<Map<String, Object>>() {
			@Override
			public Map<String, Object> doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(ServletReportTable.rowKey(request)));
				p.add(ServletReportTable.BYTE_CF_SERVLET, ServletReportTable.BYTE_C_REQUEST, SerializationUtils.serialize((Serializable) request));
				table.put(p);
				return request;
			}
		});
	}
	
	public List<Map<String, Object>> find(Map<String, String> query) {
		Scan scan = new Scan();
		
		PageFilter pageFilter = new PageFilter(1);
		scan.setFilter(pageFilter);
		
		/**
		 * Query by rowKey : appName-ipAddress-timestamp
		 */
		String appName = query.get("appName");
		String ipAddress = query.get("ipAddress");
		
		long startTime = System.currentTimeMillis() - 60 * 1000;
		long endTime = System.currentTimeMillis();
		
		String startKey = appName + "-" + ipAddress + "-" + (Long.MAX_VALUE - endTime);
		String endKey = appName + "-" + ipAddress + "-" + (Long.MAX_VALUE - startTime);
		
		scan.setStartRow(Bytes.toBytes(startKey));
		scan.setStopRow(Bytes.toBytes(endKey));
		
		List<Map<String, Object>> servletMetrics = hbaseTemplate.find(ServletReportTable.TABLE_NAME, scan, new RowMapper<Map<String, Object>>() {
			@SuppressWarnings("unchecked")
			@Override
			public Map<String, Object> mapRow(Result result, int rowNum) throws Exception {
				Map<String, Object> servletMap = new HashMap<String, Object>();
				servletMap.put("active_request", (Map<String, Object>) SerializationUtils.deserialize(result.getValue(ServletReportTable.BYTE_CF_SERVLET, ServletReportTable.BYTE_C_ACTIVE_REQUEST)));
				servletMap.put("response_code", (Map<String, Object>) SerializationUtils.deserialize(result.getValue(ServletReportTable.BYTE_CF_SERVLET, ServletReportTable.BYTE_C_RESPONSE_CODE)));
				servletMap.put("request", (Map<String, Object>) SerializationUtils.deserialize(result.getValue(ServletReportTable.BYTE_CF_SERVLET, ServletReportTable.BYTE_C_REQUEST)));
				return servletMap;
			}
		});
		
		return servletMetrics;
	}
}

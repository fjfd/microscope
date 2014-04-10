package com.vipshop.microscope.storage.hbase.repository;

import com.vipshop.microscope.common.logentry.Constants;
import com.vipshop.microscope.common.metrics.Metric;
import com.vipshop.microscope.storage.hbase.table.TSDBIndexTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TSDBIndexRepository extends AbstraceRepository {

	/**
	 * Create table
	 */
	public void initialize() {
		super.initialize(TSDBIndexTable.TABLE_NAME, new String[]{TSDBIndexTable.CF_APP, 
																	  TSDBIndexTable.CF_IP, 
																	  TSDBIndexTable.CF_METRICS});
	}

	/**
	 * Drop table
	 */
	public void drop() {
		super.drop(TSDBIndexTable.TABLE_NAME);
	}
	
	/**
	 * Save metrics index
	 * 
	 * @param metric
	 */
	public void save(final Metric metric) {
		hbaseTemplate.execute(TSDBIndexTable.TABLE_NAME, new TableCallback<Metric>() {
			@Override
			public Metric doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes(metric.getTags().get(Constants.APP)));
				p.add(TSDBIndexTable.BYTE_CF_APP, Bytes.toBytes(metric.getTags().get(Constants.APP)), Bytes.toBytes(metric.getTags().get(Constants.APP)));
				p.add(TSDBIndexTable.BYTE_CF_IP, Bytes.toBytes(metric.getTags().get(Constants.IP)), Bytes.toBytes(metric.getTags().get(Constants.IP)));
				p.add(TSDBIndexTable.BYTE_CF_METRICS, Bytes.toBytes(metric.getMetric()), Bytes.toBytes(metric.getMetric()));
				table.put(p);
				return metric;
			}
		});
	}
	
	/**
	 * Returns App, IP, ExceptionName in follow format:
	 * 
	 * [
	 * "App"   :   app name a,
	 * "IP"    :   ["ip adress 1", "ip adress 2", ...], 
	 * "Name"  :   ["name 1",      "name 2",      ...], 
	 * ]
	 * 
	 * [
	 * "App"   :   app name b,
	 * "IP"    :   ["ip adress 1", "ip adress 2", ...], 
	 * "Name"  :   ["name 1",      "name 2",      ...], 
	 * ]
	 * 
	 * ...
	 * 
	 * @return
	 */
	public List<Map<String, Object>> find() {
		final List<String> appList = new ArrayList<String>();
		hbaseTemplate.find(TSDBIndexTable.TABLE_NAME, TSDBIndexTable.CF_APP, new RowMapper<List<String>>() {
			@Override
			public List<String> mapRow(Result result, int rowNum) throws Exception {
				String[] appQunitifer = getColumnsInColumnFamily(result, TSDBIndexTable.CF_APP);
				for (int i = 0; i < appQunitifer.length; i++) {
					appList.add(appQunitifer[i]);
				}
				return appList;
			}
		});
		
		final List<Map<String, Object>> appIPNameList = new ArrayList<Map<String,Object>>();
		for (final String row : appList) {
			hbaseTemplate.get(TSDBIndexTable.TABLE_NAME, row, new RowMapper<Map<String, Object>>() {
				@Override
				public Map<String, Object> mapRow(Result result, int rowNum) throws Exception {
					Map<String, Object> appTrace = new HashMap<String, Object>();
					String[] ipQunitifer = getColumnsInColumnFamily(result, TSDBIndexTable.CF_IP);
					String[] nameQunitifer = getColumnsInColumnFamily(result, TSDBIndexTable.CF_METRICS);
					appTrace.put("app", row);
					appTrace.put("ip", Arrays.asList(ipQunitifer));
					appTrace.put("name", Arrays.asList(nameQunitifer));
					appIPNameList.add(appTrace);
					return appTrace;
				}
			});
		}
		
		return appIPNameList;
	}
	
}

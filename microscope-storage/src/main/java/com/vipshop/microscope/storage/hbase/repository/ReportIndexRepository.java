package com.vipshop.microscope.storage.hbase.repository;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.vipshop.microscope.storage.hbase.table.ReportIndexTable;

@Repository
public class ReportIndexRepository extends AbstraceRepository {

	/**
	 * Create table
	 */
	public void initialize() {
		super.initialize(ReportIndexTable.TABLE_NAME, new String[]{ReportIndexTable.CF_APP, 
																	  ReportIndexTable.CF_IP, 
																	  ReportIndexTable.CF_REPORT});
	}

	/**
	 * Drop table
	 */
	public void drop() {
		super.drop(ReportIndexTable.TABLE_NAME);
	}
	
	/**
	 * Save report index
	 * 
	 * @param report
	 */
	public void save(final Map<String, Object> report) {
		hbaseTemplate.execute(ReportIndexTable.TABLE_NAME, new TableCallback<Map<String, Object>>() {
			@Override
			public Map<String, Object> doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(Bytes.toBytes((String)report.get("APP")));
				p.add(ReportIndexTable.BYTE_CF_APP, Bytes.toBytes((String)report.get("APP")), Bytes.toBytes((String)report.get("APP")));
				p.add(ReportIndexTable.BYTE_CF_IP, Bytes.toBytes((String)report.get("IP")), Bytes.toBytes((String)report.get("IP")));
				p.add(ReportIndexTable.BYTE_CF_REPORT, Bytes.toBytes((String)report.get("Report")), Bytes.toBytes((String)report.get("Report")));
				table.put(p);
				return report;
			}
		});
	}
	
	/**
	 * Returns App, IP, ReportName in follow format:
	 * 
	 * [
	 * "App"     :   app name a,
	 * "IP"      :   ["ip adress 1", "ip adress 2", ...], 
	 * "Report"  :   ["name 1",      "name 2",      ...], 
	 * ]
	 * 
	 * [
	 * "App"     :   app name b,
	 * "IP"      :   ["ip adress 1", "ip adress 2", ...], 
	 * "Report"  :   ["name 1",      "name 2",      ...], 
	 * ]
	 * 
	 * ...
	 * 
	 * @return
	 */
	public List<Map<String, Object>> find() {
		final List<String> appList = new ArrayList<String>();
		hbaseTemplate.find(ReportIndexTable.TABLE_NAME, ReportIndexTable.CF_APP, new RowMapper<List<String>>() {
			@Override
			public List<String> mapRow(Result result, int rowNum) throws Exception {
				String[] appQunitifer = getColumnsInColumnFamily(result, ReportIndexTable.CF_APP);
				for (int i = 0; i < appQunitifer.length; i++) {
					appList.add(appQunitifer[i]);
				}
				return appList;
			}
		});
		
		final List<Map<String, Object>> appIPNameList = new ArrayList<Map<String,Object>>();
		for (final String row : appList) {
			hbaseTemplate.get(ReportIndexTable.TABLE_NAME, row, new RowMapper<Map<String, Object>>() {
				@Override
				public Map<String, Object> mapRow(Result result, int rowNum) throws Exception {
					Map<String, Object> appTrace = new HashMap<String, Object>();
					String[] ipQunitifer = getColumnsInColumnFamily(result, ReportIndexTable.CF_IP);
					String[] nameQunitifer = getColumnsInColumnFamily(result, ReportIndexTable.CF_REPORT);
					appTrace.put("app", row);
					appTrace.put("ip", Arrays.asList(ipQunitifer));
					appTrace.put("report", Arrays.asList(nameQunitifer));
					appIPNameList.add(appTrace);
					return appTrace;
				}
			});
		}
		
		return appIPNameList;
	}
	
}

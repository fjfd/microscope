package com.vipshop.microscope.storage.hbase.repository;

import com.vipshop.microscope.common.logentry.Constants;
import com.vipshop.microscope.storage.hbase.report.LogEntryReport;
import com.vipshop.microscope.common.util.CalendarUtil;
import com.vipshop.microscope.common.util.TimeStampUtil;
import com.vipshop.microscope.storage.hbase.table.ReportLogEntryTable;
import com.vipshop.microscope.storage.hbase.table.ReportTopTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReportLogEntryRepository extends AbstraceRepository {

	public void initialize() {
		super.initialize(ReportLogEntryTable.TABLE_NAME, ReportLogEntryTable.CF);
	}

	public void drop() {
		super.drop(ReportLogEntryTable.TABLE_NAME);
	}
	
	public void save(final LogEntryReport report) {
		hbaseTemplate.execute(ReportTopTable.TABLE_NAME, new TableCallback<LogEntryReport>() {
			@Override
			public LogEntryReport doInTable(HTableInterface table) throws Throwable {
				Put p = new Put(ReportLogEntryTable.rowkey(report));
                p.add(ReportLogEntryTable.BYTE_CF, ReportLogEntryTable.column(report), ReportLogEntryTable.value(report));
				table.put(p);
				return report;
			}
		});
	}
	
	public List<Map<String, Object>> find(Map<String, String> query) {

        Scan scan = new Scan();

        long startTime = Long.valueOf(query.get(Constants.STARTTIME));
        long endTime = Long.valueOf(query.get(Constants.ENDTIME));

        CalendarUtil startCal = new CalendarUtil(startTime);
        CalendarUtil entCal = new CalendarUtil(endTime);

        long startTimeOfDay = TimeStampUtil.timestampOfCurrentDay(startCal);
        long endTimeOfDay = TimeStampUtil.timestampOfCurrentDay(entCal);

        scan.setStartRow(Bytes.toBytes(startTimeOfDay));
        scan.setStopRow(Bytes.toBytes(endTimeOfDay));

        final Map<String, Object> top = new HashMap<String, Object>();
		return hbaseTemplate.find(ReportLogEntryTable.TABLE_NAME, scan, new RowMapper<Map<String, Object>>() {
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

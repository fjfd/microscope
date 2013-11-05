package com.vipshop.microscope.job.trace;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;

import com.vipshop.microscope.hbase.domain.TraceTable;
import com.vipshop.microscope.hbase.repository.Repositorys;
import com.vipshop.microscope.mysql.domain.TraceReport;
import com.vipshop.microscope.mysql.repository.MySQLRepositorys;
import com.vipshop.microscope.trace.span.Category;

public class TraceReportJob {
	
	public static void main(String[] args) {
		Category[] values = Category.values();
		for (int i = 0; i < values.length; i++) {
			
			Scan scan = new Scan();
			RowFilter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator(values[i] + ".*"));
			scan.setFilter(filter);
			
			List<TraceTable> tableTraces = Repositorys.TRACE.findWithScan(scan);
			if (tableTraces.size() == 0) {
				continue;
			}
			
			Collections.sort(tableTraces);
			
			TraceReport report = new TraceReport();
			report.setType(values[i].toString());
			report.setTotalCount(tableTraces.size());
			report.setFailureCount(0);
			report.setFailurePrecent(0);
			report.setMax(Float.valueOf(tableTraces.get(tableTraces.size() - 1).getDuration())/1000);
			report.setMin(Float.valueOf(tableTraces.get(0).getDuration())/1000);
			report.setAvg(TraceTable.avgDuration(tableTraces));
			report.setYear(Calendar.getInstance().get(Calendar.YEAR));
			report.setMonth(Calendar.getInstance().get(Calendar.MONDAY));
			report.setDay(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
			report.setHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
			
			MySQLRepositorys.TRACE_REPORT.save(report);
		}
		
		
//		for (TraceTable traceTable : tableTraces) {
//			System.out.println(traceTable);
//		}
		
	}
}

package com.vipshop.microscope.mysql.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.vipshop.microscope.mysql.factory.JdbcTemplateFactory;
import com.vipshop.microscope.mysql.report.DurationDistReport;
import com.vipshop.microscope.mysql.report.OverTimeReport;
import com.vipshop.microscope.mysql.report.TraceReport;

@Repository
public class TraceReportRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(TraceReportRepository.class);

	private JdbcTemplate jdbcTemplate = JdbcTemplateFactory.JDBCTEMPLATE;
	
	public void save(final TraceReport traceReport) {
		String insert = "insert into trace_report(id, year, month, week, day, hour, type, name, total_count, failure_count, failure_precent, min, max, avg, tps, start_time, end_time) " +
				  	 	                  "values(?,  ?,    ?,     ?,    ?,   ?,    ?,    ?,    ?,           ?,             ?,               ?,   ?,   ?,   ?,   ?,          ?)";
		
		logger.info("insert : " + insert);
		
		jdbcTemplate.update(insert, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, traceReport.getId());
				ps.setInt(2, traceReport.getYear());
				ps.setInt(3, traceReport.getMonth());
				ps.setInt(4, traceReport.getWeek());
				ps.setInt(5, traceReport.getDay());
				ps.setInt(6, traceReport.getHour());
				ps.setString(7, traceReport.getType());
				ps.setString(8, traceReport.getName());
				ps.setLong(9, traceReport.getTotalCount());
				ps.setLong(10, traceReport.getFailureCount());
				ps.setFloat(11, traceReport.getFailurePrecent());
				ps.setFloat(12, traceReport.getMin());
				ps.setFloat(13, traceReport.getMax());
				ps.setFloat(14, traceReport.getAvg());
				ps.setFloat(15, traceReport.getTps());
				ps.setLong(16, traceReport.getStartTime());
				ps.setLong(17, traceReport.getEndTime());
			}
		});
	}
	
	public void save(final DurationDistReport report) {
		String insert = "insert into duration_dist(id, year, month, week, day, hour, type, name, region_0, region_1, region_2, region_3, region_4, region_5, region_6, region_7, region_8, region_9, region_10, region_11, region_12, region_13, region_14, region_15, region_16) " +
				  	 	                   "values(?,  ?,    ?,     ?,    ?,   ?,    ?,    ?,    ?,        ?,        ?,        ?,        ?,        ?,        ?,        ?,        ?,        ?,        ?,         ?,         ?,         ?,         ?,         ?,        ?)";
		
		logger.info("insert : " + insert);
		
		jdbcTemplate.update(insert, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, report.getId());
				ps.setInt(2, report.getYear());
				ps.setInt(3, report.getMonth());
				ps.setInt(4, report.getWeek());
				ps.setInt(5, report.getDay());
				ps.setInt(6, report.getHour());
				ps.setString(7, report.getType());
				ps.setString(8, report.getName());
				ps.setInt(9, report.getRegion_0());
				ps.setInt(10, report.getRegion_1());
				ps.setInt(11, report.getRegion_2());
				ps.setInt(12, report.getRegion_3());
				ps.setInt(13, report.getRegion_4());
				ps.setInt(14, report.getRegion_5());
				ps.setInt(15, report.getRegion_6());
				ps.setInt(16, report.getRegion_7());
				ps.setInt(17, report.getRegion_8());
				ps.setInt(18, report.getRegion_9());
				ps.setInt(19, report.getRegion_10());
				ps.setInt(20, report.getRegion_11());
				ps.setInt(21, report.getRegion_12());
				ps.setInt(22, report.getRegion_13());
				ps.setInt(23, report.getRegion_14());
				ps.setInt(24, report.getRegion_15());
				ps.setInt(25, report.getRegion_16());
			}
		});
	}
	
	public void save(final OverTimeReport report) {
		String insert = "insert into over_time(id, year, month, week, day, hour, minute, type, name, avg_dura, hit_count, fail_count) " +
				  	 	               "values(?,  ?,    ?,     ?,    ?,   ?,    ?,      ?,    ?,    ?,        ?,         ?)";
		
		logger.info("insert : " + insert);
		
		jdbcTemplate.update(insert, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, report.getId());
				ps.setInt(2, report.getYear());
				ps.setInt(3, report.getMonth());
				ps.setInt(4, report.getWeek());
				ps.setInt(5, report.getDay());
				ps.setInt(6, report.getHour());
				ps.setInt(7, report.getMinute());
				ps.setString(8, report.getType());
				ps.setString(9, report.getName());
				ps.setFloat(10, report.getAvgDuration());
				ps.setInt(11, report.getHitCount());
				ps.setInt(12, report.getFailCount());
			}
		});
	}
	
	public List<TraceReport> findTraceReport() {
		final List<TraceReport> list = new ArrayList<TraceReport>();
		String sql = "select * from trace_report";
		jdbcTemplate.query(sql, new RowMapper<TraceReport>() {

			@Override
			public TraceReport mapRow(ResultSet rs, int rowNum) throws SQLException {
				TraceReport traceReport = new TraceReport();
				traceReport.setType(rs.getString("type"));
				traceReport.setName(rs.getString("name"));
				traceReport.setTotalCount(rs.getLong("total_count"));
				traceReport.setFailureCount(rs.getLong("failure_count"));
				traceReport.setFailurePrecent(rs.getFloat("failure_precent"));
				traceReport.setMin(rs.getFloat("min"));
				traceReport.setMax(rs.getFloat("max"));
				traceReport.setAvg(rs.getFloat("avg"));
				traceReport.setTps(rs.getFloat("tps"));
				list.add(traceReport);
				return traceReport;
			}
		});
		return list;
	}
	
	public List<TraceReport> findTraceReport(String sql) {
		final List<TraceReport> list = new ArrayList<TraceReport>();
		
		logger.info("query : " + sql);
		jdbcTemplate.query(sql, new RowMapper<TraceReport>() {

			@Override
			public TraceReport mapRow(ResultSet rs, int rowNum) throws SQLException {
				TraceReport traceReport = new TraceReport();
				traceReport.setType(rs.getString("type"));
				traceReport.setName(rs.getString("name"));
				traceReport.setTotalCount(rs.getLong("total_count"));
				traceReport.setFailureCount(rs.getLong("failure_count"));
				traceReport.setFailurePrecent(rs.getFloat("failure_precent"));
				traceReport.setMin(rs.getFloat("min"));
				traceReport.setMax(rs.getFloat("max"));
				traceReport.setAvg(rs.getFloat("avg"));
				traceReport.setTps(rs.getFloat("tps"));
				list.add(traceReport);
				return traceReport;
			}
		});
		return list;
	}


}

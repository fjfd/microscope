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

import com.vipshop.microscope.mysql.domain.TraceReport;
import com.vipshop.microscope.mysql.factory.JdbcTemplateFactory;

@Repository
public class TraceReportRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(TraceReportRepository.class);

	private JdbcTemplate jdbcTemplate = JdbcTemplateFactory.JDBCTEMPLATE;

	public void create(String sql) {
		jdbcTemplate.execute(sql);
	}

	/**
	 * check one trace_report exist or not.
	 * 
	 * @param id
	 * @return
	 */
	public boolean exist(final String id) {
		String sql = "select id from trace_report where id = ?" ;
		
		List<String> count = jdbcTemplate.queryForList(sql, new Object[]{id}, String.class);
		if (count.size() == 0) {
			return false;
		}
		return true;

	}

	public void save(final TraceReport traceReport) {
		String insert = "insert into trace_report(id, year, month, week, day, hour, type, name, total_count, failure_count, failure_precent, min, max, avg, tps, start_time, end_time) " +
				  	 	                  "values(?,  ?,    ?,     ?,    ?,   ?,    ?,    ?,    ?,           ?,             ?,               ?,   ?,   ?,   ?,   ?,          ?)";

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

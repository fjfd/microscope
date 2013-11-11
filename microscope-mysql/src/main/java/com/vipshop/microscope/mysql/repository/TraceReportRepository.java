package com.vipshop.microscope.mysql.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.vipshop.microscope.mysql.domain.TraceReport;
import com.vipshop.microscope.mysql.template.JdbcTemplateFactory;

@Repository
public class TraceReportRepository {

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
		String insert = "insert into trace_report(id, year, month, week, day, hour, type, name, total_count, failure_count, failure_precent, min, max, avg, tps, start_time, end_time, duration) " +
				  	 	                  "values(?,  ?,    ?,     ?,    ?,   ?,    ?,    ?,    ?,           ?,             ?,               ?,   ?,   ?,   ?,   ?,          ?,        ?)";

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
				ps.setLong(18, traceReport.getDuration());
				
			}
		});
	}

	public void update(final TraceReport traceStat) {
		String update = "update trace_stat set total_count = ?, failure_count = ?, failure_precent =?, min = ?, max = ?, avg = ? where type = ?";

		jdbcTemplate.update(update, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, traceStat.getTotalCount());
				ps.setLong(2, traceStat.getFailureCount());
				ps.setFloat(3, traceStat.getFailurePrecent());
				ps.setFloat(4, traceStat.getMin());
				ps.setFloat(5, traceStat.getMax());
				ps.setFloat(6, traceStat.getAvg());
				ps.setString(7, traceStat.getType());

			}
		});
	}

	public List<TraceReport> findTraceReport() {
		final List<TraceReport> list = new ArrayList<TraceReport>();
		String sql = "select * from trace_stat";
		jdbcTemplate.query(sql, new RowMapper<TraceReport>() {

			@Override
			public TraceReport mapRow(ResultSet rs, int rowNum) throws SQLException {
				TraceReport traceStat = new TraceReport();
				traceStat.setType(rs.getString("type"));
				traceStat.setTotalCount(rs.getLong("total_count"));
				traceStat.setFailureCount(rs.getLong("failure_count"));
				traceStat.setFailurePrecent(rs.getFloat("failure_precent"));
				traceStat.setMin(rs.getFloat("min"));
				traceStat.setMax(rs.getFloat("max"));
				traceStat.setAvg(rs.getFloat("avg"));
				list.add(traceStat);
				return traceStat;
			}
		});
		return list;
	}

}

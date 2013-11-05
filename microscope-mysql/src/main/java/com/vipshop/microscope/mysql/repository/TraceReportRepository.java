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
	 * check one trace_stat exist or not.
	 * 
	 * @param trace
	 * @return
	 */
	public boolean exist(final String trace) {
		String sql = "select type from trace_stat where type = ?" ;
		
		String count = jdbcTemplate.queryForObject(sql, new Object[]{trace}, String.class);
		if (count == null) {
			return false;
		}
		return true;

	}

	public void save(final TraceReport traceStat) {
		String insert = "insert into trace_stat(type, total_count,failure_count, failure_precent, min, max, avg, year, month, day, hour) values(?,?,?,?,?,?,?,?,?,?,?)";

		jdbcTemplate.update(insert, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, traceStat.getType());
				ps.setLong(2, traceStat.getTotalCount());
				ps.setLong(3, traceStat.getFailureCount());
				ps.setFloat(4, traceStat.getFailurePrecent());
				ps.setFloat(5, traceStat.getMin());
				ps.setFloat(6, traceStat.getMax());
				ps.setFloat(7, traceStat.getAvg());
				ps.setInt(8, traceStat.getYear());
				ps.setInt(9, traceStat.getMonth());
				ps.setInt(10, traceStat.getDay());
				ps.setInt(11, traceStat.getHour());
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

	public List<TraceReport> findTraceStat() {
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

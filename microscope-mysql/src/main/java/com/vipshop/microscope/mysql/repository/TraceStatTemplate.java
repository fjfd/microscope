package com.vipshop.microscope.mysql.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.vipshop.microscope.mysql.domain.TraceStat;

public class TraceStatTemplate {

	private JdbcTemplate jdbcTemplate = JdbcTemplateFactory.JDBCTEMPLATE;

	public void create(String sql) {
		jdbcTemplate.execute(sql);
	}

	public void save(final TraceStat traceStat) {
		String insert = "insert into trace_stat(trace_name, total_count,failure_count, failure_precent, min, max, avg) values(?,?,?,?,?,?,?)";

		jdbcTemplate.update(insert, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, traceStat.getName());
				ps.setLong(2, traceStat.getTotalCount());
				ps.setLong(3, traceStat.getFailureCount());
				ps.setFloat(4, traceStat.getFailurePrecent());
				ps.setFloat(5, traceStat.getMin());
				ps.setFloat(6, traceStat.getMax());
				ps.setFloat(7, traceStat.getAvg());

			}
		});
	}
	
	public void update(final TraceStat traceStat) {
		String update = "update trace_stat set total_count = ?, failure_count = ?, failure_precent =?, min = ?, max = ?, avg = ? where trace_name = ?";

		jdbcTemplate.update(update, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, traceStat.getTotalCount());
				ps.setLong(2, traceStat.getFailureCount());
				ps.setFloat(3, traceStat.getFailurePrecent());
				ps.setFloat(4, traceStat.getMin());
				ps.setFloat(5, traceStat.getMax());
				ps.setFloat(6, traceStat.getAvg());
				ps.setString(7, traceStat.getName());

			}
		});
	}
	
	public List<TraceStat> findTraceStat() {
		 final List<TraceStat> list = new ArrayList<TraceStat>();
		 String sql = "select * from trace_stat" ;
		 jdbcTemplate.query(sql, new RowMapper<TraceStat>(){

			@Override
			public TraceStat mapRow(ResultSet rs, int rowNum) throws SQLException {
				TraceStat traceStat = new TraceStat();
				traceStat.setName(rs.getString("trace_name"));
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

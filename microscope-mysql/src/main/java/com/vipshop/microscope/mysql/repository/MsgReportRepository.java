package com.vipshop.microscope.mysql.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.vipshop.microscope.mysql.template.JdbcTemplateFactory;

@Repository
public class MsgReportRepository {

	private JdbcTemplate jdbcTemplate = JdbcTemplateFactory.JDBCTEMPLATE;

	public void create(String sql) {
		jdbcTemplate.execute(sql);
	}
	
	public void stat(long size) {
		if (exist()) {
			update(size);
		} else {
			save(size);
		}
	}

	/**
	 * check one msg_stat exist or not.
	 * 
	 * @param trace
	 * @return
	 */
	public boolean exist() {
		String sql = "select count(*) from msg_stat where msg_day = ?";
		
		Integer count = jdbcTemplate.queryForObject(sql, new Object[]{msgDay()}, Integer.class);
		if (count.intValue() == 0) {
			return false;
		}
		return true;

	}

	public void save(final long size) {
		String insert = "insert into msg_stat(msg_day, msg_num, msg_size) values(?,?,?)";

		jdbcTemplate.update(insert, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, msgDay());
				ps.setLong(2, 1);
				ps.setLong(3, size);
			}
		});
	}

	public void update(final long size) {
		String update = "update msg_stat set msg_num = msg_num + 1, msg_size = msg_size + ? where msg_day = ?";

		jdbcTemplate.update(update, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, size);
				ps.setString(2, msgDay());
			}
		});
	}
	
	public String msgDay() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH);
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		return year + "-" + month + "-" + day;
	}

//	public List<TraceStat> findTraceStat() {
//		final List<TraceStat> list = new ArrayList<TraceStat>();
//		String sql = "select * from trace_stat";
//		jdbcTemplate.query(sql, new RowMapper<TraceStat>() {
//
//			@Override
//			public TraceStat mapRow(ResultSet rs, int rowNum) throws SQLException {
//				TraceStat traceStat = new TraceStat();
//				traceStat.setName(rs.getString("trace_name"));
//				traceStat.setTotalCount(rs.getLong("total_count"));
//				traceStat.setFailureCount(rs.getLong("failure_count"));
//				traceStat.setFailurePrecent(rs.getFloat("failure_precent"));
//				traceStat.setMin(rs.getFloat("min"));
//				traceStat.setMax(rs.getFloat("max"));
//				traceStat.setAvg(rs.getFloat("avg"));
//				list.add(traceStat);
//				return traceStat;
//			}
//		});
//		return list;
//	}

}

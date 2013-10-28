package com.vipshop.microscope.hbase.mapreduce.picket;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;

public class CallTraceOut extends TraceOut {
	public void write(PreparedStatement ps) throws SQLException {
		ps.setString(1, this.type);
		ps.setString(2, this.position);
		ps.setString(3, this.trace_name);
		ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
		ps.setString(5, this.range);
		try {
			ps.setDate(6, new java.sql.Date(sdf.parse(this.start_date).getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
package com.vipshop.microscope.job.mapreduce;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

public class TraceOut implements WritableComparable<TraceOut>, DBWritable {
	protected String type;
	protected String position;
	protected String trace_id;
	protected String range;
	protected String start_date;
	protected String end_date;
	protected String oper_date;
	protected String trace_name;
	protected static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public void readFields(ResultSet arg0) throws SQLException {
	}

	public void write(PreparedStatement ps) throws SQLException {
		ps.setString(1, this.type);
		ps.setString(2, this.position);
		ps.setString(3, this.trace_id);
		ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
		ps.setString(5, this.range);
		try {
			ps.setDate(6, new java.sql.Date(sdf.parse(this.start_date).getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void readFields(DataInput in) throws IOException {
		this.type = in.readUTF();
		this.position = in.readUTF();
		this.trace_id = in.readUTF();
		this.range = in.readUTF();
		this.start_date = in.readUTF();
		this.end_date = in.readUTF();
		this.oper_date = in.readUTF();
		this.trace_name = in.readUTF();
	}

	public void write(DataOutput out) throws IOException {
		out.writeUTF(this.type);
		out.writeUTF(this.position);
		out.writeUTF(this.trace_id);
		out.writeUTF(this.range);
		out.writeUTF(this.start_date);
		out.writeUTF(this.end_date);
		out.writeUTF(this.oper_date);
		out.writeUTF(this.trace_name);
	}

	public int compareTo(TraceOut o) {
		return this.trace_id.compareTo(o.getTrace_id());
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getTrace_id() {
		return this.trace_id;
	}

	public void setTrace_id(String trace_id) {
		this.trace_id = trace_id;
	}

	public String getRange() {
		return this.range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getStart_date() {
		return this.start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return this.end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getOper_date() {
		return this.oper_date;
	}

	public void setOper_date(String oper_date) {
		this.oper_date = oper_date;
	}

	public String getTrace_name() {
		return this.trace_name;
	}

	public void setTrace_name(String trace_name) {
		this.trace_name = trace_name;
	}
}
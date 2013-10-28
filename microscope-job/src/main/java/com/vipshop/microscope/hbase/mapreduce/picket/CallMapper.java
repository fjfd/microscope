package com.vipshop.microscope.hbase.mapreduce.picket;

import java.io.IOException;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.Text;

public class CallMapper extends TableMapper<Text, Text> {

	private final Text t_id = new Text();
	private final Text t_name = new Text();

	@Override
	protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {

		String trace_name = new String(value.getValue(TableTrace.F_CFINFO_BYTE, TableTrace.Q_TRACENAME_BYTE));
		String trace_id = new String(value.getRow());
		if (null != trace_name && !trace_name.isEmpty()) {
			t_name.set(trace_name);
			t_id.set(trace_id);
			context.write(t_name, t_id);
		}
	}
}

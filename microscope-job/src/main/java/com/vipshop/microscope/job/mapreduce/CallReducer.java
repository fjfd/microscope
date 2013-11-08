package com.vipshop.microscope.job.mapreduce;

import java.io.IOException;
import java.util.LinkedList;

import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

@SuppressWarnings("unused")
public class CallReducer extends Reducer<Text, Text, TraceOut, NullWritable> {
	private CallList clist = new CallList();

	public void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, TraceOut, NullWritable>.Context context) throws IOException, InterruptedException {
		if ((null != key) && (!Bytes.toString(key.getBytes()).isEmpty())) {
			for (Text val : values) {
				this.clist.add(new String(key.getBytes()));
			}
		}
	}

	@SuppressWarnings("rawtypes")
	protected void cleanup(Reducer<Text, Text, TraceOut, NullWritable>.Context context) {
		int size = Integer.parseInt(context.getConfiguration().get("picket_top_size"));
		if (null != this.clist) {
			CallTraceOut out = null;
			LinkedList list = this.clist.getList();
			size = size > list.size() ? list.size() : size;
			for (int i = 0; i < size; i++) {
				PicketGuy ca = (PicketGuy) list.get(i);
				out = new CallTraceOut();
				out.setType("1");
				out.setTrace_name(ca.getName());
				out.setPosition(ca.getPosition() + "");
				out.setRange(context.getConfiguration().get("picket_range"));
				out.setStart_date(context.getConfiguration().get("startdate"));
				try {
					context.write(out, NullWritable.get());
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
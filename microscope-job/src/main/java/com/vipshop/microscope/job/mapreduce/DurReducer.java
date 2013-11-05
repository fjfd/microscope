package com.vipshop.microscope.job.mapreduce;

import java.io.IOException;
import java.util.LinkedList;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class DurReducer extends Reducer<Text, IntWritable, TraceOut, NullWritable> {
	private DurReducerList dlist;

	public void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, TraceOut, NullWritable>.Context context) throws IOException, InterruptedException {
		if (null == this.dlist) {
			this.dlist = new DurReducerList(Integer.parseInt(context.getConfiguration().get("picket_top_size")));
		}

		for (IntWritable val : values) {
			this.dlist.add(new PicketGuy(new String(key.getBytes()), val.get()));
		}
	}

	protected void cleanup(Reducer<Text, IntWritable, TraceOut, NullWritable>.Context context) {
		int size = Integer.parseInt(context.getConfiguration().get("picket_top_size"));
		if (null != this.dlist) {
			LinkedList<PicketGuy> list = this.dlist.getList();
			size = size > list.size() ? list.size() : size;
			TraceOut out = null;
			for (int i = 0; i < size; i++) {
				PicketGuy ca = (PicketGuy) list.get(i);
				out = new TraceOut();
				out.setType("0");
				out.setTrace_id(ca.getName());
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
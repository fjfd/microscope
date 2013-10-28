package com.vipshop.microscope.hbase.mapreduce.picket;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

public class DurMapper extends TableMapper<Text, IntWritable> {

	private final Text ONE = new Text();

	private DurMapperList dlist;

	@Override
	protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
		if (null == dlist) {
			dlist = new DurMapperList(Integer.parseInt(context.getConfiguration().get("picket_top_size")));
		}
		dlist.addResult(value);
	}

	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {
		LinkedList<Result> list = dlist.getList();
		if (null != list) {
			Iterator<Result> it = list.iterator();
			while (it.hasNext()) {
				Result re = (Result) it.next();
				// 保存duration
				IntWritable ID = new IntWritable(Integer.parseInt(new String(re.getValue(TableTrace.F_CFINFO_BYTE, TableTrace.Q_DURATION_BYTE))));
				// 保存trace id
				ONE.set(new String(re.getRow()));
				context.write(ONE, ID);
			}
		}
	}
}

package com.vipshop.microscope.hbase.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vipshop.microscope.hbase.repository.Repositorys;

public class ScanJob {
	
	private static final Logger logger = LoggerFactory.getLogger(ScanJob.class);

	public static class Map extends TableMapper<Text, LongWritable> {

		public static enum Counters {
			ROWS, SHAKESPEAREAN
		};

		@Override
		protected void setup(Context context) {
		}

		@Override
		protected void map(ImmutableBytesWritable rowkey, Result result, Context context) {
			logger.info(result.toString());
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = Repositorys.TRACE.getConfiguration();
		@SuppressWarnings("deprecation")
		Job job = new Job(conf, "TwitBase Shakespeare counter");
		job.setJarByClass(ScanJob.class);

		Scan scan = new Scan();
		TableMapReduceUtil.initTableMapperJob("span", scan, Map.class, ImmutableBytesWritable.class, Result.class, job);

		job.setOutputFormatClass(NullOutputFormat.class);
		job.setNumReduceTasks(0);
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}

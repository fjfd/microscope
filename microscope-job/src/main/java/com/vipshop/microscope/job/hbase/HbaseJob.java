package com.vipshop.microscope.job.hbase;

import com.vipshop.microscope.storage.hbase.RepositoryFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;

import java.io.IOException;

public class HbaseJob {

//	private static String targetTable = "test";

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration config = RepositoryFactory.getConfiguration();
        Job job = new Job(config, "ExampleReadWrite");
        job.setJarByClass(HbaseJob.class); // class that contains mapper

        Scan scan = new Scan();
        scan.setCaching(500); // 1 is the default in Scan, which will be bad for
        // MapReduce jobs
        scan.setCacheBlocks(false); // don't set to true for MR jobs
        // set other scan attrs

        TableMapReduceUtil.initTableMapperJob("trace", // input table
                scan, // Scan instance to control CF and attribute selection
                MyMapper.class, // mapper class
                null, // mapper output key
                null, // mapper output value
                job);
//		TableMapReduceUtil.initTableReducerJob(targetTable, // output table
//				null, // reducer class
//				job);
//		job.setNumReduceTasks(0);

        boolean b = job.waitForCompletion(true);
        if (!b) {
            throw new IOException("error with job!");
        }
    }

    public static class MyMapper extends TableMapper<ImmutableBytesWritable, Put> {

        private static Put resultToPut(ImmutableBytesWritable key, Result result) throws IOException {
            Put put = new Put(key.get());
            for (KeyValue kv : result.raw()) {
                System.out.println(kv);
                put.add(kv);
            }
            System.out.println(put);
            return put;
        }

        public void map(ImmutableBytesWritable row, Result value, Context context) throws IOException, InterruptedException {
            // this example is just copying the data from the source table...
            context.write(row, resultToPut(row, value));
        }
    }

    static class MyTableReducer extends TableReducer<Text, IntWritable, ImmutableBytesWritable> {
        public static final byte[] CF = "cf".getBytes();
        public static final byte[] COUNT = "count".getBytes();

        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int i = 0;
            for (IntWritable val : values) {
                i += val.get();
            }
            Put put = new Put(Bytes.toBytes(key.toString()));
            put.add(CF, COUNT, Bytes.toBytes(i));
            context.write(null, put);
        }
    }
}

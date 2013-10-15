package com.vipshop.microscope.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HDFSTemplate {
	
	private static FileSystem hdfs;

	public static void main(String[] args) throws Exception {
		// 1.创建配置器 
		Configuration conf = new Configuration();
		// 2.创建文件系统 
		hdfs = FileSystem.get(conf); 
		// 3.遍历HDFS上的文件和目录 
		FileStatus[] fs = hdfs.listStatus(new Path("/home")); 
		if (fs.length > 0) { 
			for (FileStatus f : fs) { 
				showDir(f);
			}
		}
	}

	private static void showDir(FileStatus fs) throws Exception {
		Path path = fs.getPath();
		System.out.println(path);
		if (fs.isDir()) {
			FileStatus[] f = hdfs.listStatus(path);
			if (f.length > 0) {
				for (FileStatus file : f) {
					showDir(file);
				}
			}
		}
	}
}

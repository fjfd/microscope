package com.vipshop.microscope.job.mapreduce;

/**
 * 项目名：microscope-job 包名：com.vipshop.microscope.hbase.mapreduce.comm
 * 创建日期：2013-10-28 作者：malaka.weng@vipshop.com
 */
public class ByteUtil {
	public static int compare(byte[] b1, byte[] b2) {
		if (b1.length > b2.length) {
			return 1;
		}
		if (b1.length < b2.length) {
			return -1;
		}

		for (int i = 0; i < b1.length; i++) {
			if (b1[i] != b2[i]) {
				return (b1[i] & 0xFF) - (b2[i] & 0xFF);
			}
		}

		return 0;
	}

	public static void main(String[] args) {
	}
}

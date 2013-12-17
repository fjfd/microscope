package com.vipshop.micorscope.framework.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil {
	
	public static int log2(int x) {
		return (int) (Math.log(x) / Math.log(2));
	}
	
	public static float calculateQPS(long count, long time) {
		BigDecimal bigcount = new BigDecimal(count * 1000);
		BigDecimal bigtime = new BigDecimal(time);
		return bigcount.divide(bigtime, 4, RoundingMode.HALF_DOWN).floatValue();
	}
	
	public static float calculateFailPre(long totalCount, long failCount) {
		BigDecimal bigTotalCount = new BigDecimal(totalCount);
		BigDecimal bigFailCount = new BigDecimal(failCount);
		return bigFailCount.divide(bigTotalCount, 4, RoundingMode.HALF_DOWN).floatValue();
	}
	
	public static float calculateAvgDura(long count, long sum) {
		BigDecimal bigSumDura = new BigDecimal(sum);
		BigDecimal bigCount = new BigDecimal(count);
		return bigSumDura.divide(bigCount, 4, RoundingMode.HALF_DOWN).floatValue();
	}

}

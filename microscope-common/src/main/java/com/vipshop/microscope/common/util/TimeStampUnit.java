package com.vipshop.microscope.common.util;

import java.util.Calendar;

/**
 * TimeStamp Unit
 *
 * @author Xu Fei
 * @version 1.0
 */
public enum TimeStampUnit {

    HOUR {
        public long toBaseSeconds(long d) {
            return base(d, C1);
        }

        public long offsetSeconds(long d) {
            return offsetSecond(d, C1);
        }

        public long offsetMinutes(long d) {
            return offsetMinute(d, C1);
        }
    },

    DAY {
        public long toBaseSeconds(long d) {
            return base(d);
        }

        public long offsetSeconds(long d) {
            return offsetSecond(d);
        }

        public long offsetMinutes(long d) {
            return offsetMinute(d);
        }

        public long offsetHours(long d) {
            return offsetHour(d);
        }
    },

    WEEK;

    // Handy constants for conversion methods
    static final long C0 = 1000L;
    static final long C1 = 3600L;
    static final long C2 = 60L;
//    static final long C3 = C2 * 1000L;
//    static final long C4 = C3 * 60L;
//    static final long C5 = C4 * 60L;
//    static final long C6 = C5 * 24L;

    static final long MAX = Long.MAX_VALUE;

    static long base(long d, long m) {
        d = d / C0;
        return d - (d % m);
    }

    static long offsetSecond(long d, long m) {
        d = d / C0;
        return (d % m);
    }

    static long offsetMinute(long d, long m) {
        d = d / C0;
        return (d % m) / C2;
    }

    static long base(long d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(d);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis() / 1000;
    }

    static long offsetSecond(long d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(d);
        return calendar.get(Calendar.SECOND);
    }

    static long offsetMinute(long d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(d);
        return calendar.get(Calendar.MINUTE);
    }

    static long offsetHour(long d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(d);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static void main(String[] args) {
        System.out.println(TimeStampUnit.HOUR.toBaseSeconds(System.currentTimeMillis()));
        System.out.println(TimeStampUnit.HOUR.offsetSeconds(System.currentTimeMillis()));
        System.out.println(TimeStampUnit.HOUR.offsetMinutes(System.currentTimeMillis()));

        System.out.println(TimeStampUnit.DAY.toBaseSeconds(System.currentTimeMillis()));
        System.out.println(TimeStampUnit.DAY.offsetHours(System.currentTimeMillis()));
        System.out.println(TimeStampUnit.DAY.offsetMinutes(System.currentTimeMillis()));
        System.out.println(TimeStampUnit.DAY.offsetSeconds(System.currentTimeMillis()));

    }

    public long toBaseSeconds(long duration) {
        throw new AbstractMethodError();
    }

    public long offsetSeconds(long duration) {
        throw new AbstractMethodError();
    }

    public long offsetMinutes(long duration) {
        throw new AbstractMethodError();
    }

    public long offsetHours(long duration) {
        throw new AbstractMethodError();
    }

}

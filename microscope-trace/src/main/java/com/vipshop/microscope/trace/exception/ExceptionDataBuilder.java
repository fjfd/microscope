package com.vipshop.microscope.trace.exception;

import com.vipshop.microscope.common.util.IPAddressUtil;
import com.vipshop.microscope.common.util.TimeStampUtil;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.storage.Storage;
import com.vipshop.microscope.trace.storage.StorageHolder;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.lang.management.ManagementFactory;

/**
 * Record exception API.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class ExceptionDataBuilder {

    private static final Storage storage = StorageHolder.getStorage();

    /**
     * Record Exception
     *
     * @param t exception
     */
    public static void record(final Throwable t) {
        ExceptionData exception = ExceptionData.newBuilder()
                .withApp(Tracer.APP_NAME)
                .withIP(IPAddressUtil.IPAddress())
                .withDate(TimeStampUtil.currentTimeMillis())
                .withExceptionName(t.getClass().getName())
                .withExceptionMsg(ExceptionUtils.getMessage(t))
                .withExceptionStack(ExceptionUtils.getStackTrace(t))
                .withTraceId(Tracer.getTraceId())
                .withThreadInfo(ManagementFactory.getThreadMXBean()
                        .getThreadInfo(Thread.currentThread().getId()).toString())
                .build();

        storage.addExceptionData(exception);
    }

    /**
     * Record Exception and debug info
     *
     * @param t    exception
     * @param info debug info
     */
    public static void record(final Throwable t, String info) {
        ExceptionData exception = ExceptionData.newBuilder()
                .withApp(Tracer.APP_NAME)
                .withIP(IPAddressUtil.IPAddress())
                .withDate(TimeStampUtil.currentTimeMillis())
                .withExceptionName(t.getClass().getName())
                .withExceptionMsg(ExceptionUtils.getMessage(t))
                .withExceptionStack(ExceptionUtils.getStackTrace(t))
                .withTraceId(Tracer.getTraceId())
                .withThreadInfo(ManagementFactory.getThreadMXBean()
                        .getThreadInfo(Thread.currentThread().getId()).toString())
                .withDebug(info)
                .build();

        storage.addExceptionData(exception);
    }

}

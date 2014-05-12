package com.vipshop.microscope.client.exception;

import com.vipshop.microscope.client.Tracer;
import com.vipshop.microscope.common.util.IPAddressUtil;
import com.vipshop.microscope.common.util.TimeStampUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.lang.management.ManagementFactory;

/**
 * Exception data builder.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class ExceptionDataBuilder {

    /**
     * Record exception
     *
     * @param t exception
     */
    public ExceptionData build(final Throwable t) {

        ExceptionData data = ExceptionData.newBuilder()
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

        return data;
    }

    /**
     * Record exception and debug info
     *
     * @param t    exception
     * @param info debug info
     */
    public ExceptionData build(final Throwable t, String info) {

        ExceptionData data = ExceptionData.newBuilder()
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

        return data;

    }

}

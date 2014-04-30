package com.vipshop.microscope.trace.exception;

import com.vipshop.microscope.common.util.IPAddressUtil;
import com.vipshop.microscope.common.util.TimeStampUtil;
import com.vipshop.microscope.trace.Constants;
import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.storage.Storage;
import com.vipshop.microscope.trace.storage.StorageHolder;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.lang.management.ManagementFactory;
import java.util.HashMap;

/**
 * Record exception API.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class ExceptionBuilder {

    private static final Storage storage = StorageHolder.getStorage();

    /**
     * Record Exception to map
     *
     * @param t exception
     */
    public static void record(final Throwable t) {
        HashMap<String, Object> exception = new HashMap<String, Object>();

        exception.put(Constants.APP, Tracer.APP_NAME);
        exception.put(Constants.IP, IPAddressUtil.IPAddress());
        exception.put(Constants.DATE, TimeStampUtil.currentTimeMillis());
        exception.put(Constants.EXCEPTION_NAME, t.getClass().getName());
        exception.put(Constants.EXCEPTION_MESSAGE, ExceptionUtils.getMessage(t));
        exception.put(Constants.EXCEPTION_STACK, ExceptionUtils.getStackTrace(t));
        exception.put(Constants.TRACE_ID, Tracer.getTraceId());
        exception.put(Constants.THREAD_INFO, ManagementFactory.getThreadMXBean()
                .getThreadInfo(Thread.currentThread().getId()).toString());

        storage.addException(exception);
    }

    /**
     * Record Exception and debug info to map
     *
     * @param t    exception
     * @param info debug info
     */
    public static void record(final Throwable t, String info) {
        HashMap<String, Object> exception = new HashMap<String, Object>();

        exception.put(Constants.APP, Tracer.APP_NAME);
        exception.put(Constants.IP, IPAddressUtil.IPAddress());
        exception.put(Constants.DATE, TimeStampUtil.currentTimeMillis());
        exception.put(Constants.EXCEPTION_NAME, t.getClass().getName());
        exception.put(Constants.EXCEPTION_MESSAGE, ExceptionUtils.getMessage(t));
        exception.put(Constants.EXCEPTION_STACK, ExceptionUtils.getStackTrace(t));
        exception.put(Constants.TRACE_ID, Tracer.getTraceId());
        exception.put(Constants.THREAD_INFO, ManagementFactory.getThreadMXBean()
                .getThreadInfo(Thread.currentThread().getId()).toString());
        exception.put(Constants.DEBUG, info);

        storage.addException(exception);
    }

}

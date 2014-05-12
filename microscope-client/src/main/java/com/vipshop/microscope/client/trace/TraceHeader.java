package com.vipshop.microscope.client.trace;

/**
 * Trace header for propagate trace status
 *
 * @author Xu Fei
 * @version 1.0
 */
public class TraceHeader {

    /**
     * HTTP header for propagate trace status.
     *
     * As {@code Nginx} server will remove string "X_B3_Trace_Id" with
     * underline, so use middle line "X-B3-Trace-Id".
     */
    public static final String X_B3_TRACE_ID = "X-B3-Trace-Id";
    public static final String X_B3_SPAN_ID = "X-B3-Span-Id";
    public static final String X_B3_PARENT_ID = "X-B3-Parent-Id";
    public static final String X_B3_FLAG = "X-B3-Flag";
    public static final String X_B3_SAMPLED = "X-B3-Sampled";

}

package com.vipshop.microscope.trace.exception;

import java.io.Serializable;

/**
 * Representation of a exception
 *
 * @author Xu Fei
 * @version 1.0
 */
public class ExceptionData implements Serializable {

    private static final long serialVersionUID = -9127396739328141119L;

    private String app;

    private String ip;

    private long date;

    private String exceptionName;

    private String exceptionMsg;

    private String exceptionStack;

    private String traceId;

    private String threadInfo;

    private String debug;

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private ExceptionData exceptionData;

        public Builder() {
            this.exceptionData = new ExceptionData();
        }

        public Builder withApp(String app) {
            this.exceptionData.app = app;
            return this;
        }

        public Builder withIP(String ip) {
            this.exceptionData.ip = ip;
            return this;
        }

        public Builder withDate(long date) {
            this.exceptionData.date = date;
            return this;
        }

        public Builder withExceptionName(String exceptionName) {
            this.exceptionData.exceptionName = exceptionName;
            return this;
        }

        public Builder withExceptionMsg(String msg) {
            this.exceptionData.exceptionMsg = msg;
            return this;
        }

        public Builder withExceptionStack(String stack) {
            this.exceptionData.exceptionStack = stack;
            return this;
        }

        public Builder withTraceId(String traceId) {
            this.exceptionData.traceId = traceId;
            return this;
        }

        public Builder withThreadInfo(String threadInfo) {
            this.exceptionData.threadInfo = threadInfo;
            return this;
        }

        public Builder withDebug(String debug){
            this.exceptionData.debug = debug;
            return this;
        }

        public ExceptionData build() {
            return this.exceptionData;
        }

    }

    public String getApp() {
        return app;
    }

    public String getIp() {
        return ip;
    }

    public long getDate() {
        return date;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public String getExceptionStack() {
        return exceptionStack;
    }

    public String getTraceId() {
        return traceId;
    }

    public String getThreadInfo() {
        return threadInfo;
    }

    public String getDebug() {
        return debug;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExceptionData that = (ExceptionData) o;

        if (date != that.date) return false;
        if (app != null ? !app.equals(that.app) : that.app != null) return false;
        if (debug != null ? !debug.equals(that.debug) : that.debug != null) return false;
        if (exceptionMsg != null ? !exceptionMsg.equals(that.exceptionMsg) : that.exceptionMsg != null) return false;
        if (exceptionName != null ? !exceptionName.equals(that.exceptionName) : that.exceptionName != null)
            return false;
        if (exceptionStack != null ? !exceptionStack.equals(that.exceptionStack) : that.exceptionStack != null)
            return false;
        if (ip != null ? !ip.equals(that.ip) : that.ip != null) return false;
        if (threadInfo != null ? !threadInfo.equals(that.threadInfo) : that.threadInfo != null) return false;
        if (traceId != null ? !traceId.equals(that.traceId) : that.traceId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = app != null ? app.hashCode() : 0;
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (int) (date ^ (date >>> 32));
        result = 31 * result + (exceptionName != null ? exceptionName.hashCode() : 0);
        result = 31 * result + (exceptionMsg != null ? exceptionMsg.hashCode() : 0);
        result = 31 * result + (exceptionStack != null ? exceptionStack.hashCode() : 0);
        result = 31 * result + (traceId != null ? traceId.hashCode() : 0);
        result = 31 * result + (threadInfo != null ? threadInfo.hashCode() : 0);
        result = 31 * result + (debug != null ? debug.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExceptionData{" +
                "app='" + app + '\'' +
                ", ip='" + ip + '\'' +
                ", date=" + date +
                ", exceptionName='" + exceptionName + '\'' +
                ", exceptionMsg='" + exceptionMsg + '\'' +
                ", exceptionStack='" + exceptionStack + '\'' +
                ", traceId='" + traceId + '\'' +
                ", threadInfo='" + threadInfo + '\'' +
                ", debug='" + debug + '\'' +
                '}';
    }
}

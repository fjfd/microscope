package com.vipshop.microscope.common.system;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Representation of a system info
 *
 * @author Xu Fei
 * @version 1.0
 */
public class SystemInfo implements Serializable {

    private static final long serialVersionUID = -9127396739328141118L;

    private SystemInfo(){}

    public static Builder pid(int pid) {
        return new Builder(pid);
    }

    public static class Builder {

        private final SystemInfo systemInfo;

        public Builder(int pid) {
            this.systemInfo = new SystemInfo();
            systemInfo.pid = pid;
        }

        public Builder withApp(String app) {
            this.systemInfo.app = app;
            return this;
        }

        public Builder withHost(String host) {
            this.systemInfo.host = host;
            return this;
        }

        public Builder withSystemProperties(Map<String, String> systemProperties) {
            this.systemInfo.systemProperties = systemProperties;
            return this;
        }

        public Builder withJVMArguments(List<String> arguments) {
            this.systemInfo.jvmArguments = arguments;
            return this;
        }

        public Builder withOSVersion(String version) {
            this.systemInfo.osVersion = version;
            return this;
        }

        public Builder withProcessorCount(int count) {
            this.systemInfo.processorCount = count;
            return this;
        }

        public SystemInfo build() {
            return systemInfo;
        }
    }

    private int pid;

    private String app;

    private String host;

    private Map<String, String> systemProperties;

    private List<String> jvmArguments;

    private String osVersion;

    private int processorCount;

    public int getPid() {
        return pid;
    }

    public String getApp() {
        return app;
    }

    public String getHost() {
        return host;
    }

    public Map<String, String> getSystemProperties() {
        return systemProperties;
    }

    public List<String> getJvmArguments() {
        return jvmArguments;
    }

    public int getProcessorCount() {
        return processorCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SystemInfo that = (SystemInfo) o;

        if (pid != that.pid) return false;
        if (processorCount != that.processorCount) return false;
        if (app != null ? !app.equals(that.app) : that.app != null) return false;
        if (host != null ? !host.equals(that.host) : that.host != null) return false;
        if (jvmArguments != null ? !jvmArguments.equals(that.jvmArguments) : that.jvmArguments != null) return false;
        if (osVersion != null ? !osVersion.equals(that.osVersion) : that.osVersion != null) return false;
        if (systemProperties != null ? !systemProperties.equals(that.systemProperties) : that.systemProperties != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pid;
        result = 31 * result + (app != null ? app.hashCode() : 0);
        result = 31 * result + (host != null ? host.hashCode() : 0);
        result = 31 * result + (systemProperties != null ? systemProperties.hashCode() : 0);
        result = 31 * result + (jvmArguments != null ? jvmArguments.hashCode() : 0);
        result = 31 * result + (osVersion != null ? osVersion.hashCode() : 0);
        result = 31 * result + processorCount;
        return result;
    }

    public String getOsVersion() {
        return osVersion;

    }

    @Override
    public String toString() {
        return "SystemInfo{" +
                "pid=" + pid +
                ", app='" + app + '\'' +
                ", host='" + host + '\'' +
                ", systemProperties=" + systemProperties +
                ", jvmArguments=" + jvmArguments +
                ", osVersion='" + osVersion + '\'' +
                ", processorCount=" + processorCount +
                '}';
    }

}

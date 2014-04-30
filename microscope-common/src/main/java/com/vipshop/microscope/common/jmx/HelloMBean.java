package com.vipshop.microscope.common.jmx;

public interface HelloMBean {

    public String getName();

    public void setName(String name);

    public void printHello();

    public void printHello(String name);
}

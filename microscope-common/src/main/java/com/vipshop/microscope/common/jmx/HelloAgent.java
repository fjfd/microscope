package com.vipshop.microscope.common.jmx;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class HelloAgent {

    public static void main(String[] args) throws InterruptedException, MalformedObjectNameException, NullPointerException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
        // Get the Platform MBean Server
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        // Construct the ObjectName for the MBean we will register
        ObjectName name = new ObjectName("com.example.mbeans:type=Hello");

        // Create the Hello World MBean
        Hello mbean = new Hello();

        // Register the Hello World MBean
        mbs.registerMBean(mbean, name);

        // Wait forever
        System.out.println("Waiting forever...");

        Thread.sleep(Long.MAX_VALUE);
    }
}

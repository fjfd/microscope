package com.vipshop.microscope.stats.metrics.gauge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JMXClient {

	public static void getConnection() throws Exception {
		Map<String, String[]> map = new HashMap<String, String[]>();
//		map.put("jmx.remote.credentials", new String[] { "monitorRole", "QED" });
		String jmxURL = "service:jmx:rmi:///jndi/rmi://10.100.90.183:1088/jmxrmi";

		JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);
		JMXConnector connector = JMXConnectorFactory.connect(serviceURL, map);
		MBeanServerConnection mbsc = connector.getMBeanServerConnection();
		Set<?> MBeanset = mbsc.queryMBeans(null, null);
		Iterator<?> MBeansetIterator = MBeanset.iterator();
		while (MBeansetIterator.hasNext()) {
			ObjectInstance objectInstance = (ObjectInstance) MBeansetIterator.next();
			ObjectName objectName = objectInstance.getObjectName();
			MBeanInfo objectInfo = mbsc.getMBeanInfo(objectName);
			System.out.print("ObjectName:" + objectName.getCanonicalName() + ".");
			System.out.print("mehtodName:");
			for (int i = 0; i < objectInfo.getAttributes().length; i++) {
				System.out.print(objectInfo.getAttributes()[i].getName() + ",");
			}
			System.out.println();
		}

	}

	public static void main(String[] args) throws Exception {
//		getConnection();
		Map<Thread, StackTraceElement[]> elements = Thread.getAllStackTraces(); 
		Set<Entry<Thread, StackTraceElement[]>> valueSet = elements.entrySet();
		for (Entry<Thread, StackTraceElement[]> entry : valueSet) {
			StackTraceElement[] stackTraceElement = entry.getValue();
			for (int i = 0; i < stackTraceElement.length; i++) {
				System.out.println(stackTraceElement[i]);
			}
		}
	}
}

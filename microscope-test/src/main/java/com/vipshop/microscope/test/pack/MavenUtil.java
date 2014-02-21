package com.vipshop.microscope.test.pack;

import java.io.File;
import java.util.Collections;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

public class MavenUtil {

	public static final String pomFile = "E:\\Workspace\\microscope\\pom.xml";
	public static final String mavFile = "E:\\Program\\apache-maven-3.0.5";

	public static void execute(String command) {
		InvocationRequest request = new DefaultInvocationRequest();
		request.setPomFile(new File(pomFile));
		request.setGoals(Collections.singletonList(command));

		Invoker invoker = new DefaultInvoker();
		invoker.setMavenHome(new File(mavFile));

		try {
			invoker.execute(request);
		} catch (MavenInvocationException e) {
			e.printStackTrace();
		}

	}

}

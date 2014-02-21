package com.vipshop.microscope.test.pack;

import java.util.ArrayList;
import java.util.List;

public class PackPath {

	private String envPath;
	private String envZipPath;

	private String collectorJarPath;
	private String collectorLibPath;

	private String queryJarPath;
	private String queryLibPath;

	private String testJarPath;
	private String testLibPath;

	private String envCollectorJarPath;
	private String envCollectorLibPath;
	private String envCollectorShPath;
	private String envCollectorShCont;

	private String envQueryJarPath;
	private String envQueryLibPath;
	private String envQueryShPath;
	private String envQueryShCont;

	private String envTestJarPath;
	private String envTestLibPath;
	private String envTestShPath;
	private String envTestShCont;

	public static List<PackPath> getPackPath() {
		List<PackPath> packPaths = new ArrayList<PackPath>();

		packPaths.add(devPackPath());
		packPaths.add(qaPackPath());
		packPaths.add(prodPackPath());

		return packPaths;
	}

	public static PackPath devPackPath() {
		PackPath path = new PackPath();

		path.setCollectorJarPath(PackEnv.collectorJarPath);
		path.setCollectorLibPath(PackEnv.collectorLibPath);

		path.setQueryJarPath(PackEnv.queryJarPath);
		path.setQueryLibPath(PackEnv.queryLibPath);

		path.setTestJarPath(PackEnv.testJarPath);
		path.setTestLibPath(PackEnv.testLibPath);

		path.setEnvPath(PackEnv.devPath);
		path.setEnvZipPath(PackEnv.devPathZip);

		path.setEnvCollectorJarPath(PackEnv.devCollectorJarPath);
		path.setEnvCollectorLibPath(PackEnv.devCollectorLibPath);
		path.setEnvCollectorShPath(PackEnv.devCollectorShPath);
		path.setEnvCollectorShCont(PackEnv.devCollectorShCont);

		path.setEnvQueryJarPath(PackEnv.devQueryJarPath);
		path.setEnvQueryLibPath(PackEnv.devQueryLibPath);
		path.setEnvQueryShPath(PackEnv.devQueryShPath);
		path.setEnvQueryShCont(PackEnv.devQueryShCont);

		path.setEnvTestJarPath(PackEnv.devTestJarPath);
		path.setEnvTestLibPath(PackEnv.devTestLibPath);
		path.setEnvTestShPath(PackEnv.devTestShPath);
		path.setEnvTestShCont(PackEnv.devTestShCont);

		return path;

	}

	public static PackPath qaPackPath() {
		PackPath path = new PackPath();

		path.setCollectorJarPath(PackEnv.collectorJarPath);
		path.setCollectorLibPath(PackEnv.collectorLibPath);

		path.setQueryJarPath(PackEnv.queryJarPath);
		path.setQueryLibPath(PackEnv.queryLibPath);

		path.setTestJarPath(PackEnv.testJarPath);
		path.setTestLibPath(PackEnv.testLibPath);

		path.setEnvPath(PackEnv.qaPath);
		path.setEnvZipPath(PackEnv.qaPathZip);

		path.setEnvCollectorJarPath(PackEnv.qaCollectorJarPath);
		path.setEnvCollectorLibPath(PackEnv.qaCollectorLibPath);
		path.setEnvCollectorShPath(PackEnv.qaCollectorShPath);
		path.setEnvCollectorShCont(PackEnv.qaCollectorShCont);

		path.setEnvQueryJarPath(PackEnv.qaQueryJarPath);
		path.setEnvQueryLibPath(PackEnv.qaQueryLibPath);
		path.setEnvQueryShPath(PackEnv.qaQueryShPath);
		path.setEnvQueryShCont(PackEnv.qaQueryShCont);

		path.setEnvTestJarPath(PackEnv.qaTestJarPath);
		path.setEnvTestLibPath(PackEnv.qaTestLibPath);
		path.setEnvTestShPath(PackEnv.qaTestShPath);
		path.setEnvTestShCont(PackEnv.qaTestShCont);

		return path;

	}

	public static PackPath prodPackPath() {
		PackPath path = new PackPath();

		path.setCollectorJarPath(PackEnv.collectorJarPath);
		path.setCollectorLibPath(PackEnv.collectorLibPath);

		path.setQueryJarPath(PackEnv.queryJarPath);
		path.setQueryLibPath(PackEnv.queryLibPath);

		path.setTestJarPath(PackEnv.testJarPath);
		path.setTestLibPath(PackEnv.testLibPath);

		path.setEnvPath(PackEnv.prodPath);
		path.setEnvZipPath(PackEnv.prodPathZip);

		path.setEnvCollectorJarPath(PackEnv.prodCollectorJarPath);
		path.setEnvCollectorLibPath(PackEnv.prodCollectorLibPath);
		path.setEnvCollectorShPath(PackEnv.prodCollectorShPath);
		path.setEnvCollectorShCont(PackEnv.prodCollectorShCont);

		path.setEnvQueryJarPath(PackEnv.prodQueryJarPath);
		path.setEnvQueryLibPath(PackEnv.prodQueryLibPath);
		path.setEnvQueryShPath(PackEnv.prodQueryShPath);
		path.setEnvQueryShCont(PackEnv.prodQueryShCont);

		path.setEnvTestJarPath(PackEnv.prodTestJarPath);
		path.setEnvTestLibPath(PackEnv.prodTestLibPath);
		path.setEnvTestShPath(PackEnv.prodTestShPath);
		path.setEnvTestShCont(PackEnv.prodTestShCont);

		return path;

	}

	public String getCollectorJarPath() {
		return collectorJarPath;
	}

	public void setCollectorJarPath(String collectorPath) {
		this.collectorJarPath = collectorPath;
	}

	public String getCollectorLibPath() {
		return collectorLibPath;
	}

	public void setCollectorLibPath(String collectorLibPath) {
		this.collectorLibPath = collectorLibPath;
	}

	public String getQueryJarPath() {
		return queryJarPath;
	}

	public void setQueryJarPath(String queryPath) {
		this.queryJarPath = queryPath;
	}

	public String getQueryLibPath() {
		return queryLibPath;
	}

	public void setQueryLibPath(String queryLibPath) {
		this.queryLibPath = queryLibPath;
	}

	public String getTestJarPath() {
		return testJarPath;
	}

	public void setTestJarPath(String webPath) {
		this.testJarPath = webPath;
	}

	public String getTestLibPath() {
		return testLibPath;
	}

	public void setTestLibPath(String webLibPath) {
		this.testLibPath = webLibPath;
	}

	public String getEnvPath() {
		return envPath;
	}

	public void setEnvPath(String envPath) {
		this.envPath = envPath;
	}

	public String getEnvZipPath() {
		return envZipPath;
	}

	public void setEnvZipPath(String envPathZip) {
		this.envZipPath = envPathZip;
	}

	public String getEnvCollectorJarPath() {
		return envCollectorJarPath;
	}

	public void setEnvCollectorJarPath(String envCollectorPath) {
		this.envCollectorJarPath = envCollectorPath;
	}

	public String getEnvCollectorLibPath() {
		return envCollectorLibPath;
	}

	public void setEnvCollectorLibPath(String envCollectorLibPath) {
		this.envCollectorLibPath = envCollectorLibPath;
	}

	public String getEnvCollectorShPath() {
		return envCollectorShPath;
	}

	public void setEnvCollectorShPath(String envCollectorShPath) {
		this.envCollectorShPath = envCollectorShPath;
	}

	public String getEnvCollectorShCont() {
		return envCollectorShCont;
	}

	public void setEnvCollectorShCont(String envCollectorShCon) {
		this.envCollectorShCont = envCollectorShCon;
	}

	public String getEnvQueryJarPath() {
		return envQueryJarPath;
	}

	public void setEnvQueryJarPath(String envQueryPath) {
		this.envQueryJarPath = envQueryPath;
	}

	public String getEnvQueryLibPath() {
		return envQueryLibPath;
	}

	public void setEnvQueryLibPath(String envQueryLibPath) {
		this.envQueryLibPath = envQueryLibPath;
	}

	public String getEnvQueryShPath() {
		return envQueryShPath;
	}

	public void setEnvQueryShPath(String envQueryShPath) {
		this.envQueryShPath = envQueryShPath;
	}

	public String getEnvQueryShCont() {
		return envQueryShCont;
	}

	public void setEnvQueryShCont(String envQueryShCont) {
		this.envQueryShCont = envQueryShCont;
	}

	public String getEnvTestJarPath() {
		return envTestJarPath;
	}

	public void setEnvTestJarPath(String envWebPath) {
		this.envTestJarPath = envWebPath;
	}

	public String getEnvTestLibPath() {
		return envTestLibPath;
	}

	public void setEnvTestLibPath(String envWebLibPath) {
		this.envTestLibPath = envWebLibPath;
	}

	public String getEnvTestShPath() {
		return envTestShPath;
	}

	public void setEnvTestShPath(String envWebShPath) {
		this.envTestShPath = envWebShPath;
	}

	public String getEnvTestShCont() {
		return envTestShCont;
	}

	public void setEnvTestShCont(String envWebShCont) {
		this.envTestShCont = envWebShCont;
	}

}

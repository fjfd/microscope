package com.vipshop.microscope.test.pack;

public class PackEnv {

	public static String version = "1.3.4";

	public static String collectorJarPath = "E:\\Workspace\\microscope\\microscope-collector\\target\\microscope-collector-" + version + ".jar";
	public static String collectorLibPath = "E:\\Workspace\\microscope\\microscope-collector\\target\\lib\\";

	public static String queryJarPath = "E:\\Workspace\\microscope\\microscope-query\\target\\microscope-query-" + version + ".jar";
	public static String queryLibPath = "E:\\Workspace\\microscope\\microscope-query\\target\\lib\\";

	public static String testJarPath = "E:\\Workspace\\microscope\\microscope-test\\target\\microscope-test-" + version + ".jar";
	public static String testLibPath = "E:\\Workspace\\microscope\\microscope-test\\target\\lib\\";

	public static String devPath = "E:\\Workspace\\microscope-dev\\" + version;
	public static String devPathZip = "E:\\Workspace\\microscope-dev\\" + version + ".zip";

	public static String devCollectorJarPath = "E:\\Workspace\\microscope-dev\\" + version + "\\collector\\microscope-collector-" + version + ".jar";
	public static String devCollectorLibPath = "E:\\Workspace\\microscope-dev\\" + version + "\\collector\\lib\\";
	public static String devCollectorShPath = "E:\\Workspace\\microscope-dev\\" + version + "\\collector\\collector.sh";
	public static String devCollectorShCont = "nohup java -jar microscope-collector-" + version + ".jar &";

	public static String devQueryJarPath = "E:\\Workspace\\microscope-dev\\" + version + "\\query\\microscope-query-" + version + ".jar";
	public static String devQueryLibPath = "E:\\Workspace\\microscope-dev\\" + version + "\\query\\lib\\";
	public static String devQueryShPath = "E:\\Workspace\\microscope-dev\\" + version + "\\query\\query.sh";
	public static String devQueryShCont = "nohup java -jar microscope-query-" + version + ".jar &";

	public static String devTestJarPath = "E:\\Workspace\\microscope-dev\\" + version + "\\test\\microscope-test-" + version + ".jar";
	public static String devTestLibPath = "E:\\Workspace\\microscope-dev\\" + version + "\\test\\lib\\";
	public static String devTestShPath = "E:\\Workspace\\microscope-dev\\" + version + "\\test\\test.sh";
	public static String devTestShCont = "nohup java -jar microscope-test-" + version + ".jar &";

	public static String qaPath = "E:\\Workspace\\microscope-qa\\" + version;
	public static String qaPathZip = "E:\\Workspace\\microscope-qa\\" + version + ".zip";

	public static String qaCollectorJarPath = "E:\\Workspace\\microscope-qa\\" + version + "\\collector\\microscope-collector-" + version + ".jar";
	public static String qaCollectorLibPath = "E:\\Workspace\\microscope-qa\\" + version + "\\collector\\lib\\";
	public static String qaCollectorShPath = "E:\\Workspace\\microscope-qa\\" + version + "\\collector\\collector.sh";
	public static String qaCollectorShCont = "nohup java -jar microscope-collector-" + version + ".jar &";

	public static String qaQueryJarPath = "E:\\Workspace\\microscope-qa\\" + version + "\\query\\microscope-query-" + version + ".jar";
	public static String qaQueryLibPath = "E:\\Workspace\\microscope-qa\\" + version + "\\query\\lib\\";
	public static String qaQueryShPath = "E:\\Workspace\\microscope-qa\\" + version + "\\query\\query.sh";
	public static String qaQueryShCont = "nohup java -jar microscope-query-" + version + ".jar &";

	public static String qaTestJarPath = "E:\\Workspace\\microscope-qa\\" + version + "\\test\\microscope-test-" + version + ".jar";
	public static String qaTestLibPath = "E:\\Workspace\\microscope-qa\\" + version + "\\test\\lib\\";
	public static String qaTestShPath = "E:\\Workspace\\microscope-qa\\" + version + "\\test\\test.sh";
	public static String qaTestShCont = "nohup java -jar microscope-test-" + version + ".jar &";

	public static String prodPath = "E:\\Workspace\\microscope-publish\\" + version;
	public static String prodPathZip = "E:\\Workspace\\microscope-publish\\" + version + ".zip";

	public static String prodCollectorJarPath = "E:\\Workspace\\microscope-publish\\" + version + "\\collector\\microscope-collector-" + version + ".jar";
	public static String prodCollectorLibPath = "E:\\Workspace\\microscope-publish\\" + version + "\\collector\\lib\\";
	public static String prodCollectorShPath = "E:\\Workspace\\microscope-publish\\" + version + "\\collector\\collector.sh";
	public static String prodCollectorShCont = "nohup java -jar microscope-collector-" + version + ".jar &";

	public static String prodQueryJarPath = "E:\\Workspace\\microscope-publish\\" + version + "\\query\\microscope-query-" + version + ".jar";
	public static String prodQueryLibPath = "E:\\Workspace\\microscope-publish\\" + version + "\\query\\lib\\";
	public static String prodQueryShPath = "E:\\Workspace\\microscope-publish\\" + version + "\\query\\query.sh";
	public static String prodQueryShCont = "nohup java -Dport=8888 -jar microscope-query-" + version + ".jar &";

	public static String prodTestJarPath = "E:\\Workspace\\microscope-publish\\" + version + "\\test\\microscope-test-" + version + ".jar";
	public static String prodTestLibPath = "E:\\Workspace\\microscope-publish\\" + version + "\\test\\lib\\";
	public static String prodTestShPath = "E:\\Workspace\\microscope-publish\\" + version + "\\test\\test.sh";
	public static String prodTestShCont = "nohup java -Dport=1500 -jar microscope-test-" + version + ".jar &";

}

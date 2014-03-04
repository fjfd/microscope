package com.vipshop.microscope.test.pack;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pack {

	private static Logger logger = LoggerFactory.getLogger(Pack.class);

	public static void main(String[] args) throws Exception {
		MavenUtil.execute("versions:set -DnewVersion=" + PackEnv.version);

		MavenUtil.execute("clean package -Denv=dev");
		pack(PackPath.devPackPath());

//		MavenUtil.execute("clean package -Denv=qa");
//		pack(PackPath.qaPackPath());
//
//		MavenUtil.execute("clean package -Denv=prod");
//		pack(PackPath.prodPackPath());
	}

	public static void pack(PackPath path) throws Exception {

		FileUtil.deleteFile(new File(path.getEnvPath()));
		FileUtil.deleteFile(new File(path.getEnvZipPath()));

		File[] collectorFiles = new File(path.getCollectorLibPath()).listFiles();
		File toCollectorFile = new File(path.getEnvCollectorLibPath());
		toCollectorFile.mkdirs();

		for (File file : collectorFiles) {
			logger.info("Copy " + file.getPath() + " to " + path.getEnvCollectorJarPath() + file.getName());
			FileUtil.copyFile(file, new File(path.getEnvCollectorLibPath() + file.getName()));
		}

		logger.info("Copy " + path.getCollectorJarPath() + " to " + path.getEnvCollectorJarPath());
		FileUtil.copyFile(new File(path.getCollectorJarPath()), new File(path.getEnvCollectorJarPath()));

		logger.info("Write collector.sh " + path.getEnvCollectorShPath());
		FileUtil.writeFile(path.getEnvCollectorShPath(), path.getEnvCollectorShCont());

		File[] queryFiles = new File(path.getQueryLibPath()).listFiles();
		File toQueryFile = new File(path.getEnvQueryLibPath());
		toQueryFile.mkdirs();
		for (File file : queryFiles) {
			logger.info("Copy " + file.getPath() + " to " + path.getEnvQueryLibPath() + file.getName());
			FileUtil.copyFile(file, new File(path.getEnvQueryLibPath() + file.getName()));
		}

		logger.info("Copy " + path.getQueryJarPath() + " to " + path.getEnvQueryJarPath());
		FileUtil.copyFile(new File(path.getQueryJarPath()), new File(path.getEnvQueryJarPath()));

		logger.info("Write query.sh " + path.getEnvQueryShPath());
		FileUtil.writeFile(path.getEnvQueryShPath(), path.getEnvQueryShCont());

		File[] webFiles = new File(path.getTestLibPath()).listFiles();
		File toWebFile = new File(path.getEnvTestLibPath());
		toWebFile.mkdirs();
		for (File file : webFiles) {
			logger.info("Copy " + file.getPath() + " to " + path.getEnvTestLibPath() + file.getName());
			FileUtil.copyFile(file, new File(path.getEnvTestLibPath() + file.getName()));
		}

		logger.info("Copy " + path.getTestJarPath() + " to " + path.getEnvTestJarPath());
		FileUtil.copyFile(new File(path.getTestJarPath()), new File(path.getEnvTestJarPath()));

		logger.info("Write test.sh " + path.getEnvTestShPath());
		FileUtil.writeFile(path.getEnvTestShPath(), path.getEnvTestShCont());

		logger.info("compress to zip file ...");
		FileUtil.compressedFile(path.getEnvPath(), path.getEnvZipPath());

	}

}

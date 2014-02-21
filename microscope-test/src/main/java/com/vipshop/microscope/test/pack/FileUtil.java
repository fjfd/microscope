package com.vipshop.microscope.test.pack;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {

	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	public static void compressedFile(String resourcesPath, String targetPath) throws Exception {
		File resourcesFile = new File(resourcesPath);
		File targetFile = new File(targetPath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		String targetName = resourcesFile.getName() + ".zip";
		FileOutputStream outputStream = new FileOutputStream(targetPath + "\\" + targetName);
		ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(outputStream));

		createCompressedFile(out, resourcesFile, "");

		out.close();
	}

	public static void createCompressedFile(ZipOutputStream out, File file, String dir) throws Exception {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			out.putNextEntry(new ZipEntry(dir + "/"));

			dir = dir.length() == 0 ? "" : dir + "/";

			for (int i = 0; i < files.length; i++) {
				createCompressedFile(out, files[i], dir + files[i].getName());
			}
		} else {
			FileInputStream fis = new FileInputStream(file);

			out.putNextEntry(new ZipEntry(dir));
			int j = 0;
			byte[] buffer = new byte[1024];
			while ((j = fis.read(buffer)) > 0) {
				out.write(buffer, 0, j);
			}
			fis.close();
		}
	}

	@SuppressWarnings("resource")
	public static long copyFile(File f1, File f2) throws Exception {
		long time = new Date().getTime();
		int length = 2097152;
		FileInputStream in = new FileInputStream(f1);
		FileOutputStream out = new FileOutputStream(f2);
		FileChannel inC = in.getChannel();
		FileChannel outC = out.getChannel();
		@SuppressWarnings("unused")
		int i = 0;
		while (true) {
			if (inC.position() == inC.size()) {
				inC.close();
				outC.close();
				return new Date().getTime() - time;
			}
			if ((inC.size() - inC.position()) < 20971520)
				length = (int) (inC.size() - inC.position());
			else
				length = 20971520;
			inC.transferTo(inC.position(), length, outC);
			inC.position(inC.position() + length);
			i++;
		}
	}

	public static void writeFile(String filePath, String content) {
		try {
			File file = new File(filePath);
			PrintStream ps = new PrintStream(new FileOutputStream(file));
			ps.println(content);
			ps.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void deleteFile(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
				logger.info("delete file :" + file.getAbsolutePath());
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
				}
			}
			file.delete();
		} else {
			logger.info("file dose not exist");
		}
	}

	public static void deleteFile(String name) {

	}

}